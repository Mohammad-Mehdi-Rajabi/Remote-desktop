package Node;

import java.net.*;
import java.io.*;

/**
 * sub class Client for connecting to  especial IP and port
 * SUPERCLASS: Node
 */
public class Client extends Node {

    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private int port;
    private String address;


    /**
     * initialize address and port field
     *
     * @param address IP address or Host name
     * @param port    port to connection
     */
    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * inject socket directly
     *
     * @param socket socket
     */
    public Client(Socket socket) {
        this.socket = socket;
    }

    /**
     * read Node class Docs
     *
     * @throws IOException when could not establish socket connection
     */
    @Override
    public void established() throws IOException {
        if (socket == null) {
            socket = new Socket(address, port);
        } else {
            this.address = "";
            this.port = this.socket.getLocalPort();
            byte[] ip = this.socket.getInetAddress().getAddress();
            for (byte b : ip) {
                this.address += b;
            }
        }
        this.inputStream = socket.getInputStream();

        this.outputStream = socket.getOutputStream();
    }

    /**
     * read Node class Docs
     *
     * @return boolean
     * @throws IOException when could not establish socket connection
     */
    @Override
    public boolean state() throws IOException {

        return this.socket.getInetAddress().isReachable(2000);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

