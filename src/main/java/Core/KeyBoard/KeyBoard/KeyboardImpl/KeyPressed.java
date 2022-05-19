package Core.KeyBoard.KeyBoard.KeyboardImpl;

import Core.KeyBoard.KeyBoard.Keyboard;
import Core.KeyBoard.KeyPress.KeyPress;

import java.awt.*;
import java.io.Serializable;

public class KeyPressed implements Keyboard, Serializable {
    private transient KeyPress keyPress;
    private int keyCode;

    public KeyPressed(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public void run(Robot robot) {
        keyPress = new KeyPress(robot, keyCode);
        keyPress.press();
    }

    public KeyPress getKeyPress() {
        return keyPress;
    }

    public void setKeyPress(KeyPress keyPress) {
        this.keyPress = keyPress;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
