package Controllers.ControllerImpl;


import Controllers.Controller;
import Core.Manager.Server.ManagedServerNonStatic.ManagedServer;
import Core.Manager.ServerType.ServerType;
import Core.Massage.Massage;
import Core.Node.NodeImpl.Server;
import Core.Property.IP;
import Core.Util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import Core.Manager.Client.ManagedClientStatic.ManagedClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
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

    }

    public void onActionJoinBtn(ActionEvent actionEvent) {
        ManagedClient.setPassword(passwordTxt.getText());
        ManagedClient.setIp(IPTxt.getText());
        Map<ServerType, Server> servers = ManagedClient.getServers();
        if (!portCheckBox.isSelected()) {
            servers.put(ServerType.SCREEN_SERVER, new Server(Integer.parseInt(portTxt.getText())));
            servers.put(ServerType.MOUSE_SERVER, new Server(Integer.parseInt(portTxt.getText()) + 1));
            servers.put(ServerType.KEYBOARD_SERVER, new Server(Integer.parseInt(portTxt.getText()) + 2));
            servers.put(ServerType.DATA_TRANSFER_SERVER, new Server(Integer.parseInt(portTxt.getText()) + 3));
        } else {
            servers.put(ServerType.SCREEN_SERVER, new Server(Integer.parseInt(screenPortTxt.getText())));
            servers.put(ServerType.MOUSE_SERVER, new Server(Integer.parseInt(mousePortTxt.getText())));
            servers.put(ServerType.KEYBOARD_SERVER, new Server(Integer.parseInt(keyBoardPortTxt.getText())));
            servers.put(ServerType.DATA_TRANSFER_SERVER, new Server(Integer.parseInt(dataTransferPortTxt.getText())));
        }
        try {
            Socket socket = new Socket(IPTxt.getText(), servers.get(ServerType.DATA_TRANSFER_SERVER).getPort());
            ManagedClient.setDataTransferSocket(socket);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            ManagedServer managedServer = (ManagedServer) objectInputStream.readObject();
            if (managedServer.getEspecialIpsList().size() > 0) {
                for (IP ip : managedServer.getEspecialIpsList()) {
                    if (Util.getSystemIP().equals(ip.getIP()) && passwordTxt.getText().equals(ip.getPassword())) {
                        objectOutputStream.writeObject(new Massage(Massage.MassageType.VALID_PASSWORD));
                        Util.switchWindow(getClass().getClassLoader().getResource("views/ClientControlPanelWindow.fxml"),
                                "Control Panel", false);
                    }else {

                    }
                }
            } else {
                if (passwordTxt.getText().equals(managedServer.getDefaultPassword())){
                    objectOutputStream.writeObject(new Massage(Massage.MassageType.VALID_PASSWORD));
                    Util.switchWindow(getClass().getClassLoader().getResource("views/ClientControlPanelWindow.fxml"),
                            "Control Panel", false);
                }else {

                }
            }
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
