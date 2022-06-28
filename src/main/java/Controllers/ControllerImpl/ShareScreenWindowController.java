package Controllers.ControllerImpl;


import Controllers.Controller;
import Core.Image.ByteOfImage.ByteOfImage;
import Core.KeyBoard.KeyBoard.KeyboardImpl.KeyPressed;
import Core.KeyBoard.KeyBoard.KeyboardImpl.KeyReleased;
import Core.KeyBoard.KeyBoardUtil.JavaFxKeyCodeRewrite;
import Core.Manager.Server.ManagedServerStatic.ManagedServer;
import Core.Manager.ServerType.ServerType;
import Core.Massage.Massage;
import Core.Mouse.Mouse.MouseImpl.*;
import Core.Util.Util;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ShareScreenWindowController implements Initializable, Controller {
    @FXML
    public ImageView screenImageView;
    @FXML
    public AnchorPane borderPane;
    @FXML
    public MenuItem openDataTransfer;


    public static ServerSocket mouseServerSocket;
    public static Socket mouseSocket;
    public static ImageView imageView;
    public static boolean mouseConnected;
    public static ObjectOutputStream mouseObjectOutputStream;
    public static ServerSocket keyBoardServerSocket;
    public static Socket keyBoardSocket;
    public static boolean keyBoardConnected;
    public static ObjectOutputStream keyBoardObjectOutputStream;
    //-----------
    private static AnimationTimer screenAnimation;
    private static ServerSocket dataServer;
    private static Thread thread;

    static {
        mouseConnected = false;
        keyBoardConnected = false;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView = screenImageView;
        screenImageView.fitHeightProperty().bind(borderPane.heightProperty());
        screenImageView.fitWidthProperty().bind(borderPane.widthProperty());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //keyboard connection
                    keyBoardServerSocket = new ServerSocket(ManagedServer.getServers().get(ServerType.KEYBOARD_SERVER).getPort());
                    keyBoardSocket = keyBoardServerSocket.accept();
                    keyBoardConnected = true;
                    keyBoardObjectOutputStream =
                            new ObjectOutputStream(keyBoardSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //mouse connection
                    mouseServerSocket = new ServerSocket(ManagedServer.getServers().get(ServerType.MOUSE_SERVER).getPort());
                    mouseSocket = mouseServerSocket.accept();
                    mouseConnected = true;
                    mouseObjectOutputStream =
                            new ObjectOutputStream(mouseSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
//------------------------------------
        thread = new Thread(new Runnable() {
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
                                            imageView.setImage(
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

    public void onMouseEnteredImageView(MouseEvent mouseEvent) {
        imageView.getScene().setCursor(Cursor.NONE);

    }


    public void onMouseMovedImageView(MouseEvent mouseEvent) {
        if (mouseConnected) {
            try {
                mouseObjectOutputStream.writeObject(new MouseMove(mouseEvent.getX(), mouseEvent.getY(),
                        imageView.getFitWidth(), imageView.getFitHeight()));
                mouseObjectOutputStream.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onMousePressedImageView(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                mouseObjectOutputStream.writeObject(new MousePrimaryKey());
                mouseObjectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            try {
                mouseObjectOutputStream.writeObject(new MouseSecondaryKey());
                mouseObjectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mouseEvent.getButton() == MouseButton.MIDDLE) {
            try {
                mouseObjectOutputStream.writeObject(new MouseMiddleKey());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onScrollImageView(ScrollEvent scrollEvent) {

        try {
            mouseObjectOutputStream.writeObject(new MouseScroll(scrollEvent.getDeltaY()));
            mouseObjectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void onKeyReleasedScene(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.WINDOWS)
            return;
        if (keyBoardConnected) {
            try {
                keyBoardObjectOutputStream.writeObject(
                        new KeyReleased(JavaFxKeyCodeRewrite.KeyCode.getCode(keyEvent.getCode().getName()))
                );
                keyBoardObjectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void onKeyPressedScene(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.WINDOWS)
            return;
        if (keyBoardConnected) {
            try {
                keyBoardObjectOutputStream.writeObject(
                        new KeyPressed(JavaFxKeyCodeRewrite.KeyCode.getCode(keyEvent.getCode().getName()))
                );
                keyBoardObjectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onActionOpenDataTransfer(ActionEvent actionEvent) {
        Util.openWindow(getClass().getClassLoader().getResource("views/FileTransferWindow.fxml"),
                "File Transfer", false);
    }
}
