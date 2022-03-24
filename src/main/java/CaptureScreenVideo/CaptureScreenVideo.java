package CaptureScreenVideo;

import CaptureScreenShot.CaptureScreenShot;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaptureScreenVideo implements Runnable {
    private CaptureScreenShot captureScreenShot;
    private BufferedImage bufferedImage;
    private int FPS = 20;

    /**
     * constructor get CaptureScreenShot obj and use it to
     * @param captureScreenShot
     * @throws AWTException
     */
    public CaptureScreenVideo(CaptureScreenShot captureScreenShot) throws AWTException {
        this.captureScreenShot = captureScreenShot;
    }

    /**
     * this method take screenshot in a separate thread and wait 20 ms and do it again
     * and save screenshot in bufferedImage field
     */
    @Override
    public void run() {
        try {
            this.bufferedImage=this.captureScreenShot.takeScreenShot();
            Thread.sleep(this.FPS);
        } catch (AWTException e) {
            //TODO something
            e.printStackTrace();
        } catch (InterruptedException e) {
            //TODO something
            e.printStackTrace();
        }
    }

    public CaptureScreenShot getCaptureScreenShot() {
        return captureScreenShot;
    }

    public void setCaptureScreenShot(CaptureScreenShot captureScreenShot) {
        this.captureScreenShot = captureScreenShot;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }
}
