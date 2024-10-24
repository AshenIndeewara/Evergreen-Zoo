package com.evergreen.zoo;

import com.evergreen.zoo.controller.QrController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //System.out.println(QrController.readQRCodeFromFile("C:\\Users\\user\\Desktop\\qr.png"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/login/loginPane.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
