package Core.Mouse.Mouse.MouseImpl;

import Core.Mouse.Mouse.Mouse;
import Core.Mouse.Scroll.Scroll;

import java.awt.*;
import java.io.Serializable;

public class MouseScroll implements Mouse , Serializable {
    private double deltaY;
    private transient Scroll scroll;

    public MouseScroll(double deltaY){
        this.deltaY = deltaY;

    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public Scroll getScroll() {
        return scroll;
    }

    public void setScroll(Scroll scroll) {
        this.scroll = scroll;
    }

    @Override
    public void run(Robot robot) throws AWTException {
        this.scroll = new Scroll(robot);
        scroll.scrolling(deltaY);
    }
}
