package CaptureScreenShot;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CaptureScreenShotTest {
    @Test
    void takeScreenShotTest() {
        // create directory if not exists
        File directory = new File("test_pic");
        if (!directory.exists()) {
            directory.mkdir();
        }
        // delete all files in test_pic directory
        Util.Util.deleteFiles(new File("test_pic"));

        CaptureScreenShot captureScreenShot =
                new CaptureScreenShot(
                        new Rectangle(
                                Toolkit.getDefaultToolkit().getScreenSize()
                        )
                );
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = captureScreenShot.takeScreenShot();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(bufferedImage, "jpg", new File("test_pic/takeScreenShotTest_Result.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(Files.exists(Paths.get("test_pic/takeScreenShotTest_Result.jpg")));
    }

    @Test
    void takeScreenShotTest_static() {
        // create directory if not exists
        File directory = new File("test_pic");
        if (!directory.exists()) {
            directory.mkdir();
        }
        // delete all files in test_pic directory
        Util.Util.deleteFiles(new File("test_pic"));
        try {
            BufferedImage bufferedImage = CaptureScreenShot.takeScreenShot(
                    new Rectangle(
                            Toolkit.getDefaultToolkit().getScreenSize()
                    )
            );
            ImageIO.write(bufferedImage, "jpg", new File("test_pic/takeScreenShotTest_static_Result.jpg"));
        } catch (AWTException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(Files.exists(Paths.get("test_pic/takeScreenShotTest_static_Result.jpg")));
    }
}