package Core.ScreenShot.Runnable;

import Core.Image.ByteOfImage.ByteOfImage;
import Core.Node.NodeImpl.Client;
import Core.ScreenShot.CaptureScreenShotWithMouse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class CaptureScreenShotWithMouseRunnable implements Runnable {
    public int port;
    public String IP;

    public void setOutputStream(String ip, int port) {
        this.port = port;
        this.IP = ip;
    }

    public void setOutputStream(Client client) {
        this.port = client.getPort();
        this.IP = client.getAddress();
    }

    @Override
    public void run() {
        try {
            BufferedImage bufferedImage = null;
            ObjectOutputStream objectOutputStream=null;
            Socket socket = null;
            ByteOfImage byteOfImage= null;
            while (true) {
                socket = new Socket(IP, port);
                objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                bufferedImage = CaptureScreenShotWithMouse.tackScreenShot();
                byteOfImage = new ByteOfImage(bufferedImage);
                objectOutputStream.writeObject(byteOfImage);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
        } catch (AWTException e) {
            //TODO something
            e.printStackTrace();
        } catch (UnknownHostException e) {
            //TODO something
            e.printStackTrace();
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
    }
}
