package Node;

import java.net.*;
import java.io.*;

public class Client extends Node {

    // initialize socket and input output streams
    private String ip;
    private Socket socket                      = null;
    private DataInputStream dataInputStream    = null;
    private DataOutputStream dataOutputStream  = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            dataInputStream  = new DataInputStream(System.in);

            // sends output to the socket
            dataOutputStream    = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException unknownHostException)
        {
            System.out.println(unknownHostException);
        }
        catch(IOException ioException)
        {
            System.out.println(ioException);
        }

        // close the connection
        try
        {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
/*
        public static void main(String args[])
        {
            Client client = new Client("127.0.0.1", 5000);
        }
*/
}
