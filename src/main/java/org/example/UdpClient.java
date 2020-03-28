package org.example;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UdpClient {
    public static void main(String[] args) throws Exception{
        //Initialize socket and send a wake up packet to server.
        System.out.println("Client started working.");
        byte[] sentContent;
        int size = 0;
        sentContent = new byte[size];
        DatagramSocket clientSocket = new DatagramSocket();
        DatagramPacket sentPacket = new DatagramPacket(sentContent, size, InetAddress.getByName("host2"), 1234); // the ip address and port number to where we want to sent the data.
        clientSocket.send(sentPacket);

        // Now we will get the file from the server.
        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("Data/ReceivedData.pdf");
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] receivedContent;
        size = 10000;
        receivedContent = new byte[size];
        //Number of bytes read in one read() call
        int bytesRead = 0;
        DatagramPacket receivedPacket = new DatagramPacket(receivedContent, size);
        clientSocket.receive(receivedPacket); // Receive first packet so the application can check if its not empty.
        String received = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
        while(received.length() >= size){ // As long as you receive the max size stay in the while loop. The last time you'll receive something < 10000
            bos.write(receivedContent);
            clientSocket.receive(receivedPacket);
        }
        if(received.length() > 0){ // if the last time you've received something 10000>x>0, this needs to be written away.
            bos.write(receivedContent);
        }
        bos.flush();
        clientSocket.close();
        bos.close();
        fos.close();
        System.out.println("File saved successfully!");
    }
}
