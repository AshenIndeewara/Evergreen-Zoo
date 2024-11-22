package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.FoodDto;
import com.evergreen.zoo.dto.tanleDto.SupplierDto;
import com.evergreen.zoo.model.SupplierModel;
import com.evergreen.zoo.util.ShowNotification;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private TableColumn<SupplierDto, String> supAction;

    @FXML
    private TextField supAddressTXT;

    @FXML
    private TextArea supDescriptionTXT;

    @FXML
    private TableColumn<SupplierDto, String> supEmail;

    @FXML
    private TextField supEmailTXT;

    @FXML
    private TextField supNameTXT;

    @FXML
    private TextField supNumberTXT;

    @FXML
    private TableColumn<SupplierDto, String> supPhone;

    @FXML
    private TableView<SupplierDto> supTable;

    @FXML
    private TableColumn<SupplierDto, String> supName;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton updateBtn;

    SupplierModel supplierModel = new SupplierModel();

    private int role;

    public void setRole(int role) {
        this.role = role;
    }

    boolean nameRegex, emailRegex, numberRegex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supName.setCellValueFactory(new PropertyValueFactory<>("name"));
        supEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        supPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        supAction.setCellValueFactory(new PropertyValueFactory<>("description"));
        nameRegex = false;
        emailRegex = false;
        numberRegex = false;
        try {
            loadSuppliers();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void loadSuppliers() {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getSuppliers();
        supTable.getItems().clear();
        supTable.getItems().addAll(supplierDtos);
    }

    @FXML
    void addSupplier(ActionEvent event) {
        String name = supNameTXT.getText();
        String address = supAddressTXT.getText();
        String email = supEmailTXT.getText();
        String phone = supNumberTXT.getText();
        String description = supDescriptionTXT.getText();
        if(name == null || name.isEmpty() || address == null || address.isEmpty() || email == null || email.isEmpty() || phone == null || phone.isEmpty() || description == null || description.isEmpty()){
            new ShowNotification("Supplier Added Unsuccessful",
                    "Please fill all the fields",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            return;
        }
        SupplierDto supplierDto = new SupplierDto(name, phone, email, address, description);
        Boolean isAdded = supplierModel.addSupplier(supplierDto);
        if (isAdded) {
            new ShowNotification("Supplier Added",
                    "Supplier has been added successfully",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
            loadSuppliers();
        }else{
            new ShowNotification("Supplier Added Unsuccessful",
                    "Supplier has not been added successfully",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }
    }


    @FXML
    void clearSupplier(ActionEvent event) {
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        addBtn.setDisable(false);
        loadSuppliers();
        supNameTXT.clear();
        supAddressTXT.clear();
        supEmailTXT.clear();
        supNumberTXT.clear();
        supDescriptionTXT.clear();
    }

    @FXML
    void deleteSupplier(ActionEvent event) throws SQLException {
        SupplierDto supplierDto = supTable.getSelectionModel().getSelectedItem();
        ArrayList<FoodDto> items = supplierModel.getSupplierItems(supplierDto.getSupplierID());
        if(items.size() > 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Supplier");
            String message = "This supplier is used in the following food items\n";
            for (FoodDto item : items) {
                message += item.getName() + "\n";
            }
            alert.setContentText(message + "Do you want to delete this supplier?");
            alert.showAndWait();
            if(alert.getResult().getText().equals("Cancel")){
                return;
            }else{
                Boolean isDeleted = supplierModel.isDeleteSupplier(supplierDto , items);
                if (isDeleted) {
                    new ShowNotification("Supplier Deleted",
                            "Supplier has been deleted successfully",
                            "success.png",
                            "he he login notification eka click kala"
                    ).start();
                    loadSuppliers();
                }else{
                    new ShowNotification("Supplier Deleted Unsuccessful",
                            "Supplier has not been deleted successfully",
                            "unsuccess.png",
                            "he he login notification eka click kala"
                    ).start();
                }
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        addBtn.setDisable(true);
        nameRegex = true;
        emailRegex = true;
        numberRegex = true;
        SupplierDto supplierDto = supTable.getSelectionModel().getSelectedItem();
        supNameTXT.setText(supplierDto.getName());
        supAddressTXT.setText(supplierDto.getAddress());
        supEmailTXT.setText(supplierDto.getEmail());
        supNumberTXT.setText(supplierDto.getPhone());
        supDescriptionTXT.setText(supplierDto.getDescription());
        System.out.println(supplierDto.toString());
    }

    @FXML
    void updateSupplier(ActionEvent event) {
        String name = supNameTXT.getText();
        String address = supAddressTXT.getText();
        String email = supEmailTXT.getText();
        String phone = supNumberTXT.getText();
        String description = supDescriptionTXT.getText();
        SupplierDto supplierDto = new SupplierDto(name, phone, email, address, description);
        Boolean isUpdated = supplierModel.isUpdateSupplier(supplierDto);
        if (isUpdated) {
            new ShowNotification("Supplier Updated",
                    "Supplier has been updated successfully",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
            loadSuppliers();
        }else{
            new ShowNotification("Supplier Updated Unsuccessful",
                    "Supplier has not been updated successfully",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }
    }

    @FXML
    void emailRegex(KeyEvent event) {
        if(supEmailTXT.getText().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            supEmailTXT.setStyle("-fx-text-fill: green");
            emailRegex = true;
        } else {
            supEmailTXT.setStyle("-fx-text-fill: black");
            emailRegex = false;
        }
    }

    @FXML
    void nameRegex(KeyEvent event) {
        if(supNameTXT.getText().matches("^[a-zA-Z\\s]*$")) {
            supNameTXT.setStyle("; -fx-text-fill: green;");
            nameRegex = true;
        } else {
            supNameTXT.setStyle("-fx-text-fill: black");
            nameRegex = false;
        }
    }

    @FXML
    void numberRegex(KeyEvent event) {
        if(supNumberTXT.getText().matches("^[0-9]*$")) {
            supNumberTXT.setStyle("-fx-text-fill: green");
            numberRegex = true;
        } else {
            supNumberTXT.setStyle("-fx-text-fill: black");
            numberRegex = false;
        }
    }

}
