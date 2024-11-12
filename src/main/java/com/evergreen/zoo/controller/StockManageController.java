package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.tanleDto.StockDto;
import com.evergreen.zoo.model.StockModel;
import com.evergreen.zoo.util.CheckRegex;
import com.evergreen.zoo.util.ShowNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockManageController implements Initializable {

    @FXML
    private ComboBox<String> itemList;

    @FXML
    private ComboBox<String> movement;

    @FXML
    private TextField qtyTxt;

    @FXML
    private TableView<StockDto> itemTable;

    @FXML
    private TableColumn<StockDto, String> supplier;

    @FXML
    private TableColumn<StockDto, String> item;

    @FXML
    private TableColumn<StockDto, Integer> qty;

    @FXML
    private TableColumn<StockDto, ImageView> typeImage;

    @FXML
    private TextField itemName;

    @FXML
    private TextField newQTY;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<String> supplierName;

    @FXML
    private TextField minQTY;

    StockModel stockModel = new StockModel();
    Boolean isPriceValid = false;
    Boolean isNewQTYValid = false;
    Boolean isMinQTYValid = false;
    Boolean isQtyValid = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        item.setCellValueFactory(new PropertyValueFactory<>("item"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        typeImage.setCellValueFactory(new PropertyValueFactory<>("typeImage"));

        try {
            getStock();
            getItems();
            getSupplier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStock() {
        ArrayList<StockDto> stockDtos =  stockModel.getStock();
        itemTable.getItems().clear();
        itemTable.getItems().addAll(stockDtos);
    }

    public void getItems() {
        ArrayList<String> items = stockModel.getItems();
        itemList.getItems().clear();
        itemList.getItems().addAll(items);

        ArrayList<String> movements = new ArrayList<>();
        movements.add("Stock In");
        movements.add("Stock Out");
        movement.getItems().clear();
        movement.getItems().addAll(movements);
    }

    public void getSupplier() {
        ArrayList<String> suppliers = stockModel.getSupplier();
        supplierName.getItems().clear();
        supplierName.getItems().addAll(suppliers);
    }

    @FXML
    void isUpdateStock(ActionEvent event) {
        String item = itemList.getValue();
        String move = movement.getValue();
        if(!isQtyValid || item == null || move == null) {
            new ShowNotification("Invalid input",
                    "Please check the input fields",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            return;
        }
        int qty = Integer.parseInt(qtyTxt.getText());
        if(qty > stockModel.getIteeQty(item)) {
            new ShowNotification("Invalid input",
                    "Please check the input fields",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            return;
        }
        stockModel.isUpdateStock(item, move, qty);
        getStock();
    }

    @FXML
    void addNewItem(ActionEvent event) {
        if(!isPriceValid || !isNewQTYValid || !isMinQTYValid) {
            new ShowNotification("Invalid input",
                    "Please check the input fields",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
            return;
        }
        String itemName = this.itemName.getText();
        int newQTY = Integer.parseInt(this.newQTY.getText());
        double price = Double.parseDouble(this.price.getText());
        String supplierName = this.supplierName.getValue();
        int minQTY = Integer.parseInt(this.minQTY.getText());
        if(stockModel.isAddNewItem(itemName, newQTY, price, supplierName, minQTY)) {
            getStock();
            new ShowNotification("Item added successfully",
                    itemName+" added new item to the stock",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
            getStock();
        }else{
            new ShowNotification("Item added failed",
                    "Failed to add "+itemName+" to the stock",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }
    }

    @FXML
    void checkNumberRegex1(KeyEvent event) {
        if(CheckRegex.checkRegex("number", minQTY.getText())) {
            minQTY.setStyle("-fx-text-fill: BLACK");
            isPriceValid = true;
        }else{
            minQTY.setStyle("-fx-text-fill: RED");
            isPriceValid = false;
        }
    }

    @FXML
    void checkNumberRegex2(KeyEvent event) {
        if(CheckRegex.checkRegex("number", newQTY.getText())) {
            newQTY.setStyle("-fx-text-fill: BLACK");
            isNewQTYValid = true;
        }else{
            newQTY.setStyle("-fx-text-fill: RED");
            isNewQTYValid = false;
        }
    }
    @FXML
    void checkNumberRegex3(KeyEvent event) {
        if(CheckRegex.checkRegex("number", price.getText())) {
            price.setStyle("-fx-text-fill: BLACK");
            isMinQTYValid = true;
        }else{
            price.setStyle("-fx-text-fill: RED");
            isMinQTYValid = false;
        }
    }
    @FXML
    void qtyRegex(KeyEvent event) {
        if(CheckRegex.checkRegex("number", qtyTxt.getText())) {
            qtyTxt.setStyle("-fx-text-fill: BLACK");
            isQtyValid = true;
        }else{
            qtyTxt.setStyle("-fx-text-fill: RED");
            isQtyValid = false;
        }

    }
}
