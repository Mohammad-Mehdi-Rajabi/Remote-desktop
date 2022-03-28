package Controllers;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.Socket;

public class MainWindowController {
    @FXML
    public Button createServerBtn;
    @FXML
    public Button joinServerBtn;
    Socket socket = null;

    public void createServerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Main.switchScene(getClass().getClassLoader().getResource("views/SetUpServerWindow.fxml"), "Setup Server", false);
    }

    public void joinServerBtnOnAction(ActionEvent actionEvent) {
        Main.switchScene(getClass().getClassLoader().getResource("views/SetUpClientWindow.fxml"), "Setup Client", false);
    }
}
