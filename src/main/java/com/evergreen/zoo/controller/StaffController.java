package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.tanleDto.StaffDto;
import com.evergreen.zoo.model.RegisterModel;
import com.evergreen.zoo.model.StaffModel;
import com.evergreen.zoo.util.CheckRegex;
import com.evergreen.zoo.util.ShowNotification;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private TextField emEmail;

    @FXML
    private TextField emName;

    @FXML
    private TextField emNumber;

    @FXML
    private ComboBox<String> emRole;

    @FXML
    private AnchorPane actionPane;

    @FXML
    private JFXButton actionBtn;

    RegisterModel registerModel = new RegisterModel();

    private int role;

    boolean nameRegex, emailRegex, numberRegex;

    public void setRole(int role) {
        this.role = role;
    }
    @FXML
    void addStaffClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addStaff.fxml"));
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
        try {
            loadRoles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        actionPane.setVisible(true);
        StaffDto selectedItem = staffTable.getSelectionModel().getSelectedItem();
        emName.setText(selectedItem.getStaffName());
        emEmail.setText(selectedItem.getStaffEmail());
        emNumber.setText(selectedItem.getStaffContact());
        emRole.setValue(selectedItem.getStaffRole());

    }
    void loadRoles() throws SQLException {
        emRole.getItems().clear();
        ArrayList<String> roles = registerModel.getAllRoles();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(roles);
        emRole.setItems(observableList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameRegex = false;
        emailRegex = false;
        numberRegex = false;
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

    @FXML
    void emDelete(ActionEvent event) {
        if(!nameRegex || !emailRegex || !numberRegex) {
            new ShowNotification("Error",
                    "Please fill all the fields correctly",
                    "unsuccess.png",
                    "RED"
            ).start();
            return;
        }
        StaffDto selectedItem = staffTable.getSelectionModel().getSelectedItem();
        boolean yes = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedItem.getStaffName() + "?", ButtonType.YES, ButtonType.NO).showAndWait().get() == ButtonType.YES;
        if (!yes) return;
        try {
            model.deleteStaff(selectedItem);
            refreshTable();
            new ShowNotification("Employee Deleted",
                    "Employee "+ selectedItem.getStaffName()+" has been deleted successfully",
                    "success.png",
                    "GREEN"
            ).start();
        } catch (SQLException e) {
            new ShowNotification("Employee Deleted unsuccessful",
                    "Employee "+ selectedItem.getStaffName()+" has not been deleted successfully",
                    "unsuccess.png",
                    "RED"
            ).start();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void emUpdate(ActionEvent event) {
        if(!nameRegex || !emailRegex || !numberRegex) {
            new ShowNotification("Error",
                    "Please fill all the fields correctly",
                    "unsuccess.png",
                    "RED"
            ).start();
            return;
        }
        StaffDto selectedItem = staffTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        int userid = model.getEmployeeId(selectedItem);
        System.out.println(userid);
        try {
            model.updateStaff(new StaffDto(
                    emName.getText(),
                    emRole.getValue(),
                    emNumber.getText(),
                    emEmail.getText()),
                    userid);
            refreshTable();
            new ShowNotification("Employee Updated",
                    "Employee "+ selectedItem.getStaffName()+" has been updated successfully",
                    "success.png",
                    "GREEN"
            ).start();
        } catch (SQLException e) {
            new ShowNotification("Employee Updated unsuccessful",
                    "Employee "+ selectedItem.getStaffName()+" has not been updated successfully",
                    "unsuccess.png",
                    "RED"
            ).start();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void employeeClicked(MouseEvent event) {
        actionBtn.setDisable(false);
    }

    @FXML
    void emBack(ActionEvent event) {
        actionPane.setVisible(false);
    }

    @FXML
    void chackRegexEmail(KeyEvent event) {
        if(CheckRegex.checkRegex("email", emEmail.getText())) {
            emEmail.setStyle("-fx-text-fill: green");
            emailRegex = true;
        } else {
            emEmail.setStyle("-fx-text-fill: black");
            emailRegex = false;
        }
    }

    @FXML
    void chackRegexNumber(KeyEvent event) {
        if(CheckRegex.checkRegex("phone", emNumber.getText())) {
            emNumber.setStyle("-fx-text-fill: green");
            numberRegex = true;
        } else {
            emNumber.setStyle("-fx-text-fill: black");
            numberRegex = false;
        }
    }

    @FXML
    void chackRegexname(KeyEvent event) {
        if(CheckRegex.checkRegex("name", emName.getText())) {
            emName.setStyle("; -fx-text-fill: green;");
            nameRegex = true;
        } else {
            emName.setStyle("-fx-text-fill: black");
            nameRegex = false;
        }
    }
}
