package org.example;

import org.springframework.web.bind.annotation.*;


@RestController
public class RESTController {
    @GetMapping("/balanceRequest")
    public Account getBalance(@RequestParam(value = "accountName") String accountName) {
        System.out.println("Received getBalance Request from " + accountName + ", executing query..");
        Account account = BankData.getInstance().getAccount(accountName);
        if (account == null) {
            System.out.println("ERROR:Account " + accountName + " not found! Please make sure that the requested accountName exists.");
            return new Account("error", 0);
        }
        int balance = account.getBalance();
        System.out.println("Account found, balance = " + balance);
        return new Account(accountName,balance);

    }
    @PutMapping("/addBalance/{accountName}")
    public Account addBalance(@PathVariable String accountName,@RequestParam(value = "amount") int amount){
        System.out.println("Received addBalance Request from " + accountName + ", executing query..");
        Account account = BankData.getInstance().getAccount(accountName);
        if(account.getBalance() + amount >=0){
            account.addBalance(amount);
            BankData.getInstance().putAccount(account);
            return new Account(accountName,account.getBalance());
        }
        // if amount is invalid, return null
       return new Account("error",0);

    }
}

