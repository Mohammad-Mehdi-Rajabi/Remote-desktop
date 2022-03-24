package CaptureScreenShot;


import MousePosition.MousePosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * this class take an screenshot with mouse pointer
 */
public class ScreenShotWithMouse {
    public static BufferedImage bufferedImage;
    public static MousePosition mousePosition;
    public static BufferedImage mouseImage;


    /**
     * initialized static field when touch class for first time
     */
    static {
        bufferedImage = null;
        mousePosition = null;
        try {
            mouseImage = ImageIO.read(ScreenShotWithMouse.class.getClassLoader().getResource("image/mouse.png"));
        } catch (IOException e) {
            //TODO something
        }

    }

    /**
     * take an screenshot
     *
     * @return screenshot with  mouse pointer
     * @throws AWTException when  Robot class could not take screenshot
     */
    public static BufferedImage tackScreenShot() throws AWTException {
        mousePosition = new MousePosition();
        bufferedImage = new CaptureScreenShot(
                new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())
        ).takeScreenShot();
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(mouseImage, (int) mousePosition.getX(), (int) mousePosition.getY(), null);
        return bufferedImage;
    }
}
