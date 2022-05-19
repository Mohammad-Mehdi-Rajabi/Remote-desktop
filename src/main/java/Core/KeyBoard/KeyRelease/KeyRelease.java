package Core.KeyBoard.KeyRelease;

import java.awt.*;

public class KeyRelease {
    private Robot robot;
    private int keyCode;

    public KeyRelease(Robot robot, int keyCode) {
        this.robot = robot;
        this.keyCode = keyCode;
    }

    public void release(){
        robot.keyRelease(keyCode);
    }
}
