package Controllers.ControllerImpl;

import Controllers.Controller;
import Core.Util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable, Controller {
    @FXML
    public Button createServerBtn;
    @FXML
    public Button joinServerBtn;
    Socket socket = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createServerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Util.switchWindow(getClass().getClassLoader().getResource("views/SetUpServerWindow.fxml"),
                "Setup Server", false);
    }

    public void joinServerBtnOnAction(ActionEvent actionEvent) {
        Util.switchWindow(getClass().getClassLoader().getResource("views/SetUpClientWindow.fxml"),
                "Setup Client", false);
    }


}
