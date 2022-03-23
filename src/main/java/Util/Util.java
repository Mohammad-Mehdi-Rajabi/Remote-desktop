package Util;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Util {
    /**
     * static method to delete all file in directory
     * @param dirPath path of directory
     */
    public static void deleteFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        for(File file : filesList) {
            if(file.isFile()) {
                file.delete();
            } else {
                deleteFiles(file);
            }
        }
    }

    /**
     * static method to return system IP
     * @return IP
     */
    public static String getSystemIP(){
        String ip=null;
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
}
