package Core.Node.NodeImpl;

import Core.Node.Node;

import java.io.Serializable;

/**
 * class Server for listening on a port
 * Interface: Core.Node
 */
public class Server implements Node, Serializable {
    public static final long serialVersionUID = 9863574210000l;

    private int port;



    /**
     * initialize port field
     *
     * @param port port to listening
     */
    public Server(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
