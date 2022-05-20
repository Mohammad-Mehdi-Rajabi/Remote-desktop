package Core.Manager.Server.ManagedServerNonStatic;

import Core.Manager.ServerType.ServerType;
import Core.Node.NodeImpl.Server;
import Core.Property.IP;

import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ManagedServer implements Serializable {
    private String defaultPassword;
    private List<IP> especialIpsList;
    private transient Map<ServerType, Server> servers;

    private transient Socket screenSocket;
    private transient Socket mouseSocket;
    private transient Socket keyBoardSocket;
    private transient Socket dataTransferSocket;

    public ManagedServer() {
        initialize();
    }

    public void initialize() {
        defaultPassword = Core.Manager.Server.ManagedServerStatic.ManagedServer.getDefaultPassword();
        especialIpsList = Core.Manager.Server.ManagedServerStatic.ManagedServer.getEspecialIpsList();
        servers = Core.Manager.Server.ManagedServerStatic.ManagedServer.getServers();
        screenSocket = Core.Manager.Server.ManagedServerStatic.ManagedServer.getScreenSocket();
        mouseSocket = Core.Manager.Server.ManagedServerStatic.ManagedServer.getMouseSocket();
        keyBoardSocket = Core.Manager.Server.ManagedServerStatic.ManagedServer.getKeyBoardSocket();
        dataTransferSocket = Core.Manager.Server.ManagedServerStatic.ManagedServer.getDataTransferSocket();
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public List<IP> getEspecialIpsList() {
        return especialIpsList;
    }

    public void setEspecialIpsList(List<IP> especialIpsList) {
        this.especialIpsList = especialIpsList;
    }

    public Map<ServerType, Server> getServers() {
        return servers;
    }

    public void setServers(Map<ServerType, Server> servers) {
        this.servers = servers;
    }

    public Socket getScreenSocket() {
        return screenSocket;
    }

    public void setScreenSocket(Socket screenSocket) {
        this.screenSocket = screenSocket;
    }

    public Socket getMouseSocket() {
        return mouseSocket;
    }

    public void setMouseSocket(Socket mouseSocket) {
        this.mouseSocket = mouseSocket;
    }

    public Socket getKeyBoardSocket() {
        return keyBoardSocket;
    }

    public void setKeyBoardSocket(Socket keyBoardSocket) {
        this.keyBoardSocket = keyBoardSocket;
    }

    public Socket getDataTransferSocket() {
        return dataTransferSocket;
    }

    public void setDataTransferSocket(Socket dataTransferSocket) {
        this.dataTransferSocket = dataTransferSocket;
    }
}
