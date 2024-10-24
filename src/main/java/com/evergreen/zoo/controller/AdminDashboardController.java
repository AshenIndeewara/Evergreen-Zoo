package com.evergreen.zoo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private Label titalLable;

    @FXML
    private Label animalCunt;

    @FXML
    private Label dateLabel;

    @FXML
    private Label eventsCount;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label speciesCount;

    @FXML
    private Label staffCount;

    @FXML
    private Label timeLabel;

    @FXML
    private Label visitorsCount;

    private static Button tempClickedBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titalLable.setText("Dashboard");
        try {
            callPane("dashboard.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setDate();
        setTime();
    }

    private void setDate() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    // Update the label with the current date
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    dateLabel.setText(formatter.format(currentDate));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void setTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime currentTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    //System.out.println(formatter.format(currentTime));
                    timeLabel.setText(formatter.format(currentTime));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void mouseEnter(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    @FXML
    void mouseExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color:  #1e293c; -fx-text-fill: white;");
    }

    @FXML
    void callAnimals(ActionEvent event) {
        titalLable.setText("Animal Management");

    }

    @FXML
    void callDashBoard(ActionEvent event) throws IOException {
        titalLable.setText("DashBoard");
        callPane("dashboard.fxml");

    }

    @FXML
    void callReports(ActionEvent event) {
        titalLable.setText("Reports Management");

    }

    @FXML
    void callSettings(ActionEvent event) {
        titalLable.setText("Settings");

    }

    void callPane(String path) throws IOException {
        AnchorPane newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/admin/" + path)));
        mainPane.getChildren().setAll(newPane);
    }
    @FXML
    void callStaff(ActionEvent event) throws IOException {
        titalLable.setText("Staff Management");
        callPane("staffPane.fxml");

    }
}
