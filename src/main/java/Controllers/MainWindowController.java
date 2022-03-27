package Controllers;

import Core.CaptureScreenShot.Runnable.CaptureScreenShotWithMouseRunnable;

import Core.Node.Server;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.Socket;

public class MainWindowController {
    @FXML
    public Button createServerBtn;
    @FXML
    public Button joinServerBtn;
    Socket socket=null;
    public void createServerBtnOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void joinServerBtnOnAction(ActionEvent actionEvent) {

    }
}
