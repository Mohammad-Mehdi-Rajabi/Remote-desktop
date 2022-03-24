package Node;

import java.net.*;
import java.io.*;

/**
 * sub class Server for listening on a port
 * SUPERCLASS: Node
 */
public class Server extends Node {

    private int port;
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    /**
     * initialize port field
     *
     * @param port port to listening
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * inject ServerSocket directly
     *
     * @param serverSocket serverSocket
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * read Node class Docs
     *
     * @throws IOException when could not establish socket connection
     */
    @Override
    public void established() throws IOException {
        if (serverSocket == null) {
            this.serverSocket = new ServerSocket(port);
        } else {
            this.port = this.serverSocket.getLocalPort();
        }
        this.socket = serverSocket.accept();
        this.outputStream = this.socket.getOutputStream();
        this.inputStream = this.socket.getInputStream();
    }

    /**
     * read Node class Docs
     *
     * @return boolean
     * @throws IOException when could not establish socket connection
     */
    @Override
    public boolean state() throws IOException {
        return serverSocket.getInetAddress().isReachable(2000);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
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
}
