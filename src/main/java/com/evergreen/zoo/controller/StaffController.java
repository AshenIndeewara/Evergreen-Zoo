package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.tanleDto.StaffDto;
import com.evergreen.zoo.model.StaffModel;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StaffController implements Initializable {
    StaffModel model = new StaffModel();
    @FXML
    private TableColumn<StaffDto, String> actionCol;

    @FXML
    private TableColumn<StaffDto, String> cntactNumber;

    @FXML
    private TableColumn<StaffDto, String> namCol;

    @FXML
    private TableColumn<StaffDto, Integer> roleCol;

    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton searchBtnClicked;

    @FXML
    private TableView<StaffDto> staffTable;

    @FXML
    void addStaffClick(ActionEvent event) {

    }

    @FXML
    void takeAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshTable() throws SQLException {
        staffTable.getItems().clear();
        ArrayList<StaffDto> allStaff = model.getAllStaff();
        ObservableList<StaffDto> staffTMS = FXCollections.observableArrayList();
        for (StaffDto staff : allStaff) {
            System.out.println(staff);
            staffTMS.add(staff);
        }
        staffTable.setItems(staffTMS);
    }

}
