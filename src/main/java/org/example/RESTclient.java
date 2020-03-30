package org.example;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONObject;

// REST java client application. The client will get a text menu with different options. This version does not implement passwords
// since this is not the goal of this exercise
public class RESTclient {
    public static void main(String[] args) throws IOException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the bank REST test application running on Jetty, please enter your accountName.");
        String accountName = scanner.nextLine();

        String jsonString = getAccountDetails(accountName); // returns a String in jsonformat
        JSONObject jsonObject = new JSONObject(jsonString); // name and balance stored in jsonObject
        int balance = jsonObject.getInt("balance");
        System.out.println("Welcome ." + accountName +", would you like to deposit(d),withdraw(w), or check your balance(b)?");
        String command = scanner.nextLine();


        // balance handler
        if("b".equalsIgnoreCase(command)){
            System.out.println("Your balance is: "+ balance);

        }
        //deposit handler
        else if("d".equalsIgnoreCase(command)) {
            System.out.println("How much do you want to deposit?");
            int amount = Integer.parseInt(scanner.nextLine()); //gives error if input is NaN
            balance += amount;
            setAccountBalance(accountName,balance);
            System.out.println(amount +" Deposited successfully");
        }
        else if("w".equalsIgnoreCase(command)){
            System.out.println("How much do you want to withdraw?");
            int amount = Integer.parseInt(scanner.nextLine()); //gives error if input is NaN
            if (amount > balance){
                System.out.println("Insufficient funds,check your balance!");
            }
            else{
                balance -= amount;
                setAccountBalance(accountName,balance);
                System.out.println(amount +" Withdrawn successfully");
            }


        }

        else{
            System.out.println("Invalid command");
        }
        scanner.close();

        System.out.println("REST bank will close now");

    }
    // method to get accountdata using the api, in this case we use the local jetty server.
    public static String getAccountDetails(String name) throws IOException{

        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/bankService/" + name).openConnection();

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

            return response;
        }

        // an error happened
        return null;
    }

    public static void setAccountBalance(String accountName,int balance) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/bankService/" + accountName).openConnection();

        connection.setRequestMethod("POST");
        //parameters for the servlet
        String postData = "accountName=" + URLEncoder.encode(accountName);
        postData += "&balance=" + balance;


        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            System.out.println("POST was successful.");
        }
        else if(responseCode == 401){
            System.out.println("Wrong password.");
        }
    }
}
