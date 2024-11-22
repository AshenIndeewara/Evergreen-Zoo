package com.evergreen.zoo.controller;

import com.evergreen.zoo.db.DBConnection;
import com.evergreen.zoo.dto.reportDto.AnimalReportDto;
import com.evergreen.zoo.dto.reportDto.TicketReportDto;
import com.evergreen.zoo.dto.tanleDto.*;
import com.evergreen.zoo.model.ReportModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private TableColumn<CustomerTDto, Integer> Cid;
    @FXML
    private TableColumn<AnimalReportDto, String> aAge;
    @FXML
    private TableColumn<AnimalReportDto, String> aDiet;
    @FXML
    private TableColumn<AnimalReportDto, Integer> aID;
    @FXML
    private TableColumn<AnimalReportDto, String> aName;
    @FXML
    private TableColumn<AnimalReportDto, String> aSpecies;
    @FXML
    private TableView<AnimalReportDto> animalData;
    @FXML
    private TableColumn<CustomerTDto, String> cDate;
    @FXML
    private TableColumn<CustomerTDto, String> cEmail;
    @FXML
    private TableColumn<CustomerTDto, String> cName;
    @FXML
    private TableColumn<CustomerTDto, String> cPhone;
    @FXML
    private TableView<CustomerTDto> customerDataTable;
    @FXML
    private TableColumn<SupplierDto, String> sAddress;
    @FXML
    private TableColumn<StaffDto, String> eAddress;
    @FXML
    private TableColumn<StaffDto, String> eContact;
    @FXML
    private TableColumn<StaffDto, String> eEmail;
    @FXML
    private TableColumn<StaffDto, Integer> eID;
    @FXML
    private TableColumn<StaffDto, String> eName;
    @FXML
    private TableColumn<StaffDto, String> eRole;
    @FXML
    private TableView<StaffDto> employeeData;
    @FXML
    private ComboBox<String> reportType;
    @FXML
    private TableColumn<SupplierDto, String> sDescription;
    @FXML
    private TableColumn<SupplierDto, String> sEmail;
    @FXML
    private TableColumn<SupplierDto, Integer> sID;
    @FXML
    private TableColumn<SupplierDto, String> sName;
    @FXML
    private TableColumn<SupplierDto, String> sPhone;
    @FXML
    private TableView<TicketReportDto> salesTable;
    @FXML
    private TableView<SupplierDto> supplierData;
    @FXML
    private TableColumn<TicketReportDto, Integer> tID;
    @FXML
    private TableColumn<TicketReportDto, String> tPrice;
    @FXML
    private TableColumn<TicketReportDto, String> tQTY;
    @FXML
    private TableColumn<TicketReportDto, String> tTotal;
    @FXML
    private TableColumn<TicketReportDto, String> tType;
    @FXML
    private AnchorPane tablePane;

    ReportModel reportModel = new ReportModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportType.getItems().addAll("Customer Report", "Sales Report", "Animal Report", "Employee Report", "Supplier Report");

        // Setting up TableColumn values
        Cid.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        tType.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        tPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        aID.setCellValueFactory(new PropertyValueFactory<>("animalID"));
        aName.setCellValueFactory(new PropertyValueFactory<>("name"));
        aSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
        aDiet.setCellValueFactory(new PropertyValueFactory<>("diet"));
        aAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        eID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        eName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        eRole.setCellValueFactory(new PropertyValueFactory<>("staffRole"));
        eContact.setCellValueFactory(new PropertyValueFactory<>("staffContact"));
        eEmail.setCellValueFactory(new PropertyValueFactory<>("staffEmail"));
        eAddress.setCellValueFactory(new PropertyValueFactory<>("staffAddress"));

        sID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        sName.setCellValueFactory(new PropertyValueFactory<>("name"));
        sPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        sEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        sAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        sDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    void getReportData(ActionEvent event) {
        tablePane.setVisible(false);
        customerDataTable.setVisible(false);
        salesTable.setVisible(false);
        animalData.setVisible(false);
        employeeData.setVisible(false);
        supplierData.setVisible(false);

        String report = reportType.getValue();
        tablePane.setVisible(true);
        if (report.equals("Customer Report")) {
            customerDataTable.setVisible(true);
            ArrayList<CustomerTDto> customerTDtos = reportModel.getCustomerReport();
            customerDataTable.getItems().clear();
            customerDataTable.getItems().addAll(customerTDtos);
        } else if (report.equals("Sales Report")) {
            salesTable.setVisible(true);
            ArrayList<TicketReportDto> ticketReportDtos = reportModel.getSalesReport();
            salesTable.getItems().clear();
            salesTable.getItems().addAll(ticketReportDtos);
        } else if (report.equals("Animal Report")) {
            animalData.setVisible(true);
            ArrayList<AnimalReportDto> animalReportDtos = reportModel.getAnimalReport();
            animalData.getItems().clear();
            animalData.getItems().addAll(animalReportDtos);
        } else if (report.equals("Employee Report")) {
            employeeData.setVisible(true);
            ArrayList<StaffDto> staffDtos = reportModel.getAllStaff();
            employeeData.getItems().clear();
            employeeData.getItems().addAll(staffDtos);
        } else if (report.equals("Supplier Report")) {
            supplierData.setVisible(true);
            ArrayList<SupplierDto> supplierDtos = reportModel.getAllSupplier();
            supplierData.getItems().clear();
            supplierData.getItems().addAll(supplierDtos);
        }
    }

    @FXML
    void getJReport(ActionEvent event) {
        String report = reportType.getValue();
        if (report.equals("Customer Report")) {
            showData("/report/customerReport.jrxml");
        } else if (report.equals("Sales Report")) {
            showData("/report/ticketReport.jrxml");
        } else if (report.equals("Animal Report")) {
            showData("/report/animalReport.jrxml");
        } else if (report.equals("Employee Report")) {
            showData("/report/staffReport.jrxml");
        } else if (report.equals("Supplier Report")) {
            showData("/report/supplierReport.jrxml");
        }
    }

    void showData(String path) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream(path)
            );
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
