package com.model;

public class User {

    private int accountNumber;
    private String pin;
    private int openingBalance;
    private int Overdraft;

    public User(){
    }

    public User(int accountNumber, String pin, int openingBalance, int overdraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.openingBalance = openingBalance;
        Overdraft = overdraft;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getOverdraft() {
        return Overdraft;
    }

    public void setOverdraft(int overdraft) {
        Overdraft = overdraft;
    }

    @Override
    public String toString(){
        return "Bank{" +
                " accountNumber=" + getAccountNumber() +
                " pin=" + getPin() +
                " openingBalance=" + getOpeningBalance() +
                " Overdraft=" + getOverdraft() +
                " }";
    }
}
