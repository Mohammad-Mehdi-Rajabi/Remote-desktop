package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.ServerSocket;

public class ShareScreenWindowController {
    @FXML
    public ImageView screenImageView;
    @FXML
    public AnchorPane anchorPane;


    public int port;

    @FXML
    public void initialize() {
        screenImageView.fitHeightProperty().bind(anchorPane.heightProperty());
        screenImageView.fitWidthProperty().bind(anchorPane.widthProperty());


        new Thread(new Runnable() {
            ServerSocket serverSocket = null;

            @Override
            public void run() {
                //serverSocket = new ServerSocket();
            }
        }).start();
    }

    public void setPort(int port) {
        this.port = port;
    }
}
