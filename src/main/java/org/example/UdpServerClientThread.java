package org.example;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;

public class UdpServerClientThread extends Thread{
    Socket clientSocket;
    int clientNo;
    Semaphore readSem;
    Semaphore writeSem;

    UdpServerClientThread(Socket inSocket, int counter){
        clientSocket = inSocket;
        clientNo=counter;
        readSem = new Semaphore(1);
        writeSem = new Semaphore(1);
    }

    public void run(){
        try{
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
            readSem.acquire();
            long fileLength = file.length();
            readSem.release();
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
                readSem.acquire();
                bis.read(contents, 0, size);
                readSem.release();
                writeSem.acquire();
                os.write(contents);
                writeSem.release();
                System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
            }

            os.flush();
            //File transfer done. Close the clientSocket connection!
            clientSocket.close();
            fis.close();
            bis.close();
            System.out.println("File sent succesfully!");
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        finally{
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }
}
