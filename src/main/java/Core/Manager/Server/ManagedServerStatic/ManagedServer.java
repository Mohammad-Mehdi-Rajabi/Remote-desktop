package Core.Manager.Server.ManagedServerStatic;

import Core.Manager.ServerType.ServerType;
import Core.Node.NodeImpl.Server;
import Core.Property.IP;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagedServer  {
    private static String defaultPassword;
    private static List<IP> especialIpsList;
    private static Map<ServerType, Server> servers;

    private  static Socket screenSocket;
    private  static Socket mouseSocket;
    private  static Socket keyBoardSocket;
    private  static Socket dataTransferSocket;
    private transient static ObjectOutputStream dataTransferSocketObjectOutputStream;
    private transient static ObjectInputStream dataTransferSocketObjectInputStream;


    static {
        especialIpsList = new ArrayList<>();
        servers = new HashMap<>();
        screenSocket = null;
        mouseSocket = null;
        keyBoardSocket = null;
        dataTransferSocket = null;
        dataTransferSocketObjectOutputStream = null;
        dataTransferSocketObjectInputStream = null;
    }


    public static boolean passwordValidate(String password, String ip) {
        if (especialIpsList.size() > 0) {
            for (IP i : especialIpsList) {
                if (i.getIP().equals(ip) && i.getPassword().equals(password))
                    return true;
            }
        } else {
            if (defaultPassword.equals(password))
                return true;
        }
        return false;
    }

    public static String getDefaultPassword() {
        return defaultPassword;
    }

    public static void setDefaultPassword(String defaultPassword) {
        ManagedServer.defaultPassword = defaultPassword;
    }

    public static List<IP> getEspecialIpsList() {
        return especialIpsList;
    }

    public static void setEspecialIpsList(List<IP> especialIpsList) {
        ManagedServer.especialIpsList = especialIpsList;
    }

    public static Map<ServerType, Server> getServers() {
        return servers;
    }

    public static void setServers(Map<ServerType, Server> servers) {
        ManagedServer.servers = servers;
    }

    public static Socket getScreenSocket() {
        return screenSocket;
    }

    public static void setScreenSocket(Socket screenSocket) {
        ManagedServer.screenSocket = screenSocket;
    }

    public static Socket getMouseSocket() {
        return mouseSocket;
    }

    public static void setMouseSocket(Socket mouseSocket) {
        ManagedServer.mouseSocket = mouseSocket;
    }

    public static Socket getKeyBoardSocket() {
        return keyBoardSocket;
    }

    public static void setKeyBoardSocket(Socket keyBoardSocket) {
        ManagedServer.keyBoardSocket = keyBoardSocket;
    }

    public static Socket getDataTransferSocket() {
        return dataTransferSocket;
    }

    public static void setDataTransferSocket(Socket dataTransferSocket) {
        ManagedServer.dataTransferSocket = dataTransferSocket;
    }

    public static ObjectOutputStream getDataTransferSocketObjectOutputStream() {
        return dataTransferSocketObjectOutputStream;
    }

    public static void setDataTransferSocketObjectOutputStream(ObjectOutputStream dataTransferSocketObjectOutputStream) {
        ManagedServer.dataTransferSocketObjectOutputStream = dataTransferSocketObjectOutputStream;
    }

    public static ObjectInputStream getDataTransferSocketObjectInputStream() {
        return dataTransferSocketObjectInputStream;
    }

    public static void setDataTransferSocketObjectInputStream(ObjectInputStream dataTransferSocketObjectInputStream) {
        ManagedServer.dataTransferSocketObjectInputStream = dataTransferSocketObjectInputStream;
    }
}
