package com.service;

import com.controller.AtmController;
import com.exception.InsufficientBalanceException;
import com.exception.InsufficientNoteException;
import com.exception.InvalidAmountException;
import com.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AtmService {

    @Autowired
    AtmController atmController;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/dispense")
    public ResponseEntity<ResponseWrapper> deposit(
            @RequestParam("accountNumber") int accountNumber, @RequestParam("pin") int pin, @RequestParam("amount") int amount)
            throws InsufficientNoteException, InvalidAmountException, InsufficientBalanceException {
        return atmController.calculateBank(accountNumber, pin, amount);
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/checkBalance")
    public ResponseEntity<ResponseWrapper> checkBalance() {
        return atmController.checkBalance();
    }

}
