package Core.ScreenShot;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * this class take screenShot
 */
public class CaptureScreenShot {
    private BufferedImage bufferedImage;
    private Rectangle rectangle;
    private Robot robot;

    public CaptureScreenShot(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public CaptureScreenShot() {
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Robot getRobot() {
        return robot;
    }


    /**
     * external thread safe method to take screenShot
     *
     * @return BufferedImage
     */
    public synchronized BufferedImage takeScreenShot() throws AWTException, NullPointerException {
        if (rectangle != null) {
            robot = new Robot();
            this.bufferedImage = robot.createScreenCapture(rectangle);
        } else {
            throw new NullPointerException("Rectangle is null");
        }
        return this.bufferedImage;
    }


    /**
     * static thread safe method to take screenShot using injected rectangle
     * this method doesn't use or init class fields
     *
     * @param rectangle rectangle to screenShot size
     * @return BufferedImage
     * @throws AWTException         if can't create Robot
     * @throws NullPointerException if rectangle was null
     */
    public synchronized static BufferedImage takeScreenShot(Rectangle rectangle) throws AWTException, NullPointerException {
        if (rectangle != null) {
            return new Robot().createScreenCapture(rectangle);
        } else {
            throw new NullPointerException("Rectangle is null");
        }
    }


}
