package com.evergreen.zoo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {


    @FXML
    private Button animalBTN;

    @FXML
    private Button dashboardBTN;

    @FXML
    private Button reportBTN;

    @FXML
    private Button settingsBTN;

    @FXML
    private Button speciesBTN;

    @FXML
    private Button staffBTN;

    @FXML
    private Button stockBTN;

    @FXML
    private Button supplierBTN;

    @FXML
    private Button ticketBTN;


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

    private int role;

    public void setRole(int role) {
        this.role = role;
        System.out.println("Role set to: " + role);
        if(role == 2){
            staffBTN.setVisible(false);
            staffBTN.setManaged(false);

            supplierBTN.setVisible(false);
            supplierBTN.setManaged(false);

            stockBTN.setVisible(false);
            stockBTN.setManaged(false);

            ticketBTN.setVisible(false);
            ticketBTN.setManaged(false);

            reportBTN.setVisible(false);
            reportBTN.setManaged(false);
        } else if (role == 3) {
            ticketBTN.setVisible(false);
            ticketBTN.setManaged(false);

            reportBTN.setVisible(false);
            reportBTN.setManaged(false);
        } else if (role == 4) {
            staffBTN.setVisible(false);
            staffBTN.setManaged(false);

            supplierBTN.setVisible(false);
            supplierBTN.setManaged(false);

            stockBTN.setVisible(false);
            stockBTN.setManaged(false);

            reportBTN.setVisible(false);
            reportBTN.setManaged(false);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titalLable.setText("Dashboard");
        try {
            callPane("admin/dashboard.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setDate();
        setTime();
    }

    private void setDate() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
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
    void callAnimals(ActionEvent event) throws IOException {
        AnimalPaneController animalPaneController = new AnimalPaneController();
        animalPaneController.setRole(role);
        titalLable.setText("Animal Management");
        callPane("animalPane.fxml");
    }

    @FXML
    void callDashBoard(ActionEvent event) throws IOException {
        DashboardController dashboardController = new DashboardController();
        dashboardController.setRole(role);
        titalLable.setText("DashBoard");
        callPane("admin/dashboard.fxml");

    }

    @FXML
    void callSpecies(ActionEvent event) throws IOException {
        SpeciesController speciesController = new SpeciesController();
        speciesController.setRole(role);
        titalLable.setText("Species Management");
        callPane("speciesPane.fxml");

    }
    @FXML
    void callReports(ActionEvent event) throws IOException {
        titalLable.setText("Reports Management");
        callPane("reportPane.fxml");
    }

    @FXML
    void callSettings(ActionEvent event) {
        titalLable.setText("Settings");

    }

    void callPane(String path) throws IOException {
        String fullPath = "/view/"+path;
        AnchorPane newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + path)));
        mainPane.getChildren().setAll(newPane);
    }
    @FXML
    void callStaff(ActionEvent event) throws IOException {
        StaffController staffController = new StaffController();
        staffController.setRole(role);
        titalLable.setText("Staff Management");
        callPane("staffPane.fxml");

    }

    @FXML
    void callTicket(ActionEvent event) throws IOException {
        TicketPaneController ticketPaneController = new TicketPaneController();
        ticketPaneController.setRole(role);
        titalLable.setText("Ticket Management");
        callPane("ticketPane.fxml");
    }

    @FXML
    void callStocks(ActionEvent event) throws IOException {
        StockManageController stockManageController = new StockManageController();
        stockManageController.setRole(role);
        titalLable.setText("Stock Management");
        callPane("stockManage.fxml");
    }

    @FXML
    void callSupplier(ActionEvent event) throws IOException {
        SupplierController supplierController = new SupplierController();
        supplierController.setRole(role);
        titalLable.setText("Supplier Management");
        callPane("supplierPane.fxml");

    }

    @FXML
    void callLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/loginPane.fxml"));
        Parent root = loader.load();
        Stage newWindow = new Stage();
        newWindow.setScene(new Scene(root));
        newWindow.show();
        Stage window = (Stage) mainPane.getScene().getWindow();
        window.close();
    }

}
