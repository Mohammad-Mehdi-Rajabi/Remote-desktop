package Core.Manager.Client.ManagedClientStatic;

import Core.Manager.ServerType.ServerType;
import Core.Node.NodeImpl.Server;
import Core.Util.Util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ManagedClient {
    private static String password;
    private static String ip;
    private static Map<ServerType, Server> servers;

    private transient static Socket screenSocket;
    private transient static Socket mouseSocket;
    private transient static Socket keyBoardSocket;
    private transient static Socket dataTransferSocket;

    static {
        servers = new HashMap<>();
        screenSocket = null;
        mouseSocket = null;
        keyBoardSocket = null;
        dataTransferSocket = null;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ManagedClient.password = password;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        ManagedClient.ip = ip;
    }

    public static Map<ServerType, Server> getServers() {
        return servers;
    }

    public static void setServers(Map<ServerType, Server> servers) {
        ManagedClient.servers = servers;
    }

    public static boolean sendPassword() {
        try {
            Socket socket = new Socket(ip, servers.get(ServerType.DATA_TRANSFER_SERVER).getPort());
            DataOutputStream dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(password);
            dataOutputStream.flush();
            dataOutputStream.close();
            socket = new Socket(ip, servers.get(ServerType.DATA_TRANSFER_SERVER).getPort());
            dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(Util.getSystemIP());
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Socket getScreenSocket() {
        return screenSocket;
    }

    public static void setScreenSocket(Socket screenSocket) {
        ManagedClient.screenSocket = screenSocket;
    }

    public static Socket getMouseSocket() {
        return mouseSocket;
    }

    public static void setMouseSocket(Socket mouseSocket) {
        ManagedClient.mouseSocket = mouseSocket;
    }

    public static Socket getKeyBoardSocket() {
        return keyBoardSocket;
    }

    public static void setKeyBoardSocket(Socket keyBoardSocket) {
        ManagedClient.keyBoardSocket = keyBoardSocket;
    }

    public static Socket getDataTransferSocket() {
        return dataTransferSocket;
    }

    public static void setDataTransferSocket(Socket dataTransferSocket) {
        ManagedClient.dataTransferSocket = dataTransferSocket;
    }
}
