package Core.Mouse.MousePosition.ChangeMousePosition;

import java.awt.*;

public class ChangeMousePosition {

    private Robot robot;

    public ChangeMousePosition() throws AWTException {
        robot=new Robot();
    }
    public void changePosition(double x, double y){
        robot.mouseMove((int)x,(int)y);

    }
}

