package org.example;


public class Account {
    private String accountName;
    private int balance;

    public Account(String accountName, int balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getBalance() {
        return balance;
    }



}