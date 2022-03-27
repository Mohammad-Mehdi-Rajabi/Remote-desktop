package Controllers;

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
    Socket socket=null;
    public void createServerBtnOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void joinServerBtnOnAction(ActionEvent actionEvent) {

    }
}
