package Controllers.ControllerImpl;


import Controllers.Controller;
import Core.Node.NodeImpl.Server;
import Core.Util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import Core.Manager.Client.ManagedClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class SetUpClientWindowController implements Initializable, Controller {
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        IPWrongLbl.setText("IP was incorrect");
    }

    public void onActionJoinBtn(ActionEvent actionEvent) {
        wrongPasswordLbl.setVisible(false);
        IPWrongLbl.setVisible(false);
        ManagedClient.setPassword(passwordTxt.getText());
        ManagedClient.setIp(IPTxt.getText());
        Map<String, Server> servers = ManagedClient.getServers();
        if (!portCheckBox.isSelected()) {
            servers.put("screenServer", new Server(Integer.parseInt(portTxt.getText())));
            servers.put("mouseServer", new Server(Integer.parseInt(portTxt.getText()) + 1));
            servers.put("keyBoardServer", new Server(Integer.parseInt(portTxt.getText()) + 2));
            servers.put("dataTransferServer", new Server(Integer.parseInt(portTxt.getText()) + 3));
        } else {
            servers.put("screenServer", new Server(Integer.parseInt(screenPortTxt.getText())));
            servers.put("mouseServer", new Server(Integer.parseInt(mousePortTxt.getText())));
            servers.put("keyBoardServer", new Server(Integer.parseInt(keyBoardPortTxt.getText())));
            servers.put("dataTransferServer", new Server(Integer.parseInt(dataTransferPortTxt.getText())));
        }
        try {
            Socket socket = new Socket(IPTxt.getText(), servers.get("dataTransferServer").getPort());
            DataOutputStream dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("password");
            dataOutputStream.flush();

            DataInputStream dataInputStream =
                    new DataInputStream(socket.getInputStream());
            String password = dataInputStream.readUTF();

            dataOutputStream.writeUTF("IPList");
            dataOutputStream.flush();
            String countOfIP = dataInputStream.readUTF();
            List<String> ipsAndPasswords = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(countOfIP); i++) {
                ipsAndPasswords.add(dataInputStream.readUTF());
            }
            if (ipsAndPasswords.size() == 0) {
                if (password.equals(passwordTxt.getText())) {
                    dataOutputStream.writeUTF("validPassword");
                    dataOutputStream.flush();
                    Util.switchWindow(getClass().getClassLoader().getResource("views/ClientControlPanelWindow.fxml"),
                            "Control Panel", false);
                } else {
                    wrongPasswordLbl.setVisible(true);
                    socket.close();
                }
            } else {
                String[] ipAndPassword;
                for (String s : ipsAndPasswords) {
                    ipAndPassword = s.split(" ");
                    if (ipAndPassword[0].equals(Util.getSystemIP())) {
                        if (ipAndPassword[1].equals(passwordTxt.getText())) {
                            dataOutputStream.writeUTF("validPassword");
                            dataOutputStream.flush();
                            Util.switchWindow(getClass().getClassLoader().getResource("views/ClientControlPanelWindow.fxml"),
                                    "Control Panel", false);
                        } else {
                            wrongPasswordLbl.setVisible(true);
                            socket.close();
                        }
                    } else {
                        IPWrongLbl.setVisible(true);
                        socket.close();
                    }
                }
            }

        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
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
        Util.switchWindow(getClass().getClassLoader().getResource("views/MainWindow.fxml"),
                "Remote Desktop Application", false);
    }

}
