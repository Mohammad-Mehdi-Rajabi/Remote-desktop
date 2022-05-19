package Main;

import Core.Util.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Util.main=this;
        stage = primaryStage;
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/MainWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        /*primaryStage.getIcons().add(SwingFXUtils.toFXImage(
                ImageIO.read(
                        new File(getClass().getClassLoader().getResource("images/icon.jpg").toURI())), null)
        );*/
        primaryStage.setTitle("Remote Desktop Application");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
