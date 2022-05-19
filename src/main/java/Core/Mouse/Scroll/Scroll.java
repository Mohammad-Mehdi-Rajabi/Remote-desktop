package Core.Mouse.Scroll;

import java.awt.*;

public class Scroll {
    private Robot robot;

    public Scroll(Robot robot) throws AWTException {
        this.robot=robot;
    }
    public void scrolling(double x){
        if(x<0)
            robot.mouseWheel(1);
        if(x>0)
            robot.mouseWheel(-1);

    }
}
