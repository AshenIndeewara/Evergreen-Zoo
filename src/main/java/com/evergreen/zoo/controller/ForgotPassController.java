package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.ForgotDto;
import com.evergreen.zoo.model.ForgotModel;
import com.evergreen.zoo.util.SendEmail;
import com.evergreen.zoo.util.SendSMS;
import com.evergreen.zoo.util.ShowNotification;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPassController {
    ForgotDto forgotDto;
    static int count = 3;

    @FXML
    public JFXButton restPassBtn1;

    @FXML
    private JFXTextField newPassTxt1;

    @FXML
    private JFXTextField newPassTxt2;

    @FXML
    private TextField emailTxt;

    @FXML
    private JFXButton getOtpBtnMail;

    @FXML
    private JFXButton getOtpBtnNum;

    @FXML
    private JFXTextField otpTxt;

    @FXML
    private TextField phoneNumber;

    @FXML
    private JFXButton restPassBtn;

    @FXML
    private Label errorMsg;

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXButton getUserDataBtn;


    @FXML
    private AnchorPane mainpane;

    @FXML
    void getOtpEmail(ActionEvent event) {
        count--;
        if(count > 0) {
            getOtpBtnMail.setDisable(true); getOtpBtnNum.setDisable(true);
            otpTxt.setDisable(false); restPassBtn.setDisable(false);

            int otp = 10000 + new Random().nextInt(90000);
            System.out.println(otp);
            forgotDto.setOTP(otp);

            System.out.println(forgotDto.toString());
            System.out.println("Otp eka send kala");

            SendEmail.sendEmail(forgotDto.getEmail(), String.valueOf(forgotDto.getOTP()));
            new ShowNotification("OTP Sent Successfully!",
                    "An OTP has been sent to your registered email.\n Please check your messages and enter the code to proceed.",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
        }else {
            new ShowNotification("Action Required!",
                "An issue has occurred.\n Please contact your system administrator for assistance.",
                "unsuccess.png",
                "he he login notification eka click kala"
            ).start();
        }
    }

    @FXML
    void getOtpSMS(ActionEvent event) {
        count--;
        if(count > 0) {
            getOtpBtnMail.setDisable(true); getOtpBtnNum.setDisable(true);
            otpTxt.setDisable(false); restPassBtn.setDisable(false);

            int otp = 10000 + new Random().nextInt(90000);
            forgotDto.setOTP(otp);

            System.out.println(forgotDto.toString());
            System.out.println("Otp eka send kala");

            SendSMS.sendsms(forgotDto.getPhoneNumber(), forgotDto.getOTP());
            new ShowNotification("OTP Sent Successfully!",
                    "An OTP has been sent to your registered contact.\n Please check your messages and enter the code to proceed.",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
        }else {
            new ShowNotification("Action Required!",
                "An issue has occurred.\n Please contact your system administrator for assistance.",
                "unsuccess.png",
                "he he login notification eka click kala"
            ).start();
        }
    }

    @FXML
    void getUserData(ActionEvent event) throws SQLException {
        forgotDto = ForgotModel.getUserData(usernameTxt.getText());
        if (forgotDto == null) {
            new ShowNotification("Invalid Username!",
                    "The username you entered is not recognized.\n Please check the spelling and try again.",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }else {
            phoneNumber.setText(forgotDto.getPhoneNumber());
            emailTxt.setText(forgotDto.getEmail());
            getOtpBtnMail.setDisable(false); getOtpBtnNum.setDisable(false);
        }

    }

    @FXML
    void rextPass(ActionEvent event) {
        if(otpTxt.getText().equals(String.valueOf(forgotDto.getOTP()))) {
            //TODO : add anchr pane
            usernameTxt.setVisible(false); restPassBtn.setVisible(false); phoneNumber.setVisible(false); getUserDataBtn.setVisible(false); getOtpBtnNum.setVisible(false); getOtpBtnMail.setVisible(false); emailTxt.setVisible(false); otpTxt.setVisible(false);errorMsg.setVisible(false);

            newPassTxt2.setVisible(true); newPassTxt1.setVisible(true); restPassBtn1.setVisible(true);
            System.out.println("add new password");
        }else{
            new ShowNotification("Invalid OTP!",
                    "The OTP you entered is incorrect. \n Please check the code and try again.",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            getOtpBtnMail.setDisable(false); getOtpBtnNum.setDisable(false);
        }

    }
    @FXML
    public void resetPassFromDB(ActionEvent actionEvent) throws SQLException, IOException {
        if (newPassTxt1.getText().equals(newPassTxt2.getText())) {
            errorMsg.setVisible(false);
            String newPasswd = BCrypt.hashpw(newPassTxt1.getText(), BCrypt.gensalt());
            Boolean isChangeUserPW = ForgotModel.isChangeUserPW(forgotDto,newPasswd);
            new ShowNotification("Password Changed Successfully!",
                    "Your password has been updated successfully. \n You can now use your new password to log in.",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
            //TODO : passwords encrypt karannooo

            mainpane.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/login/loginPane.fxml"));
            mainpane.getChildren().add(pane);

        }else{
            new ShowNotification("Password Mismatch!",
                    "The passwords you entered do not match. \n Please make sure both passwords are identical and try again.",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }
    }
}
