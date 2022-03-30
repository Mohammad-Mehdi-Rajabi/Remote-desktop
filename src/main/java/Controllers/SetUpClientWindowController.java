package Controllers;

import Core.Node.Client;
import Core.Property.IP;
import Core.Util.Util;
import Main.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.xml.ws.spi.http.HttpContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SetUpClientWindowController {
    @FXML
    public TextField passwordTxt;
    @FXML
    public Button createBtn;
    @FXML
    public CheckBox portCheckBox;
    @FXML
    public TextField screenPortTxt;
    @FXML
    public TextField mousePortTxt;
    @FXML
    public TextField keyBoardPortTxt;
    @FXML
    public TextField dataTransferPortTxt;
    @FXML
    public Button backBtn;
    @FXML
    public TextField portTxt;
    @FXML
    public TextField IPTxt;
    @FXML
    public Label screenPortLbl;
    @FXML
    public Label mousePortLbl;
    @FXML
    public Label keyBoardPortLbl;
    @FXML
    public Label dataTransferPortLbl;
    @FXML
    public Label wrongPasswordLbl;
    public Label IPWrongLbl;


    @FXML
    public void initialize() {
        /**
         *  port text fields just get number
         */
        portTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        dataTransferPortTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        keyBoardPortTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        mousePortTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        screenPortTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        if (!portCheckBox.isSelected()) {
            screenPortTxt.setEditable(false);
            screenPortTxt.setDisable(true);
            mousePortTxt.setEditable(false);
            mousePortTxt.setDisable(true);
            keyBoardPortTxt.setEditable(false);
            keyBoardPortTxt.setDisable(true);
            dataTransferPortTxt.setEditable(false);
            dataTransferPortTxt.setDisable(true);
            portTxt.setDisable(false);
            portTxt.setEditable(true);
            portTxt.setDisable(false);
            portTxt.setEditable(true);
            screenPortLbl.setDisable(true);
            mousePortLbl.setDisable(true);
            keyBoardPortLbl.setDisable(true);
            dataTransferPortLbl.setDisable(true);
        }
        wrongPasswordLbl.setVisible(false);
        wrongPasswordLbl.setText("your entry password was incorrect");
        IPWrongLbl.setVisible(false);
        IPWrongLbl.setText("IP or Port was incorrect");
    }

    public void onActionCreateBtn(ActionEvent actionEvent) {
        wrongPasswordLbl.setVisible(false);
        IPWrongLbl.setVisible(false);
        Client dataTransferClient = null;
        if (portCheckBox.isSelected()) {
            dataTransferClient = new Client(IPTxt.getText(), Integer.parseInt(dataTransferPortTxt.getText()));
        } else {
            dataTransferClient = new Client(IPTxt.getText(), Integer.parseInt(portTxt.getText()) + 3);
        }
        try {
            Socket socket = new Socket(dataTransferClient.getAddress(), dataTransferClient.getPort());
            DataOutputStream dataOutputStream=
                    new DataOutputStream(socket.getOutputStream());
            //TODO is better to use hash code for send password
            dataOutputStream.writeUTF("Password="+passwordTxt.getText());
            dataOutputStream.flush();
            dataOutputStream.close();
            if (new DataInputStream(socket.getInputStream()).readUTF().equals("accept")) {
                Main.switchScene(getClass().getClassLoader().getResource("views/ShareScreenWindow.fxml"), "Remote Desktop Application", true);
            } else {
                passwordTxt.clear();
                wrongPasswordLbl.setVisible(true);
            }
        } catch (IOException e) {
            IPTxt.clear();
            if(portCheckBox.isSelected()){
                dataTransferPortTxt.clear();
                screenPortTxt.clear();
                mousePortTxt.clear();
               keyBoardPortTxt.clear();
            }else {
                portTxt.clear();
            }
            IPWrongLbl.setVisible(true);
        }


    }

    public void onMouseClickedPortCheckBox(MouseEvent mouseEvent) {
        if (portCheckBox.isSelected()) {
            screenPortTxt.setEditable(true);
            screenPortTxt.setDisable(false);
            mousePortTxt.setEditable(true);
            mousePortTxt.setDisable(false);
            keyBoardPortTxt.setEditable(true);
            keyBoardPortTxt.setDisable(false);
            dataTransferPortTxt.setEditable(true);
            dataTransferPortTxt.setDisable(false);
            portTxt.setDisable(true);
            portTxt.setEditable(false);
            portTxt.setDisable(true);
            portTxt.setEditable(false);
            screenPortLbl.setDisable(false);
            mousePortLbl.setDisable(false);
            keyBoardPortLbl.setDisable(false);
            dataTransferPortLbl.setDisable(false);
            portTxt.setEditable(false);
            portTxt.setDisable(true);
        } else {
            screenPortTxt.setEditable(false);
            screenPortTxt.setDisable(true);
            mousePortTxt.setEditable(false);
            mousePortTxt.setDisable(true);
            keyBoardPortTxt.setEditable(false);
            keyBoardPortTxt.setDisable(true);
            dataTransferPortTxt.setEditable(false);
            dataTransferPortTxt.setDisable(true);
            portTxt.setDisable(false);
            portTxt.setEditable(true);
            portTxt.setDisable(false);
            portTxt.setEditable(true);
            screenPortLbl.setDisable(true);
            mousePortLbl.setDisable(true);
            keyBoardPortLbl.setDisable(true);
            dataTransferPortLbl.setDisable(true);
            portTxt.setEditable(true);
            portTxt.setDisable(false);
        }
    }

    public void onActionBackBtn(ActionEvent actionEvent) {
        Main.switchScene(getClass().getClassLoader().getResource("views/MainWindow.fxml"), "Remote Desktop Application", false);
    }
}
