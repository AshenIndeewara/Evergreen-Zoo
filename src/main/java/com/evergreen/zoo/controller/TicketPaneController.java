package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.TicketDto;
import com.evergreen.zoo.model.TicketModel;
import com.evergreen.zoo.util.CheckRegex;
import com.evergreen.zoo.util.ShowNotification;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TicketPaneController implements Initializable {

    @FXML
    private Label adultCount;

    @FXML
    private Label adultLabel;

    @FXML
    private Label adultLabel1;

    @FXML
    private Label childCount;

    @FXML
    private Label childLabel;

    @FXML
    private Label childLabel1;

    @FXML
    private Label foreignChildCount;

    @FXML
    private Label foreignChildLabel;

    @FXML
    private Label foreignChildLabel1;

    @FXML
    private Label foreignCount;

    @FXML
    private Label foreignLabel;

    @FXML
    private Label foreignLabel1;

    @FXML
    private Label studentCount;

    @FXML
    private Label studentLabel;

    @FXML
    private Label studentLabel1;

    @FXML
    private Label totalLabel;

    @FXML
    private JFXComboBox<String> paymentOptions;

    @FXML
    private GridPane buttonGrid;

    @FXML
    private AnchorPane changePricePane;

    @FXML
    private AnchorPane ticketPane;

    @FXML
    private TextField adultNewPrice;

    @FXML
    private TextField childNewPrice;

    @FXML
    private TextField studentNewPrice;

    @FXML
    private TextField foreignNewPrice;

    @FXML
    private TextField fChildNewPrice;


    private TicketModel ticketModel = new TicketModel();
    private HashMap<String, Integer> ticketPrice;
    private double totalPrice = 0;

    //different ticket prices
    private double adultPrice = 0;
    private double childPrice = 0;
    private double foreignChildPrice = 0;
    private double foreignPrice = 0;
    private double studentPrice = 0;

    private TicketDto ticketDto;

    double getTotalPrice() {
        return adultPrice + childPrice + foreignChildPrice + foreignPrice + studentPrice;
    }

    void loadPayments() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll("Card", "Cash");
        paymentOptions.setItems(observableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalLabel.setText("0");
        loadPayments();
        try {
            ticketPrice = ticketModel.getTicketPrice();
            adultLabel.setText(ticketPrice.get("Adult").toString());
            childLabel.setText(ticketPrice.get("Child").toString());
            foreignChildLabel.setText(ticketPrice.get("ForeignChild").toString());
            foreignLabel.setText(ticketPrice.get("Foreign").toString());
            studentLabel.setText(ticketPrice.get("Student").toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    String add(int count) {
        return String.valueOf(count + 1);
    }

    String minus(int count) {
        if (count == 0) {
            return "0";
        }
        return String.valueOf(count - 1);
    }

    @FXML
    void ClearFields(ActionEvent event) {
        adultCount.setText("0");
        childCount.setText("0");
        foreignCount.setText("0");
        studentCount.setText("0");
        foreignChildCount.setText("0");
        totalLabel.setText("0.0");
        adultPrice = 0;
        childPrice = 0;
        foreignChildPrice = 0;
        foreignPrice = 0;
        studentPrice = 0;
        totalPrice = 0;
    }

    @FXML
    void adultAdd(ActionEvent event) {
        adultCount.setText(add(Integer.parseInt(adultCount.getText())));
        adultPrice += ticketPrice.get("Adult");
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void adultRemove(ActionEvent event) {
        adultCount.setText(minus(Integer.parseInt(adultCount.getText())));
        if(adultPrice >= ticketPrice.get("Adult")) {
            adultPrice -= ticketPrice.get("Adult");
        }
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void childAdd(ActionEvent event) {
        childCount.setText(add(Integer.parseInt(childCount.getText())));
        childPrice += ticketPrice.get("Child");
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void childRemove(ActionEvent event) {
        childCount.setText(minus(Integer.parseInt(childCount.getText())));
        if(childPrice >= ticketPrice.get("Child")) {
            childPrice -= ticketPrice.get("Child");
        }
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void foreignAdd(ActionEvent event) {
        foreignCount.setText(add(Integer.parseInt(foreignCount.getText())));
        foreignPrice += ticketPrice.get("Foreign");
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void foreignRemove(ActionEvent event) {
        foreignCount.setText(minus(Integer.parseInt(foreignCount.getText())));
        if(foreignPrice >= ticketPrice.get("Foreign")) {
            foreignPrice -= ticketPrice.get("Foreign");
        }
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void studentAdd(ActionEvent event) {
        studentCount.setText(add(Integer.parseInt(studentCount.getText())));
        studentPrice += ticketPrice.get("Student");
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void studentRemove(ActionEvent event) {
        studentCount.setText(minus(Integer.parseInt(studentCount.getText())));
        if(studentPrice >= ticketPrice.get("Student")) {
            studentPrice -= ticketPrice.get("Student");
        }
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void foreignChildAdd(ActionEvent event) {
        foreignChildCount.setText(add(Integer.parseInt(foreignChildCount.getText())));
        foreignChildPrice += ticketPrice.get("ForeignChild");
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void foreignChildRemove(ActionEvent event) {
        foreignChildCount.setText(minus(Integer.parseInt(foreignChildCount.getText())));
        if(foreignChildPrice >= ticketPrice.get("ForeignChild")) {
            foreignChildPrice -= ticketPrice.get("ForeignChild");
        }
        totalLabel.setText(getTotalPrice()+"");

    }

    @FXML
    void changePrice(ActionEvent event) {
        //set the current ticket prices in labels
        adultLabel1.setText(ticketPrice.get("Adult")+"");
        childLabel1.setText(ticketPrice.get("Child").toString());
        foreignChildLabel1.setText(ticketPrice.get("ForeignChild").toString());
        foreignLabel1.setText(ticketPrice.get("Foreign").toString());
        studentLabel1.setText(ticketPrice.get("Student").toString());

        //set the new ticket prices in textfields
        adultNewPrice.setText(ticketPrice.get("Adult")+"");
        childNewPrice.setText(ticketPrice.get("Child").toString());
        fChildNewPrice.setText(ticketPrice.get("ForeignChild").toString());
        foreignNewPrice.setText(ticketPrice.get("Foreign").toString());
        studentNewPrice.setText(ticketPrice.get("Student").toString());

        ticketPane.setVisible(false);
        changePricePane.setVisible(true);
        buttonGrid.setVisible(false);

    }

    @FXML
    void completePurchase(ActionEvent event) {
        ticketDto = new TicketDto(
                getTotalPrice(),
                paymentOptions.getValue(),
                Integer.parseInt(adultCount.getText()),
                Integer.parseInt(childCount.getText()),
                Integer.parseInt(foreignCount.getText()),
                Integer.parseInt(foreignChildCount.getText()),
                Integer.parseInt(studentCount.getText())
        );
        try {
            ticketModel.addTicket(ticketDto);
            new ShowNotification("Ticket Purchased",
                    "Ticket purchased successfully",
                    "success.png",
                    ""
            ).start();
            ClearFields(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void reloadNewPrice(String type) {
        try {
            ticketPrice = ticketModel.getTicketPrice();
            switch (type){
                case "main":
                    adultLabel.setText(ticketPrice.get("Adult").toString());
                    childLabel.setText(ticketPrice.get("Child").toString());
                    foreignChildLabel.setText(ticketPrice.get("ForeignChild").toString());
                    foreignLabel.setText(ticketPrice.get("Foreign").toString());
                    studentLabel.setText(ticketPrice.get("Student").toString());
                    break;
                case "":
                    adultLabel1.setText(ticketPrice.get("Adult")+"");
                    childLabel1.setText(ticketPrice.get("Child").toString());
                    foreignChildLabel1.setText(ticketPrice.get("ForeignChild").toString());
                    foreignLabel1.setText(ticketPrice.get("Foreign").toString());
                    studentLabel1.setText(ticketPrice.get("Student").toString());
                    break;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveNewTicketPrice(ActionEvent event){
        ticketModel.updateTicketPrice("Adult", Double.parseDouble(adultNewPrice.getText()));
        ticketModel.updateTicketPrice("Child", Double.parseDouble(childNewPrice.getText()));
        ticketModel.updateTicketPrice("ForeignChild", Double.parseDouble(fChildNewPrice.getText()));
        ticketModel.updateTicketPrice("Foreign", Double.parseDouble(foreignNewPrice.getText()));
        ticketModel.updateTicketPrice("Student", Double.parseDouble(studentNewPrice.getText()));
        new ShowNotification("Ticket Prices Updated",
                "New ticket prices saved successfully",
                "update.png",
                ""
        ).start();
        reloadNewPrice("");
    }

    @FXML
    void backAction(ActionEvent event){
        ticketPane.setVisible(true);
        changePricePane.setVisible(false);
        buttonGrid.setVisible(true);
        reloadNewPrice("main");
    }
}
