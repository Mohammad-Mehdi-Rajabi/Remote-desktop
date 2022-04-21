package Core.ScreenShot.Runnable;

import Core.Image.ByteOfImage.ByteOfImage;
import Core.Node.Client;
import Core.ScreenShot.CaptureScreenShotWithMouse;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import static Core.ScreenShot.CaptureScreenShotWithMouse.bufferedImage;

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
//        try {
//            BufferedImage bufferedImage = null;
//            Socket socket = null;
//            while (true) {
//                socket = new Socket(IP, port);
//                bufferedImage = CaptureScreenShotWithMouse.tackScreenShot();
//                ImageIO.write(bufferedImage, "jpg", socket.getOutputStream());
//                socket.getOutputStream().flush();
//                socket.getOutputStream().close();
//                Thread.sleep(1);
//            }
//        } catch (AWTException e) {
//            //TODO something
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            //TODO something
//            e.printStackTrace();
//        } catch (IOException e) {
//            //TODO something
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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
