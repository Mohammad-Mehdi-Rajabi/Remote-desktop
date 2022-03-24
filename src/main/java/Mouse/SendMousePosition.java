package Mouse;

import java.awt.*;

public class SendMousePosition implements Runnable{

    private double xCordinate;
    private double yCordinate;

    public SendMousePosition(double xCordinate, double yCordinate) {
        this.xCordinate = xCordinate;
        this.yCordinate = yCordinate;
    }

    @Override
    public void run() {
        while (true) {
            Point mouseCursor = MouseInfo.getPointerInfo().getLocation();
            xCordinate = mouseCursor.getX();
            yCordinate = mouseCursor.getY();
            MousePosition mousePosition = new MousePosition(xCordinate, yCordinate);
        }
    }
}
