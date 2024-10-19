package com.evergreen.zoo.controller;
import com.evergreen.zoo.dto.LoginDto;
import com.evergreen.zoo.model.LoginModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.evergreen.zoo.notification.ShowNotification;
import org.mindrot.jbcrypt.BCrypt;

public class LoginPaneController implements Initializable {
    private LoginModel loginModel = new LoginModel();
    @FXML
    private Label errorMsg;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXPasswordField passTxt;

    @FXML
    private JFXTextField userTxt;

    @FXML
    private ImageView imageview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        URL imageUrl = getClass().getResource("/asserts/mr-bean-waiting.gif");
//        if (imageUrl != null) {
//            Image image = new Image(imageUrl.toString());
//            imageview.setImage(image);
//        } else {
//            System.out.println("Image not found");
//        }
    }

    private void closeWindow(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void loadWindow(String path, boolean b) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/"+path));
        Parent root = loader.load();
        Stage windows = new Stage();
        if(b){
            windows.setFullScreen(true);
            windows.setResizable(false);

        }
        windows.setScene(new Scene(root));
        try {
            windows.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void checkLogin(ActionEvent event) throws Exception {
        LoginDto loginDto = new LoginDto(userTxt.getText(), passTxt.getText());
        System.out.println(loginDto.toString());
        ResultSet resultSet = loginModel.checkLogin(loginDto);
        if (resultSet == null || !BCrypt.checkpw(loginDto.getPassword(), resultSet.getString(5))) {
            new ShowNotification("Recheck your username or password!",
                    "Invalid username or password",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        } else {
            closeWindow(event);
            new ShowNotification("Login successfully!",
                    "Hello " + resultSet.getString(1) + " welcome back",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();

            if (resultSet.getInt(4)==1) {
                loadWindow("admin/adminDashboard.fxml", true);
            } else if (resultSet.getInt(4)==2) {
                //TODO : make dashboards
            } else if (resultSet.getInt(4)==3) {
                //TODO : make dashboards
            } else if (resultSet.getInt(4)==4) {
                //TODO : make dashboards
            }
        }
    }

    @FXML
    void forgotPass(ActionEvent event) throws IOException {
        //closeWindow(event);
        //new ShowNotification().start();
        //loadWindow("login/forgotPass.fxml", false);
        mainPane.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/login/forgotPass.fxml"));
        mainPane.getChildren().add(pane);
    }


    @FXML
    void clickRegister(ActionEvent event) throws IOException {
        mainPane.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/register/registerPane.fxml"));
        mainPane.getChildren().add(pane);
    }

}
