package org.example;
import java.util.HashMap;
import java.util.Map;

public class BankData {
    private Map<String, Account> accountMap = new HashMap<>();
    private static BankData instance = new BankData();

    //Create an instance to test our program with dummy data
    public static BankData getInstance() {
        return instance;
    }
    // Constructor for the dummy instance
    private BankData(){
        accountMap.put("Odemar",new Account("Odemar",100));
        accountMap.put("Jens",new Account("Jens",500));
        accountMap.put("Nader",new Account("Nader",700));
        accountMap.put("Samer",new Account("Samer",300));
        accountMap.put("Calvin",new Account("Calvin",1000));
    }

    public Account getAccount(String accountName) {
        return accountMap.get(accountName);
    }

    public void putAccount(Account account) {
        accountMap.put(account.getAccountName(), account);
    }
}