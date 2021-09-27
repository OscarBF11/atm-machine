package com.util;

public class BankMapper {

    public static String getBankType(int bankValue){
        String bankType;
        switch (bankValue){
            case 50:
                bankType = BankEnum.FIFTY.name();
                break;
            case 20:
                bankType = BankEnum.TWENTY.name();
                break;
            case 10:
                bankType = BankEnum.TEN.name();
                break;
            default:
                bankType = BankEnum.FIVE.name();
                break;
        }
        return bankType;
    }

}
