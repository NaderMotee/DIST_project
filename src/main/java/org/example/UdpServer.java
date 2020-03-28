package org.example;
import java.net.ServerSocket;
import java.net.Socket;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        try{
            ServerSocket serverSocket = new ServerSocket(5000);
            int counter=0;
            System.out.println("Server Started ....");
            while(true){
                counter++;
                Socket clientSocket=serverSocket.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                UdpServerClientThread sct = new UdpServerClientThread(clientSocket,counter); //send  the request to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}