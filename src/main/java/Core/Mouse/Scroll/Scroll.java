package Core.Mouse.Scroll;

import java.awt.*;

public class Scroll {
    private Robot robot;

    public Scroll() throws AWTException {
        robot=new Robot();
    }
    public void scrolling(double x){
        if(x<0)
            robot.mouseWheel(1);
        if(x>0)
            robot.mouseWheel(-1);

    }
}
