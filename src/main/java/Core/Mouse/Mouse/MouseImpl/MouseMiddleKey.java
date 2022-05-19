package Core.Mouse.Mouse.MouseImpl;

import Core.Mouse.Clicked.Clicked;
import Core.Mouse.Mouse.Mouse;
import javafx.scene.input.MouseButton;

import java.awt.*;
import java.io.Serializable;

public class MouseMiddleKey implements Mouse, Serializable {
    private transient Clicked clicked;

    public MouseMiddleKey() {

    }

    @Override
    public void run(Robot robot) throws AWTException {
        this.clicked = new Clicked(robot);
        clicked.click(MouseButton.MIDDLE);
    }
}
