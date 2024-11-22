package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.tanleDto.DashboardDto;
import com.evergreen.zoo.model.DashboardModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private TableColumn<DashboardDto, Integer> tableQTY;

    @FXML
    private TableColumn<DashboardDto, String> tableSpecies;

    @FXML
    private TableColumn<DashboardDto, String> tableStatus;

    @FXML
    private TableView<DashboardDto> animalTable;

    @FXML
    private VBox vboxVisitor;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChartTicket;

    DashboardModel dashboardModel = new DashboardModel();

    private int role;

    public void setRole(int role) {
        this.role = role;
        System.out.println("role from dashboard pane = " + role);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String, String> status = dashboardModel.status();

        animalCount.setText(status.get("animal"));
        staffCount.setText(status.get("employee"));
        visitorCount.setText(status.get("visitordetails"));

        tableSpecies.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("species"));
        tableQTY.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("count"));
        tableStatus.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        loadTable();

    }

    void loadTable() {
        ArrayList<DashboardDto> dashboardDtos = dashboardModel.getSpecies();
        animalTable.getItems().clear();
        animalTable.getItems().addAll(dashboardDtos);

        XYChart.Series<String, Number> series = dashboardModel.getData();
        barChartTicket.getData().add(series);
    }

}
