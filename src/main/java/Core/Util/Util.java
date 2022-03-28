package Core.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Util {
    /**
     * static method to delete all file in directory
     *
     * @param dirPath path of directory
     */
    public static void deleteFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        for (File file : filesList) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteFiles(file);
            }
        }
    }

    /**
     * static method to return system IP
     *
     * @return IP
     */
    public static String getSystemIP() {
        String ip = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                return ip;

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    /**
     * static method to convert ip in string to byte array
     *
     * @param ip ip in string
     * @return ip in byte array
     */
    public static byte[] stringIPToByteIP(String ip) {
        String[] split = ip.split(".");
        byte[] b = new byte[4];
        int i = 0;
        for (String s : split) {
            b[i++] = Byte.parseByte(s);
        }
        return b;
    }

    public static void addDataToTableView(TableView tableView, ArrayList<String> arrayList) throws SQLException {

        ObservableList<String> row = null;
        row = FXCollections.observableArrayList();
        for (String s : arrayList) {
            row.add(s);
        }
        tableView.setItems(row);
    }
}
