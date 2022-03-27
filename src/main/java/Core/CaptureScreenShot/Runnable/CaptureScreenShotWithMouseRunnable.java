package Core.CaptureScreenShot.Runnable;

import Core.CaptureScreenShot.CaptureScreenShotWithMouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CaptureScreenShotWithMouseRunnable implements Runnable {
    public  int port;
    public  String IP;

    public  void setOutputStream(String ip, int port) {
        this.port = port;
       this.IP = ip;
    }

    @Override
    public void run() {
        try {
            BufferedImage bufferedImage = null;
            Socket socket = null;
            while (true) {
                socket = new Socket(IP, port);
                bufferedImage = CaptureScreenShotWithMouse.tackScreenShot();
                ImageIO.write(bufferedImage, "jpg", socket.getOutputStream());
                socket.getOutputStream().flush();
                socket.getOutputStream().close();
                Thread.sleep(15);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
