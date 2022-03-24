package Node;

import java.io.IOException;

/**
 * super class for client and server class
 */
public abstract class Node {

    private int port;

    /**
     * to establish socket connection
     * @throws IOException when could not establish socket connection
     */
    public abstract void established() throws IOException;


    /**
     * to return socket connection status
     * @return connection status
     * @throws IOException when could not establish socket connection
     */
    public abstract boolean state() throws IOException;
}
