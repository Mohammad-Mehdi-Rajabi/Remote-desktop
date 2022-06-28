package Main;

import Core.Util.Util;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Util.main=this;
        stage = primaryStage;
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setAlwaysOnTop(false);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/MainWindow.fxml"));
        primaryStage.setScene(new Scene(root));
//        primaryStage.getIcons().add(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().getResource("images/images/pcIcon.png").toURI().toURL()),
//                null));
        primaryStage.setTitle("Remote Desktop Application");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}