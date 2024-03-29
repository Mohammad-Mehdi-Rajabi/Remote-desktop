package Core.Mouse.MousePosition.MousePosition;

import java.awt.*;
import java.io.Serializable;

/**
 * this class calculate mouse position
 */
public class MousePosition implements Serializable {
    public static final long serialVersionUID = 1234567890l;

    private double X;
    private double Y;

    /**
     * when create class constructor initialized x and y fields
     */
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
