package org.example;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Runnable;

public class TcpServer {
    public static void main(String[] args) throws Exception {
        try{
            ServerSocket serverSocket = new ServerSocket(5000);
            int counter=0;
            System.out.println("Server Started ....");
            while(true){
                counter++;
                Socket clientSocket=serverSocket.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                TcpServerClientThread sct = new TcpServerClientThread(clientSocket,counter); //send  the request to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}