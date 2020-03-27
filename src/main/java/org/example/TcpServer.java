package org.example;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) throws Exception {
        //Initialize Sockets
        System.out.println("Server started working.");
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket clientSocket = serverSocket.accept();

        //The InetAddress specification
        InetAddress serverIP = InetAddress.getByName("localhost"); // 127.0.0.1

        //Specify the file
        File file = new File("Cloud_access_-_Group_8.pdf");
        FileInputStream fis = new FileInputStream(file); // Reads bytes from the file.
        BufferedInputStream bis = new BufferedInputStream(fis); // Gives extra functionality to fileInputStream so it can buffer data.

        //Get clientSocket's output stream
        OutputStream os = clientSocket.getOutputStream(); // Enables to send the data to the client

        //Read File Contents into contents array
        byte[] contents;
        long fileLength = file.length();
        long current = 0;

        long start = System.nanoTime();
        while(current!=fileLength){
            int size = 10000;
            if(fileLength - current >= size)
                current += size;
            else{
                size = (int)(fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
        }

        os.flush();
        //File transfer done. Close the clientSocket connection!
        clientSocket.close();
        serverSocket.close();
        System.out.println("File sent succesfully!");
    }
}