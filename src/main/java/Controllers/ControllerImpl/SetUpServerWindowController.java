package Controllers.ControllerImpl;

import Controllers.Controller;
import Core.Manager.ServerType.ServerType;
import Core.Node.NodeImpl.Server;
import Core.Property.IP;
import Core.Util.Util;
import Core.Manager.Server.ManagedServerStatic.ManagedServer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class SetUpServerWindowController implements Initializable, Controller {

    @FXML
    public TextField portTxt;
    @FXML
    public TextField IPTxt;
    @FXML
    public CheckBox IPCheckBox;
    @FXML
    public TextField especialIPTxt;
    @FXML
    public TextField especialPasswordTxt;
    @FXML
    public Button especialAddBtn;
    @FXML
    public javafx.scene.control.TableView tableView;
    @FXML
    public TextField passwordTxt;
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
    public Button createBtn;
    @FXML
    public CheckBox defaultPasswordCheckBox;
    @FXML
    public Button backBtn;
    @FXML
    public Label screenPortLbl;
    @FXML
    public Label mousePortLbl;
    @FXML
    public Label keyBoardPortLbl;
    @FXML
    public Label dataTransferPortLbl;

    TableColumn<IP, String> tableColumnIP;
    TableColumn<IP, String> tableColumnPassword;
    ObservableList<IP> observableList;



    public static  Stage shareScreenWindowStage;



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
        portCheckBox.textProperty().addListener(new ChangeListener<String>() {
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

        if (!IPCheckBox.isSelected()) {
            especialAddBtn.setDisable(true);
            especialIPTxt.setEditable(false);
            especialIPTxt.setDisable(true);
            tableView.setDisable(true);
            tableView.setEditable(false);
            especialPasswordTxt.setDisable(true);
            especialPasswordTxt.setEditable(false);
            defaultPasswordCheckBox.setDisable(true);
        }
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
            screenPortLbl.setDisable(true);
            mousePortLbl.setDisable(true);
            keyBoardPortLbl.setDisable(true);
            dataTransferPortLbl.setDisable(true);

        }
        IPTxt.setEditable(false);
        IPTxt.setText(Util.getSystemIP());
        tableColumnPassword = new TableColumn<>("Password");
        tableColumnIP = new TableColumn<>("IP");
        tableColumnPassword.setCellValueFactory(new PropertyValueFactory<IP, String>("password"));
        tableColumnIP.setCellValueFactory(new PropertyValueFactory<IP, String>("IP"));
        tableView.getColumns().addAll(tableColumnIP, tableColumnPassword);
        tableColumnIP.setMinWidth(100.0);
        tableColumnPassword.setMinWidth(158.0);
        observableList = FXCollections.observableArrayList();
        tableView.setPlaceholder(new Label("No especial IP added yet "));
        /**
         * add delete menu to table view
         */
        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IP ip = (IP) tableView.getSelectionModel().getSelectedItem();
                observableList.remove(ip);
            }
        });
        /**
         * add edit menu to table view
         */
        MenuItem menuItemEdit = new MenuItem("Edit");
        menuItemEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IP ip = (IP) tableView.getSelectionModel().getSelectedItem();
                showEditPage(ip);
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(menuItemEdit, menuItemDelete);
        tableView.setContextMenu(contextMenu);
    }


    public void onMouseClickedIPCheckBox(MouseEvent mouseEvent) {
        if (IPCheckBox.isSelected()) {
            especialAddBtn.setDisable(false);
            especialIPTxt.setEditable(true);
            especialIPTxt.setDisable(false);
            tableView.setDisable(false);
            especialPasswordTxt.setDisable(false);
            especialPasswordTxt.setEditable(true);
            passwordTxt.setDisable(true);
            passwordTxt.setEditable(false);
            defaultPasswordCheckBox.setDisable(false);
        } else {
            especialAddBtn.setDisable(true);
            especialIPTxt.setEditable(false);
            especialIPTxt.setDisable(true);
            tableView.setDisable(true);
            especialPasswordTxt.setDisable(true);
            especialPasswordTxt.setEditable(false);
            passwordTxt.setDisable(false);
            passwordTxt.setEditable(true);
            defaultPasswordCheckBox.setDisable(true);
        }
    }

    public void especialAddBtn(ActionEvent actionEvent) {
        if (!defaultPasswordCheckBox.isSelected()) {
            observableList.add(new IP(especialIPTxt.getText(), especialPasswordTxt.getText()));
            tableView.setItems(observableList);
            especialIPTxt.clear();
            especialPasswordTxt.clear();
        } else {
            observableList.add(new IP(especialIPTxt.getText(), passwordTxt.getText()));
            tableView.setItems(observableList);
            especialIPTxt.clear();
            especialPasswordTxt.clear();
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
            portTxt.setEditable(false);
            screenPortLbl.setDisable(false);
            mousePortLbl.setDisable(false);
            keyBoardPortLbl.setDisable(false);
            dataTransferPortLbl.setDisable(false);

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
            portTxt.setEditable(true);
            screenPortLbl.setDisable(true);
            mousePortLbl.setDisable(true);
            keyBoardPortLbl.setDisable(true);
            dataTransferPortLbl.setDisable(true);

        }
    }

    public void showEditPage(IP ip) {

        TextField IP = new TextField();
        IP.setText(ip.getIP());
        TextField password = new TextField();
        password.setText(ip.getPassword());

        VBox vBox = new VBox();
        vBox.setMargin(IP, new Insets(10, 10, 10, 10));
        vBox.setMargin(password, new Insets(10, 10, 10, 10));
        ObservableList listTextField = vBox.getChildren();
        Label IPLabel = new Label();
        Label passwordLabel = new Label();
        Button button = new Button();
        button.setText("Edit");
        VBox vBoxLabel = new VBox();
        IPLabel.setText("IP");
        passwordLabel.setText("Password");

        vBoxLabel.setMargin(button, new Insets(30, 10, 30, 10));
        vBoxLabel.setMargin(IPLabel, new Insets(10, 10, 10, 10));
        vBoxLabel.setMargin(passwordLabel, new Insets(20, 10, 10, 10));


        ObservableList listLabel = vBoxLabel.getChildren();
        listLabel.addAll(IPLabel, passwordLabel, button);
        listTextField.addAll(IP, password);
        HBox hBox = new HBox();
        ObservableList list1 = hBox.getChildren();

        list1.addAll(vBoxLabel, vBox);


        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Edit Selected Port");
        stage.show();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < observableList.size(); i++) {
                    if (observableList.get(i).equals(ip)) {
                        observableList.get(i).setIP(IP.getText());
                        observableList.get(i).setPassword(password.getText());
                        break;
                    }
                }
                stage.close();
            }
        });
    }

    public void onActionCreateBtn(ActionEvent actionEvent) {
          Map servers= ManagedServer.getServers();
          List especialIpsList = ManagedServer.getEspecialIpsList();

        try {
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
            if (IPCheckBox.isSelected()) {
                for (IP ip : observableList) {
                    especialIpsList.add(ip);
                }
            }
        } catch (Exception e) {
            //TODO something
        }
        ManagedServer.setDefaultPassword(passwordTxt.getText());
        Util.switchWindow(getClass().getClassLoader().getResource("views/ServerControlPanelWindow.fxml"),
                "Control panel",false);
        shareScreenWindowStage = Util.openWindowWithReturnValue(getClass().getClassLoader().getResource("views/ShareScreenWindow.fxml"),
                "Screen", true);
        shareScreenWindowStage.getScene().setOnKeyReleased(ShareScreenWindowController::onKeyReleasedScene);
        shareScreenWindowStage.getScene().setOnKeyPressed(ShareScreenWindowController::onKeyPressedScene);
;
    }



    public void onMouseClickedDefaultPasswordCheckBox(MouseEvent mouseEvent) {
        if (defaultPasswordCheckBox.isSelected()) {
            especialPasswordTxt.setEditable(false);
            especialPasswordTxt.setDisable(true);
            passwordTxt.setDisable(false);
            passwordTxt.setEditable(true);
        } else {
            especialPasswordTxt.setEditable(true);
            especialPasswordTxt.setDisable(false);
            passwordTxt.setDisable(true);
            passwordTxt.setEditable(false);
        }
    }

    public void onActionBackBtn(ActionEvent actionEvent) {
        Util.switchWindow(getClass().getClassLoader().getResource("views/MainWindow.fxml"),
                "Remote Desktop Application", false);
    }



}

