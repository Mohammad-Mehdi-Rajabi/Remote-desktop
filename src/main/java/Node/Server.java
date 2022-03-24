package Node;
import java.net.*;
import java.io.*;
public class Server extends Node {
    //initialize socket and input stream
    private Socket          socket             = null;
    private ServerSocket    serverSocket       = null;
    private DataInputStream dataInputStream    = null;
    private DataOutputStream dataOutputStream  = null;

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = serverSocket.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            System.out.println("Closing connection");
            // close connection

            //--------------------------------------------

           //code here

            //-----------------------------------------------



            socket.close();
            dataInputStream.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    @Override
    public void established() throws IOException {

    }

    @Override
    public boolean state() {
        return false;
    }

    /*
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }

     */

}
