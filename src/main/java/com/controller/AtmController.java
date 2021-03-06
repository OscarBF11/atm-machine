package com.controller;

import com.database.DatabaseManager;
import com.model.ResponseWrapper;
import com.model.User;
import com.util.Constant;
import com.util.BankValidator;
import com.util.HashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AtmController {

    private final Logger logger = LoggerFactory.getLogger(AtmController.class);

    @Autowired
    DatabaseManager databaseManager;
    @Autowired
    HashHelper hashHelper;

    public ResponseEntity<ResponseWrapper> calculateBank(int accountNumber, int pin, int amount){
        String responseCode;
        String responseDesc;
        String responseStatus;
        ResponseWrapper responseWrapper = new ResponseWrapper();
        try {
            User user = databaseManager.getUserByAccountNumber(accountNumber);
            //Temp generate SHA here
            String hashedPin = hashHelper.hashString(Integer.toString(pin));
            if (hashedPin.equals(user.getPin())) {
                if (amount <= user.getOverdraft() && amount <= user.getOpeningBalance()) {
                    try {
                        int[] bankAmounts = databaseManager.getBankAmount();
                        int[] bankValues = databaseManager.getBankValues();

                        BankValidator.validateAmount(amount);
                        BankValidator.validateRemainingBalance(amount, bankAmounts, bankValues);

                        List<int[]> bankList = findBanks(amount, new int[bankAmounts.length], bankAmounts, bankValues, 0);
                        int[] selectedBankList = BankValidator.validateRemainingNote(bankAmounts, bankList);
                        int[] updatedBankList = subtractBankAmt(bankAmounts, selectedBankList);
                        databaseManager.updateOverdraftUser(accountNumber, user.getOverdraft() - amount);
                        databaseManager.updateBalanceAmt(updatedBankList, bankValues);
                        responseWrapper.setResponseBody(selectedBankList, bankValues);
                        responseCode = Constant.SUCCESS_CODE;
                        responseDesc = "OK - Actual balance: "+ databaseManager.getUserByAccountNumber(accountNumber).getOverdraft() + "???";
                        responseStatus = Constant.SUCCESS;
                    } catch (Exception e) {
                        logger.error("Got Exception while processing : {}", e.getMessage());
                        responseCode = Constant.FAIL_CODE;
                        responseDesc = e.getMessage();
                        responseStatus = Constant.FAIL;
                    }
                }else {
                    responseCode = Constant.FAIL_CODE;
                    responseDesc = "Insufficient balance - Actual balance: " + user.getOverdraft() + "???";
                    responseStatus = Constant.FAIL;
                }
            } else {
                responseCode = Constant.FAIL_CODE;
                responseDesc = "Incorrect PIN";
                responseStatus = Constant.FAIL;
            }
        } catch (Exception e){
            logger.error("Got Exception while processing : {}", e.getMessage());
            responseCode = Constant.FAIL_CODE;
            responseDesc = e.getMessage();
            responseStatus = Constant.FAIL;
          }


        responseWrapper.setResponseCode(responseCode);
        responseWrapper.setResponseDesc(responseDesc);
        responseWrapper.setResponseStatus(responseStatus);

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }

    public ResponseEntity<ResponseWrapper> checkBalance() {
        int[] bankAmounts = databaseManager.getBankAmount();
        int[] bankValues = databaseManager.getBankValues();

        ResponseWrapper responseWrapper = new ResponseWrapper();

        responseWrapper.setResponseBody(bankAmounts, bankValues);
        responseWrapper.setResponseCode(Constant.SUCCESS_CODE);
        responseWrapper.setResponseDesc(Constant.SUCCESS);
        responseWrapper.setResponseStatus(Constant.SUCCESS);

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }

    public List<int[]> findBanks(int amount, int[] currentBankAmt, int[] balanceBankAmt, int[] bankValues, int position){
        List<int[]> bankCombination = new ArrayList<>();
        int totalAmt = calCurrentTotalAmt(currentBankAmt, bankValues);
        if (totalAmt < amount) {
            for (int i = position; i < currentBankAmt.length; i++) {
                if (balanceBankAmt[i] > currentBankAmt[i]) {
                    int[] newCurrentBankAmt = currentBankAmt.clone();
                    newCurrentBankAmt[i]++;
                    List<int[]> resultList = findBanks(amount, newCurrentBankAmt, balanceBankAmt, bankValues, i);
                    if (resultList!=null) {
                        bankCombination.addAll(resultList);
                    }
                }
            }
        } else if (totalAmt == amount){
            bankCombination.add(currentBankAmt);
        }
        return bankCombination;
    }

    public int[] subtractBankAmt(int[] balanceBanks, int[] banks){
        for (int i = 0; i < banks.length; i++){
            balanceBanks[i] -= banks[i];
        }
        return balanceBanks;
    }

    public int calCurrentTotalAmt(int[] bankAmounts, int[] bankValues){
        int totalAmt = 0;
        for (int i = 0; i< bankAmounts.length; i++){
            totalAmt += bankAmounts[i]*bankValues[i];
        }
        return totalAmt;
    }
}
