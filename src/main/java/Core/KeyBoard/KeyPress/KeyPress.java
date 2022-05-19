package Core.KeyBoard.KeyPress;

import java.awt.*;


public class KeyPress {
    private Robot robot;
    private int keyCode;

    public KeyPress(Robot robot, int keyCode) {
        this.robot = robot;
        this.keyCode = keyCode;
    }

    public void press() {
        robot.keyPress(keyCode);
    }

}
