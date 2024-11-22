package com.evergreen.zoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login/loginPane.fxml"));
        Scene scene = new Scene(root);

        // Add the application icon
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/asserts/icon/zoo.png")));

        stage.setResizable(false);
        // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
