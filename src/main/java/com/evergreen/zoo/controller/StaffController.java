package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.tanleDto.StaffDto;
import com.evergreen.zoo.model.StaffModel;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StaffController implements Initializable {
    StaffModel model = new StaffModel();
    @FXML
    private TableColumn<StaffDto, String> emailCol;

    @FXML
    private TableColumn<StaffDto, String> cntactNumber;

    @FXML
    private TableColumn<StaffDto, String> namCol;

    @FXML
    private TableColumn<StaffDto, Integer> roleCol;

    @FXML
    private TextField searchBox;

    @FXML
    private TableView<StaffDto> staffTable;

    @FXML
    void addStaffClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin/addStaff.fxml"));
        Parent root = loader.load();
        Stage windows = new Stage();
        windows.initStyle(StageStyle.UNDECORATED);
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.initOwner(searchBox.getScene().getWindow());
        windows.setScene(new Scene(root));
        windows.show();
    }

    @FXML
    void takeAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        namCol.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("staffRole"));
        cntactNumber.setCellValueFactory(new PropertyValueFactory<>("staffContact"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("staffEmail"));
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void refreshTable() throws SQLException {
        staffTable.getItems().clear();
        ArrayList<StaffDto> allStaff = model.getAllStaff();
        ObservableList<StaffDto> staffTMS = FXCollections.observableArrayList();
        for (StaffDto staff : allStaff) {
            staffTMS.add(staff);
        }
        staffTable.setItems(staffTMS);
    }

    @FXML
    void searchStaff(KeyEvent event) throws SQLException {
        ArrayList<StaffDto> staff = model.searchStaff(searchBox.getText());
        ObservableList<StaffDto> staffTMS = FXCollections.observableArrayList();
        for (StaffDto staffDto : staff) {
            System.out.println(staffDto.toString());
            staffTMS.add(staffDto);
        }
        staffTable.setItems(staffTMS);
    }
}
