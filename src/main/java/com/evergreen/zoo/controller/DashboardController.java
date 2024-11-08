package com.evergreen.zoo.controller;

import com.evergreen.zoo.model.DashboardModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label animalCount;

    @FXML
    private Label eventCount;

    @FXML
    private Label staffCount;

    @FXML
    private Label visitorCount;

    DashboardModel dashboardModel = new DashboardModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String, String> status = dashboardModel.status();

        animalCount.setText(status.get("animal"));
        eventCount.setText(status.get("eventPrograms"));
        staffCount.setText(status.get("employee"));
        visitorCount.setText(status.get("visitor"));
    }
}
