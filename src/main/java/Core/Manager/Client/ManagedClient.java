package Core.Manager.Client;

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
    private static Map<String, Server> servers;

    static {
        servers = new HashMap<>();
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

    public static Map<String, Server> getServers() {
        return servers;
    }

    public static void setServers(Map<String, Server> servers) {
        ManagedClient.servers = servers;
    }

    public static boolean sendPassword() {
        try {
            Socket socket = new Socket(ip, servers.get("dataTransferServer").getPort());
            DataOutputStream dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(password);
            dataOutputStream.flush();
            dataOutputStream.close();
            socket = new Socket(ip, servers.get("dataTransferServer").getPort());
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
}
