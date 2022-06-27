package Controllers.ControllerImpl;


import Controllers.Controller;
import Core.Image.ByteOfImage.ByteOfImage;
import Core.Manager.ServerType.ServerType;
import Core.Manager.Server.ManagedServerStatic.ManagedServer;
import Core.Massage.Massage;
import Core.ScreenShot.CaptureScreenShotWithMouse;
import Core.Util.Util;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ServerControlPanelWindowController implements Initializable, Controller {
    @FXML
    public Button start;
    @FXML
    public Button stop;
    @FXML
    public Button dataTransfer;

    private static AnimationTimer screenAnimation;
    private static ServerSocket dataServer;


    public void onActionStopBtn(ActionEvent actionEvent) {

    }

    public void onActionStartBtn(ActionEvent actionEvent) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataServer = new ServerSocket(ManagedServer.getServers().get(ServerType.DATA_TRANSFER_SERVER).getPort());
                    Socket accept = dataServer.accept();
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(accept.getOutputStream());
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(accept.getInputStream());
                    objectOutputStream.writeObject(new Core.Manager.Server.ManagedServerNonStatic.ManagedServer());
                    objectOutputStream.flush();

                    while (true) {
                        Massage massage = (Massage) objectInputStream.readObject();
                        if (massage.getMassage().equals(Massage.MassageType.VALID_PASSWORD.getMassage())) {
                            try {
                                screenAnimation = new AnimationTimer() {
                                    ServerSocket serverSocket = new ServerSocket(ManagedServer.getServers().get(ServerType.SCREEN_SERVER).getPort());
                                    Socket socket = null;
                                    ObjectInputStream objectInputStream = null;
                                    ByteOfImage byteOfImage = null;

                                    @Override
                                    public void handle(long now) {
                                        try {
                                            socket = serverSocket.accept();
                                            objectInputStream = new ObjectInputStream(socket.getInputStream());
                                            byteOfImage = (ByteOfImage) objectInputStream.readObject();
                                            ShareScreenWindowController.imageView.setImage(
                                                    SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(byteOfImage.getByteOfImage())),
                                                            null)
                                            );
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                screenAnimation.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dataServer.close();
                            break;
                        }

                    }
                    dataServer = new ServerSocket(ManagedServer.getServers().get(ServerType.DATA_TRANSFER_SERVER).getPort());
                    ManagedServer.setDataTransferSocket(dataServer.accept());
                } catch (IOException e) {
                    //TODO something
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void onActionDataTransferBtn(ActionEvent actionEvent) {
        Util.openWindow(getClass().getClassLoader().getResource("views/FileTransferWindow.fxml"),
                "File Transfer", false);
    }


    public void startRemoteDesktop() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
