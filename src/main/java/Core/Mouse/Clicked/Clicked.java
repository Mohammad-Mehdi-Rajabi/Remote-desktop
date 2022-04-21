package Core.Mouse.Clicked;

import javafx.scene.input.MouseButton;

import java.awt.*;
import java.awt.event.InputEvent;

public class Clicked {
    private Robot robot;

    public Clicked() throws AWTException {
        robot = new Robot();
    }

    public void click(MouseButton mouseButton) {
        if (mouseButton == MouseButton.PRIMARY) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        if (mouseButton == MouseButton.SECONDARY) {
            robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        }
        if (mouseButton == MouseButton.MIDDLE) {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }

    }
}
