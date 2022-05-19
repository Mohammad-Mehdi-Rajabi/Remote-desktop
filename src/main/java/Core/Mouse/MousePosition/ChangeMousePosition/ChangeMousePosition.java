package Core.Mouse.MousePosition.ChangeMousePosition;

import java.awt.*;


public class ChangeMousePosition{

    private Robot robot;

    public ChangeMousePosition(Robot robot) throws AWTException {
        this.robot=robot;
    }
    public void changePosition(double x, double y){
        robot.mouseMove((int)x,(int)y);

    }
}

