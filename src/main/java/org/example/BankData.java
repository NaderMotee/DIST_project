package org.example;
import java.util.HashMap;
import java.util.Map;

public class BankData {
    private Map<String, Account> accountMap = new HashMap<>();
    private static BankData instance = new BankData();

    public static BankData getInstance() {
        return instance;
    }

    public Account getAccount(String accountName) {
        return accountMap.get(accountName);
    }

    public void putAccount(Account account) {
        accountMap.put(account.getAccountName(), account);
    }
}