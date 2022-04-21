package Controllers;


import Core.Manager.Client.ManagedClient;
import Core.Manager.Server.ManagedServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ShareScreenWindowController implements Initializable {
    @FXML
    public ImageView screenImageView;
    @FXML
    public BorderPane borderPane;


    public static ServerSocket mouseServerSocket;
    public static Socket mouseSocket;
    public static ImageView imageView;
    public static boolean mouseConnected;
    public static DataOutputStream mouseDataOutputStream;

    static {
        mouseConnected = false;
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
                    mouseServerSocket = new ServerSocket(ManagedServer.getServers().get("mouseServer").getPort());
                    mouseSocket = mouseServerSocket.accept();
                    mouseConnected = true;
                    mouseDataOutputStream =
                            new DataOutputStream(mouseSocket.getOutputStream());
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
                mouseDataOutputStream.writeUTF("move");
                mouseDataOutputStream.flush();
                mouseDataOutputStream.writeDouble(mouseEvent.getX());
                mouseDataOutputStream.flush();
                mouseDataOutputStream.writeDouble(mouseEvent.getY());
                mouseDataOutputStream.flush();
                mouseDataOutputStream.writeDouble(imageView.getFitWidth());
                mouseDataOutputStream.flush();
                mouseDataOutputStream.writeDouble(imageView.getFitHeight());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onMousePressedImageView(MouseEvent mouseEvent) {
        if(mouseEvent.getButton()== MouseButton.PRIMARY){
            try {
                mouseDataOutputStream.writeUTF("primary");
                mouseDataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(mouseEvent.getButton()== MouseButton.SECONDARY){
            try {
                mouseDataOutputStream.writeUTF("secondary");
                mouseDataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(mouseEvent.getButton()== MouseButton.MIDDLE){
            try {
                mouseDataOutputStream.writeUTF("middle");
                mouseDataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onScrollImageView(ScrollEvent scrollEvent) {
        try {
            mouseDataOutputStream.writeUTF("scroll");
            mouseDataOutputStream.flush();
            mouseDataOutputStream.writeDouble(scrollEvent.getDeltaY());
            mouseDataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
