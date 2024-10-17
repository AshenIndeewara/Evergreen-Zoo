module com.evergreen.zoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires resend.java;
    requires jbcrypt;
    requires org.controlsfx.controls;
    requires java.management;
    requires java.desktop;

    opens com.evergreen.zoo.controller to javafx.fxml;
    opens com.evergreen.zoo.dto to javafx.base;
    opens com.evergreen.zoo.otpSend to javafx.base;
    opens com.evergreen.zoo.notification to javafx.base;
    exports com.evergreen.zoo;
}