package Controllers;

import Core.Util.Util;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class SetUpServerWindowController {

    @FXML
    public TextField portTxt;
    @FXML
    public TextField IPTxt;

    @FXML
    public void initialize() {
        portTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        IPTxt.setEditable(false);
        IPTxt.setText(Util.getSystemIP());
    }


}
