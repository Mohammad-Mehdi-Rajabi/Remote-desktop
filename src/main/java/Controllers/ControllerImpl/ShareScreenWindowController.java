package Controllers.ControllerImpl;


import Controllers.Controller;
import Core.KeyBoard.KeyBoard.KeyboardImpl.KeyPressed;
import Core.KeyBoard.KeyBoard.KeyboardImpl.KeyReleased;
import Core.KeyBoard.KeyBoardUtil.JavaFxKeyCodeRewrite;
import Core.Manager.Server.ManagedServer;
import Core.Mouse.Mouse.MouseImpl.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ShareScreenWindowController implements Initializable, Controller {
    @FXML
    public ImageView screenImageView;
    @FXML
    public BorderPane borderPane;

    public static ServerSocket mouseServerSocket;
    public static Socket mouseSocket;
    public static ImageView imageView;
    public static boolean mouseConnected;
    public static ObjectOutputStream mouseObjectOutputStream;
    public static ServerSocket keyBoardServerSocket;
    public static Socket keyBoardSocket;
    public static boolean keyBoardConnected;
    public static ObjectOutputStream keyBoardObjectOutputStream;

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
                    keyBoardServerSocket = new ServerSocket(ManagedServer.getServers().get("keyBoardServer").getPort());
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
                    mouseServerSocket = new ServerSocket(ManagedServer.getServers().get("mouseServer").getPort());
                    mouseSocket = mouseServerSocket.accept();
                    mouseConnected = true;
                    mouseObjectOutputStream =
                            new ObjectOutputStream(mouseSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

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
        if(keyBoardConnected) {
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
        if(keyBoardConnected) {
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

}
