package org.example;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.DatagramPacket;
import java.util.concurrent.Semaphore;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        try{
            System.out.println("Server started working.");
            byte[] receivedContent;
            int size = 10000;
            receivedContent = new byte[size];
            DatagramPacket receivedPacket = new DatagramPacket( receivedContent, size);
            DatagramSocket serverSocket = new DatagramSocket(1234);
            serverSocket.receive(receivedPacket); // If there's a request, we save this request datagram packet in receivedPacket

            InetAddress clientAddress = receivedPacket.getAddress(); // Returns the IP address of the machine to which this datagram is being sent or from which the datagram was received.
            int clientPort = receivedPacket.getPort();

            //Specify the file
            File file = new File("Data/document.pdf");
            FileInputStream fis = new FileInputStream(file); // Reads bytes from the file.
            BufferedInputStream bis = new BufferedInputStream(fis); // Gives extra functionality to fileInputStream so it can buffer data.

            // Specify new Datagram packet to send to the client
            DatagramPacket sendPacket;
            byte[] sendContents;
            long fileLength = file.length();
            long current = 0;
            while(current!=fileLength) {
                if(fileLength - current >= size)
                    current += size;
                else{
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                sendContents = new byte[size];
                bis.read(sendContents, 0, size);
                sendPacket = new DatagramPacket(sendContents, size, clientAddress, clientPort);
                System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
                serverSocket.send(sendPacket);
            }
            bis.close();
            fis.close();
            serverSocket.close();
            System.out.println("File sent succesfully!");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}