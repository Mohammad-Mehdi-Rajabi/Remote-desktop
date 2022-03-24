package Mouse;

import java.io.Serializable;

public class MousePosition implements Serializable {

    private double xCordinate;
    private double yCordinate;

    public MousePosition(double xCordinate, double yCordinate) {
        this.xCordinate = xCordinate;
        this.yCordinate = yCordinate;
        ReadMousePosition readMousePosition = new ReadMousePosition(xCordinate,yCordinate);
        readMousePosition.run();
    }

}
