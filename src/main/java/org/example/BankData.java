package org.example;
import java.util.HashMap;
import java.util.Map;

public class BankData {
    private Map<String, Account> accountMap = new HashMap<>();
    //Create an instance to test our program with dummy data
    private static BankData instance = new BankData();

    // Constructor for the dummy instance
    private BankData(){
        accountMap.put("Odemar",new Account("Odemar",100));
        accountMap.put("Jens",new Account("Jens",500));
        accountMap.put("Nader",new Account("Nader",700));
        accountMap.put("Samer",new Account("Samer",300));
        accountMap.put("Calvin",new Account("Calvin",1000));
    }
    public static BankData getInstance() {
        return instance;
    }
    public Account getAccount(String accountName) {
        return accountMap.get(accountName);
    }

    public void putAccount(Account account) { // adds the account to the hashmap, if it already exists, it will update the value instead.
        accountMap.put(account.getAccountName(), account);
    }

}