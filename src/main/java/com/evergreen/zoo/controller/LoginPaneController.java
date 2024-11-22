package com.evergreen.zoo.controller;
import com.evergreen.zoo.dto.LoginDto;
import com.evergreen.zoo.model.LoginModel;
import com.evergreen.zoo.util.UserIDQrEncryption;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.ImageUtils;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.evergreen.zoo.util.ShowNotification;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.swing.*;

import static com.evergreen.zoo.util.CheckRegex.checkRegex;

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

    private Boolean isUserValid = false;

    ActionEvent actionEvent;

    @FXML
    void usernameRegex(KeyEvent event) {
        if(checkRegex("username", userTxt.getText())) {
            userTxt.setStyle("-fx-text-fill: Green");
            isUserValid = true;
        } else {
            userTxt.setStyle("-fx-text-fill: red");
            isUserValid = false;
        }
    }


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


    @FXML
    void enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginBtn.fire();
        }
    }

    private void closeWindow(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void loadWindow(String path, boolean b, int role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/"+path));
        Parent root = loader.load();
        Stage windows = new Stage();

        AdminDashboardController adminDashboardController = loader.getController();
        adminDashboardController.setRole(role);

        if(b){
            //windows.initOwner(mainPane.getScene().getWindow());
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
        if (!isUserValid) {
            new ShowNotification("Invalid username",
                    "Please enter a valid username",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            return;
        }
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

            loadDashboards(resultSet.getInt(4), event);
//            if (resultSet.getInt(4)==1) {
//                loadWindow("admin/adminDashboard.fxml", true, 1);
//            } else if (resultSet.getInt(4)==2) {
//                //TODO : make dashboards
//            } else if (resultSet.getInt(4)==3) {
//                //TODO : make dashboards
//            } else if (resultSet.getInt(4)==4) {
//                //TODO : make dashboards
//            }
        }
    }

    void loadDashboards(int role, ActionEvent event) throws IOException {
        if (role == 1) {
            closeWindow(event);
            loadWindow("admin/adminDashboard.fxml", true, 1);
        } else if (role == 2) {
            closeWindow(event);
            loadWindow("admin/adminDashboard.fxml", true, 2);
            //TODO : make dashboards
        } else if (role == 3) {
            closeWindow(event);
            //TODO : make dashboards
        } else if (role == 4) {
            closeWindow(event);
            //TODO : make dashboards
        }else{
            System.out.println("Invalid role");
        }
    }

    @FXML
    void forgotPass(ActionEvent event) throws IOException {
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

    @FXML
    void qrScan(ActionEvent event) throws Exception {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setImageSizeDisplayed(true);

        webcam.open();

        JFrame window = new JFrame("QR Code Scanner");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        String qrCode = "8vQiEnkR3IJfLNZ2G1eQrg==";
        while (window.isVisible()){
            BufferedImage image = webcam.getImage();
            String filename = "selfie.jpg";

            ImageIO.write(image, ImageUtils.FORMAT_JPG, new File("selfie.jpg"));

            qrCode = QrController.readQRCodeFromFile("selfie.jpg");
            System.out.println(qrCode);
            Thread.sleep(1500);
            if(qrCode != null){
                webcam.close();
                window.dispose();
                break;
            }
        }
        try {
            webcam.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (qrCode == null) {
            qrCode = "8vQiEnkR3IJfLNZ2G1eQrg==";
        }
        System.out.println(UserIDQrEncryption.decrypt(qrCode));
        loadDashboards(Integer.parseInt(UserIDQrEncryption.decrypt(qrCode)), event);
    }

}
