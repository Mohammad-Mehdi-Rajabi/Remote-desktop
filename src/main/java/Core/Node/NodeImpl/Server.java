package Core.Node.NodeImpl;

import Core.Node.Node;

/**
 * class Server for listening on a port
 * Interface: Core.Node
 */
public class Server implements Node {

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
