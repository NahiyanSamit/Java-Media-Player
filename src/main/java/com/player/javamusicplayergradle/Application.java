// This is a simple Media player application created using JAVA
// This application is written by Ibnus Nahiyan Samit
// Github https://github.com/NahiyanSamit

package com.player.javamusicplayergradle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage){
        try {
            //Load FXML File
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Media-Player.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            InputStream stream = Files.newInputStream(Paths.get("src/main/resources/Icon/Application.png"));
            Image image = new Image(stream);
            stage.getIcons().add(image);
            stage.setTitle("JAVA Media Player");
            stage.setMinHeight(150);
            stage.setMinWidth(650);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception ignored){}
    }

    public static void main(String[] args) {

        launch();
    }
}
