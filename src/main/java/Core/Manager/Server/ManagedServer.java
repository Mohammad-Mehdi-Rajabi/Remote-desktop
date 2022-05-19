package Core.Manager.Server;

import Core.Node.NodeImpl.Server;
import Core.Property.IP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagedServer {
    private static String defaultPassword;
    private static List<IP> especialIpsList;
    private static Map<String, Server> servers;


    static {
        especialIpsList = new ArrayList<>();
        servers = new HashMap<>();
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

    public static Map<String, Server> getServers() {
        return servers;
    }

    public static void setServers(Map<String, Server> servers) {
        ManagedServer.servers = servers;
    }
}
