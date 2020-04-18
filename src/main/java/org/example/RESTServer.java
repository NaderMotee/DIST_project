package org.example;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

@SpringBootApplication
public class RESTServer {
    public static void main(String[] args) throws IOException, JSONException {
        SpringApplication.run(RESTServer.class, args);
        System.out.println("REST Server started succesfully!");
        clientApplication();
    }

    private static int clientApplication() throws IOException, JSONException {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the bank REST test application, please enter your accountName.");
        String accountName = scanner.nextLine(); // this accountname has not yet been checked
        String jsonString = getAccountDetails(accountName); // returns a String in jsonformat
        JSONObject jsonObject = new JSONObject(jsonString); // name and balance stored in jsonObject
        int balance = jsonObject.getInt("balance");
        String validAccountName = jsonObject.getString("accountName");
        if (validAccountName.equals("error")){
            System.out.println("Invalid username, exiting program.");
            return -1;}
        System.out.println("Welcome " + validAccountName);
        while(running) {
            System.out.println("would you like to deposit(d),withdraw(w),check your balance(b)? or quit(q)?");
            String command = scanner.nextLine();


            // balance handler
            if ("b".equalsIgnoreCase(command)) {
                System.out.println("Your balance is: " + balance);

            }
            //deposit handler
            else if ("d".equalsIgnoreCase(command)) {
                System.out.println("How much do you want to deposit?");
                int amount = Integer.parseInt(scanner.nextLine()); //gives error if input is NaN

                String depositString = addAccountBalance(accountName, amount);
                System.out.println(depositString);
                JSONObject depositJson = new JSONObject(depositString);
                balance = depositJson.getInt("balance");
                System.out.println(amount + " Deposited successfully, new balance = " + balance);

            } else if ("w".equalsIgnoreCase(command)) {
                System.out.println("How much do you want to withdraw?");
                int amount = Integer.parseInt(scanner.nextLine()); //gives error if input is NaN
                String withdrawString = addAccountBalance(accountName, -1*amount);
                JSONObject withdrawJson = new JSONObject(withdrawString);
                if (withdrawJson.getString("accountName").equals("error"))
                    System.out.println("Insufficient funds,check your balance!");

                else {
                    balance = withdrawJson.getInt("balance");
                    System.out.println(amount + " Withdrawn successfully, new balance =" + balance);
                }


            }
            else if("q".equalsIgnoreCase(command)){
                running = false;
            }
            else {
                System.out.println("Invalid command");
            }
        }
        scanner.close();

        System.out.println("REST bank will close now");
        return 0;
    }
    public static String getAccountDetails(String name) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/balanceRequest?accountName=" + name).openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){ //connection successful
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
                response += "\n";
            }
            scanner.close();
            // returns a string
            return response;
        }
        System.out.println("Request failed!");

        // an error happened
        return null;
    }

    public static String addAccountBalance(String accountName, int amount) throws IOException{

        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/addBalance/" + accountName + "?amount=" + amount).openConnection();

        connection.setRequestMethod("PUT");
        //parameters for the servlet



        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){

                response += scanner.nextLine();
                response += "\n";
            }
            scanner.close();
            return response;
        }
        System.out.println("Request failed!");
        return "";
    }
}

