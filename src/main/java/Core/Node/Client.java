package Core.Node;

import java.net.*;
import java.io.*;

/**
 * class Client for connecting to  especial IP and port
 * Interface: Core.Node
 */
public class Client implements Node {
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

