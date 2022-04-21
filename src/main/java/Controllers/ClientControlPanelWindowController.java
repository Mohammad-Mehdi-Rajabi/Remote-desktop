package Controllers;

import Core.Manager.Client.ManagedClient;
import Core.Mouse.Clicked.Clicked;
import Core.Mouse.MousePosition.ChangeMousePosition.ChangeMousePosition;
import Core.Mouse.Scroll.Scroll;
import Core.ScreenShot.Runnable.CaptureScreenShotWithMouseRunnable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientControlPanelWindowController implements Initializable {
    public static Thread screenThread;
    public static Thread mouseThread;
    public static double x;
    public static double y;
    public static double screenWidth;
    public static double screenHeight;
    public static double sceneWidth;
    public static double sceneHeight;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onActionStopBtn(ActionEvent actionEvent) {
        screenThread.stop();
        mouseThread.stop();
    }

    public void onActionStartBtn(ActionEvent actionEvent) {
        CaptureScreenShotWithMouseRunnable captureScreenShotWithMouseRunnable =
                new CaptureScreenShotWithMouseRunnable();
        captureScreenShotWithMouseRunnable.setOutputStream(ManagedClient.getIp(),
                ManagedClient.getServers().get("screenServer").getPort());
        screenThread = new Thread(captureScreenShotWithMouseRunnable);
        screenThread.start();
        mouseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket mouseSocket = new Socket(ManagedClient.getIp(),
                            ManagedClient.getServers().get("mouseServer").getPort());
                    DataInputStream dataInputStream =
                            new DataInputStream(mouseSocket.getInputStream());
                    ChangeMousePosition changeMousePosition =
                            new ChangeMousePosition();
                    Scroll scroll=
                            new Scroll();
                    Clicked clicked=
                            new Clicked();
                    while (true) {
                        String readUTF = dataInputStream.readUTF();
                        if (readUTF.equals("move")) {
                            x = dataInputStream.readDouble();
                            y = dataInputStream.readDouble();
                            sceneWidth = dataInputStream.readDouble();
                            sceneHeight = dataInputStream.readDouble();
                            changeMousePosition.changePosition(x * (screenWidth / sceneWidth),
                                    y * (screenHeight / sceneHeight));
                        }
                        if (readUTF.equals("scroll")) {
                            scroll.scrolling(dataInputStream.readDouble());
                        }
                        if (readUTF.equals("primary")) {
                            clicked.click(MouseButton.PRIMARY);
                        }
                        if (readUTF.equals("secondary")) {
                            x = dataInputStream.readDouble();
                            y = dataInputStream.readDouble();
                            sceneWidth = dataInputStream.readDouble();
                            sceneHeight = dataInputStream.readDouble();
                            changeMousePosition.changePosition(x * (screenWidth / sceneWidth),
                                    y * (screenHeight / sceneHeight));
                        }
                        if (readUTF.equals("middle")) {
                            x = dataInputStream.readDouble();
                            y = dataInputStream.readDouble();
                            sceneWidth = dataInputStream.readDouble();
                            sceneHeight = dataInputStream.readDouble();
                            changeMousePosition.changePosition(x * (screenWidth / sceneWidth),
                                    y * (screenHeight / sceneHeight));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }

            }
        });
        mouseThread.start();
    }


}
