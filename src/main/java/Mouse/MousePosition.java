package Mouse;

import java.awt.*;
import java.io.Serializable;

public class MousePosition implements Serializable {

    private double X;
    private double Y;

    public MousePosition() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        this.X= location.getX();
        this.Y=location.getY();
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
