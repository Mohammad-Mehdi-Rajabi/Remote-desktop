package Main;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.centerOnScreen();
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


    public static void switchScene(URL url, String title, Boolean resizeable) {
        try {
            Parent root = FXMLLoader.load(url);
            stage.setResizable(resizeable);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
    }

    public static void openWindow(URL url, String title, boolean resizeable) {
        Stage stage2 = new Stage();
        try {
            stage2.setResizable(resizeable);
            Parent parent = FXMLLoader.load(url);
            stage2.setScene(new Scene(parent));
            stage2.setResizable(false);
            stage2.setTitle(title);
            stage2.centerOnScreen();
            stage2.show();
        } catch (IOException e) {
            //TODO something
            e.printStackTrace();
        }
    }

}
