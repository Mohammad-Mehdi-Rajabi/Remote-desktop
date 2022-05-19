package Core.Util;

import Main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
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

    // before use switchWindow and openWindow initialize main field by Main class
    public static Main main;

    /**
     * to switch from right scene to new one
     * before use switchWindow  initialize main field by Main class
     *
     * @param url        url of fxml file
     * @param title      title of new scene
     * @param resizeable resizeable ability
     */
    public static void switchWindow(URL url, String title, Boolean resizeable) {
        try {
            Parent root = FXMLLoader.load(url);
            main.stage.setResizable(resizeable);
            main.stage.setTitle(title);
            main.stage.setScene(new Scene(root));
            main.stage.centerOnScreen();
            main.stage.setAlwaysOnTop(true);
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
    }

    /**
     * open new scene
     * before use openWindow initialize main field by Main class
     *
     * @param url        url of fxml file
     * @param title      title of new scene
     * @param resizeable resizeable ability
     */
    public static void openWindow(URL url, String title, boolean resizeable) {
        Stage stage2 = new Stage();
        try {
            stage2.setResizable(resizeable);
            Parent parent = FXMLLoader.load(url);
            stage2.setScene(new Scene(parent));
            stage2.setResizable(resizeable);
            stage2.setTitle(title);
            stage2.centerOnScreen();
            stage2.setAlwaysOnTop(true);
            stage2.show();
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
    }


    public static Stage switchWindowWithReturnValue(URL url, String title, Boolean resizeable) {
        try {
            Parent root = FXMLLoader.load(url);
            main.stage.setResizable(resizeable);
            main.stage.setTitle(title);
            main.stage.setScene(new Scene(root));
            main.stage.centerOnScreen();
            main.stage.setAlwaysOnTop(true);
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
        return Main.stage;
    }

    public static Stage openWindowWithReturnValue(URL url, String title, boolean resizeable) {
        Stage stage2 = new Stage();
        try {
            stage2.setResizable(resizeable);
            Parent parent = FXMLLoader.load(url);
            stage2.setScene(new Scene(parent));
            stage2.setResizable(resizeable);
            stage2.setTitle(title);
            stage2.centerOnScreen();
            stage2.setAlwaysOnTop(true);
            stage2.show();
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
        return stage2;
    }
}
