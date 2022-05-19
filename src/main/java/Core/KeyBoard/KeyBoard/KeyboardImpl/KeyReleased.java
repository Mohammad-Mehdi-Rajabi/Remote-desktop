package Core.KeyBoard.KeyBoard.KeyboardImpl;

import Core.KeyBoard.KeyBoard.Keyboard;
import Core.KeyBoard.KeyRelease.KeyRelease;

import java.awt.*;
import java.io.Serializable;

public class KeyReleased implements Keyboard, Serializable {
    private transient KeyRelease keyRelease;
    private int keyCode;

    public KeyReleased(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public void run(Robot robot) {
        keyRelease = new KeyRelease(robot, keyCode);
        keyRelease.release();
    }

    public KeyRelease getKeyRelease() {
        return keyRelease;
    }

    public void setKeyRelease(KeyRelease keyRelease) {
        this.keyRelease = keyRelease;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
