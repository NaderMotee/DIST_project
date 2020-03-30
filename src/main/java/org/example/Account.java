package org.example;

import javax.naming.InsufficientResourcesException;

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

    public void addMoney(int amount) {
        balance += amount;

    }

    public int takeMoney(int amount) {
        if (amount > balance) {
            return -1; //InsufficientResources
        }
        balance -= amount;
        return 0;
    }

}