package org.example;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) throws Exception{
        //Initialize socket
        Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);
        byte[] contents = new byte[10000];

        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("../../../../Data/ReceivedData.pdf");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = socket.getInputStream(); // I want to receive a file from the server.

        //Number of bytes read in one read() call
        int bytesRead = 0;

        while((bytesRead=is.read(contents))!=-1) // -1 ==> no data left to read.
            bos.write(contents, 0, bytesRead); // content, offset, how many bytes are read.

        bos.flush();
        socket.close();

        System.out.println("File saved successfully!");
    }
}
