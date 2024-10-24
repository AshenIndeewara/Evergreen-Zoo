package com.evergreen.zoo.controller;

import java.io.IOException;
import java.net.URL;
import com.evergreen.zoo.dto.RegisterDto;
import com.evergreen.zoo.model.RegisterModel;
import com.evergreen.zoo.notification.ShowNotification;
import com.evergreen.zoo.otpSend.SendEmail;
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

        if(registerModel.registorUser(registerDto)){
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

}
