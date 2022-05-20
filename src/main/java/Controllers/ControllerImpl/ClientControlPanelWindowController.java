package Controllers.ControllerImpl;

import Controllers.Controller;
import Core.KeyBoard.KeyBoard.Keyboard;
import Core.Manager.Client.ManagedClientStatic.ManagedClient;
import Core.Manager.ServerType.ServerType;
import Core.Mouse.Mouse.Mouse;
import Core.ScreenShot.Runnable.CaptureScreenShotWithMouseRunnable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientControlPanelWindowController implements Initializable, Controller {
    public static Thread screenThread;
    public static Thread mouseThread;
    public static Thread keyBoardThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onActionStopBtn(ActionEvent actionEvent) {
        screenThread.stop();
        mouseThread.stop();
        keyBoardThread.stop();
    }

    public void onActionStartBtn(ActionEvent actionEvent) {
        CaptureScreenShotWithMouseRunnable captureScreenShotWithMouseRunnable =
                new CaptureScreenShotWithMouseRunnable();
        captureScreenShotWithMouseRunnable.setOutputStream(ManagedClient.getIp(),
                ManagedClient.getServers().get(ServerType.SCREEN_SERVER).getPort());
        screenThread = new Thread(captureScreenShotWithMouseRunnable);
        screenThread.start();
        mouseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket mouseSocket = new Socket(ManagedClient.getIp(),
                            ManagedClient.getServers().get(ServerType.MOUSE_SERVER).getPort());
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(mouseSocket.getInputStream());
                    while (true) {
                        Object o = objectInputStream.readObject();
                        ((Mouse) o).run(new Robot());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        });
        mouseThread.start();
        keyBoardThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket keyBoardSocket = new Socket(ManagedClient.getIp(),
                            ManagedClient.getServers().get(ServerType.KEYBOARD_SERVER).getPort());
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(keyBoardSocket.getInputStream());
                    while (true) {
                        Object o = objectInputStream.readObject();
                        ((Keyboard) o).run(new Robot());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        });
        keyBoardThread.start();
    }


}
