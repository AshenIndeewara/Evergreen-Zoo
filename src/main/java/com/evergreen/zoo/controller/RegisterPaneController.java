package com.evergreen.zoo.controller;

import java.io.IOException;
import java.net.URL;
import com.evergreen.zoo.dto.RegisterDto;
import com.evergreen.zoo.model.RegisterModel;
import com.evergreen.zoo.util.ShowNotification;
import com.evergreen.zoo.otpSend.SendEmail;
import com.evergreen.zoo.util.CheckRegex;
import com.evergreen.zoo.util.UserIDQrEncryption;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterPaneController implements Initializable {

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField email;

    @FXML
    private Label errorMsg;

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXPasswordField password1;

    @FXML
    private JFXPasswordField password2;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXComboBox<String> role;

    @FXML
    private JFXTextField userName;

    Boolean isUserValid = false;
    Boolean isPassValid = false;
    Boolean isEmailValid = false;
    Boolean isNameValid = false;
    Boolean isNumberValid = false;

    RegisterModel registerModel = new RegisterModel();
    QrController qrController = new QrController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadRoles();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    @FXML
    void checkRegistor(ActionEvent event) throws Exception {
        String fullName = this.fullName.getText();
        String phoneNumber = this.phoneNumber.getText();
        String password2 = this.password2.getText();
        String password1 = this.password1.getText();
        String address = this.address.getText();
        String email = this.email.getText();
        String userName = this.userName.getText();
        String position = role.getValue();

        System.out.println("userName = " + isNameValid + "\n" +
                "password1 = " + isPassValid + "\n" +
                "email = " + isEmailValid + "\n" +
                "fullName = " + isNameValid + "\n" +
                "phoneNumber = " + isNumberValid + "\n" +
                "position = " + position);
        if (!isUserValid || !isPassValid || !isEmailValid || !isNameValid || !isNumberValid) {
            new ShowNotification("Recheck your input data!",
                    "Something wrong in your input",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            return;
        }

        int roleId = registerModel.getRoleIdByDescription(position);
        //TODO : add regex check password, encrypt password
        RegisterDto registerDto = new RegisterDto(
                userName,
                BCrypt.hashpw(password1, BCrypt.gensalt()),
                fullName,
                email,
                phoneNumber,
                address,
                roleId
        );

        if(registerModel.registerUser(registerDto)){

            System.out.println("register success");
            System.out.println("QR sending to email");
            SendEmail.sendQr(email, qrController.getQrAPI(UserIDQrEncryption.encrypt(String.valueOf(roleId))));
            new ShowNotification("Login successfully!",
                    "Hello "+userName+" register",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
        }else{
            new ShowNotification("Recheck your input data!",
                    "Something wrong in your input",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }
    }

    void loadRoles() throws SQLException {
        role.getItems().clear();
        ArrayList<String> roles = registerModel.getAllRoles();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(roles);
        role.setItems(observableList);
    }

    @FXML
    void clickLogin(ActionEvent event) throws IOException {
        mainPane.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/login/loginPane.fxml"));
        mainPane.getChildren().add(pane);
    }

    @FXML
    void confirmPassRegex(KeyEvent event) {
        if (password1.getText().equals(password2.getText())) {
            password2.setStyle("-fx-text-fill: GREEN");
            isPassValid = true;
        } else {
            password2.setStyle("-fx-text-fill: RED");
            isPassValid = false;
        }

    }

    @FXML
    void emailRegex(KeyEvent event) {
        if (CheckRegex.checkRegex("email", email.getText())) {
            email.setStyle("-fx-text-fill: GREEN");
            isEmailValid = true;
        } else {
            email.setStyle("-fx-text-fill: RED");
            isEmailValid = false;
        }
    }

    @FXML
    void nameRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("name", fullName.getText())) {
            fullName.setStyle("-fx-text-fill: GREEN");
            isNameValid = true;
        }else {
            fullName.setStyle("-fx-text-fill: RED");
            isNameValid = false;
        }
    }

    @FXML
    void numberRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("number", phoneNumber.getText())) {
            phoneNumber.setStyle("-fx-text-fill: GREEN");
            isNumberValid = true;
        }else {
            phoneNumber.setStyle("-fx-text-fill: RED");
            isNumberValid = false;
        }
    }

    @FXML
    void passwordRegex(KeyEvent event) {
        System.out.println(password1.getText());
        if (CheckRegex.checkRegex("password", password1.getText())) {
            password1.setStyle("-fx-text-fill: GREEN");
            isPassValid = true;
        } else {
            password1.setStyle("-fx-text-fill: RED");
            isPassValid = false;
        }
    }

    @FXML
    void usernameRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("username", userName.getText())) {
            userName.setStyle("-fx-text-fill: GREEN");
            isUserValid = true;
        }else {
            userName.setStyle("-fx-text-fill: RED");
            isUserValid = false;
        }
    }

}
