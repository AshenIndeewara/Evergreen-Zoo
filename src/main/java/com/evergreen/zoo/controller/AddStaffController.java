package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.RegisterDto;
import com.evergreen.zoo.model.RegisterModel;
import com.evergreen.zoo.util.ShowNotification;
import com.evergreen.zoo.util.CheckRegex;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {

    @FXML
    private JFXTextField addressTXT;

    @FXML
    private JFXTextField emailTXT;

    @FXML
    private JFXTextField nameTXT;

    @FXML
    private JFXTextField numberTXT;

    @FXML
    private JFXPasswordField passwordTXT;

    @FXML
    private JFXTextField usernameTXT;

    @FXML
    private JFXComboBox<String> role;

    RegisterModel registerModel = new RegisterModel();

    Boolean isUserValid = false;
    Boolean isPassValid = false;
    Boolean isEmailValid = false;
    Boolean isPhoneValid = false;
    Boolean isNameValid = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadRoles();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    void clearFields() {
        usernameTXT.clear();
        passwordTXT.clear();
        nameTXT.clear();
        emailTXT.clear();
        numberTXT.clear();
        addressTXT.clear();
    }

    @FXML
    void saveBtnClicked(ActionEvent event) throws SQLException {
        if(isUserValid && isPassValid && isEmailValid && isPhoneValid && isNameValid) {
            System.out.println("All fields are valid");

            int roleId = registerModel.getRoleIdByDescription(role.getValue());
            //TODO : add regex check password, encrypt password

            Boolean isUserRegister = registerModel.registerUser(new RegisterDto(
                    usernameTXT.getText(),
                    passwordTXT.getText(),
                    nameTXT.getText(),
                    emailTXT.getText(),
                    numberTXT.getText(),
                    addressTXT.getText(),
                    roleId
            ));

            if (isUserRegister) {
                new ShowNotification(
                        "Success",
                        "Staff added successfully",
                        "success.png",
                        "GREEN"
                ).start();
                clearFields();
            } else {
                new ShowNotification(
                        "Error",
                        "Please fill all the fields correctly",
                        "unsuccess.png",
                        "RED"
                ).start();
            }
        } else {
            new ShowNotification(
                    "Error",
                    "Please fill all the fields correctly",
                    "unsuccess.png",
                    "RED"
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
    void emailRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("email", emailTXT.getText())) {
            emailTXT.setStyle("-fx-text-fill: green");
            isEmailValid = true;
        } else {
            emailTXT.setStyle("-fx-text-fill: red");
            isEmailValid = false;
        }
    }

    @FXML
    void nameRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("name", nameTXT.getText())) {
            nameTXT.setStyle("; -fx-text-fill: green;");
            isNameValid = true;
        } else {
            nameTXT.setStyle("-fx-text-fill: red");
            isNameValid = false;
        }
    }

    @FXML
    void passwordRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("password", passwordTXT.getText())) {
            passwordTXT.setStyle("-fx-text-fill: green");
            isPassValid = true;
        } else {
            passwordTXT.setStyle("-fx-text-fill: red");
            isPassValid = false;
        }
    }

    @FXML
    void phoneRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("number", numberTXT.getText())) {
            numberTXT.setStyle("-fx-text-fill: green");
            isPhoneValid = true;
        } else {
            numberTXT.setStyle("-fx-text-fill: red");
            isPhoneValid = false;
        }
    }

    @FXML
    void usernameRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("username", usernameTXT.getText())) {
            usernameTXT.setStyle("-fx-text-fill: green");
            isUserValid = true;
        } else {
            usernameTXT.setStyle("-fx-text-fill: red");
            isUserValid = false;
        }
    }

}
