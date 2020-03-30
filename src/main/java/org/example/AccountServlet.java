package org.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class AccountServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestUrl = request.getRequestURI();

        String accountName = requestUrl.substring("/UserService/getUser/".length());

        Account account = BankData.getInstance().getAccount(accountName);

        if(account != null){
            String json = "{\n";
            json += "\"accountName\": " + JSONObject.quote(account.getAccountName()) + ",\n";
            json += "\"balance\": " + account.getBalance() + ",\n";
            json += "}";
            response.getOutputStream().println(json); // prints the requested info in json format
        }
        else{
            //account not found, return error message
            response.getOutputStream().println("{}");
        }
    }



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String accountName = request.getParameter("accountName");
        int balance= Integer.parseInt(request.getParameter("balance"));

        BankData.getInstance().putAccount(new Account(accountName, balance));
    }
}