package Core.Mouse.Mouse.MouseImpl;

import Core.Mouse.Mouse.Mouse;
import Core.Mouse.MousePosition.ChangeMousePosition.ChangeMousePosition;

import java.awt.*;
import java.io.Serializable;

public class MouseMove implements Mouse , Serializable {
    private double x;
    private double y;
    private double screenWidth;
    private double screenHeight;
    private double sceneWidth;
    private double sceneHeight;
    private transient ChangeMousePosition changeMousePosition;

    public MouseMove(double x, double y, double sceneWidth, double screenHeight)  {
        this.x = x;
        this.y = y;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = screenHeight;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(double screenHeight) {
        this.screenHeight = screenHeight;
    }

    public double getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(double sceneWidth) {
        this.sceneWidth = sceneWidth;
    }

    public double getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(double sceneHeight) {
        this.sceneHeight = sceneHeight;
    }

    public ChangeMousePosition getChangeMousePosition() {
        return changeMousePosition;
    }

    public void setChangeMousePosition(ChangeMousePosition changeMousePosition) {
        this.changeMousePosition = changeMousePosition;
    }

    @Override
    public void run(Robot robot) throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
        this.changeMousePosition = new ChangeMousePosition(robot);
        changeMousePosition.changePosition(x * (screenWidth / sceneWidth),
                y * (screenHeight / sceneHeight));
    }
}
