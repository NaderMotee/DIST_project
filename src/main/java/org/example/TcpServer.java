package org.example;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Runnable;

public class TcpServer implements Runnable{
    public static void main(String[] args) throws Exception {
        Runnable runnable = new TcpServer();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    @Override
    public void run(){
        while (true){
            //Initialize Sockets
            System.out.println("Server started working.");
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(5000);
                Socket clientSocket = serverSocket.accept();

                //The InetAddress specification
                InetAddress serverIP = InetAddress.getByName("localhost"); // 127.0.0.1

                //Specify the file
                File file = new File("Data/document.pdf");
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
                    System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
                }

                os.flush();
                //File transfer done. Close the clientSocket connection!
                clientSocket.close();
                serverSocket.close();
                System.out.println("File sent succesfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}