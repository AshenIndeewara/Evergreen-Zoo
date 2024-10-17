package com.evergreen.zoo.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StaffController {

    @FXML
    private TableColumn<?, ?> actionCol;

    @FXML
    private TableColumn<?, ?> cntactNumber;

    @FXML
    private TableColumn<?, ?> depCol;

    @FXML
    private TableColumn<?, ?> namCol;

    @FXML
    private TableColumn<?, ?> roleCol;

    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton searchBtnClicked;

    @FXML
    private TableView<?> staffTable;

    @FXML
    void addStaffClick(ActionEvent event) {

    }

}

