package Controllers.ControllerImpl;


import Controllers.Controller;
import Core.Image.ByteOfImage.ByteOfImage;
import Core.Property.IP;
import Core.Manager.Server.ManagedServer;
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
                    dataServer = new ServerSocket(ManagedServer.getServers().get("dataTransferServer").getPort());
                    Socket accept = dataServer.accept();
                    DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());
                    DataOutputStream dataOutputStream =
                            new DataOutputStream(accept.getOutputStream());
                    while (true) {
                        String readUTF = dataInputStream.readUTF();
                        if (readUTF.equals("password")) {
                            dataOutputStream.writeUTF(ManagedServer.getDefaultPassword());
                            dataOutputStream.flush();
                        }
                        readUTF = dataInputStream.readUTF();
                        if (readUTF.equals("IPList")) {
                            dataOutputStream.writeUTF(ManagedServer.getEspecialIpsList().size() + "");
                            dataOutputStream.flush();
                            for (IP ip : ManagedServer.getEspecialIpsList()) {
                                dataOutputStream.writeUTF(ip.getIP() + " " + ip.getPassword());
                                dataOutputStream.flush();
                            }
                        }
                        readUTF = dataInputStream.readUTF();
                        if (readUTF.equals("validPassword")) {
                            try {
//                                screenAnimation = new AnimationTimer() {
//                                    ServerSocket serverSocket = new ServerSocket(ManagedServer.getServers().get("screenServer").getPort());
//                                    Socket socket = null;
//
//                                    @Override
//                                    public void handle(long now) {
//                                        try {
//                                            socket = serverSocket.accept();
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                        try {
//                                            ShareScreenWindowController.imageView.setImage(
//                                                    SwingFXUtils.toFXImage(ImageIO.read(socket.getInputStream()), null)
//                                            );
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                };
                                screenAnimation = new AnimationTimer() {
                                    ServerSocket serverSocket = new ServerSocket(ManagedServer.getServers().get("screenServer").getPort());
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
                        }
                    }
                } catch (IOException e) {
                    //TODO something
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void onActionDataTransferBtn(ActionEvent actionEvent) {

    }


    public void startRemoteDesktop() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
