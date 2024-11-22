package com.evergreen.zoo.controller;

import com.evergreen.zoo.dto.tanleDto.AnimalTDto;
import com.evergreen.zoo.dto.tanleDto.SpeciesDto;
import com.evergreen.zoo.model.SpeciesModel;
import com.evergreen.zoo.util.ShowNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SpeciesController implements Initializable {

    @FXML
    private TableView<AnimalTDto> animalTable;

    @FXML
    private TableColumn<AnimalTDto, String> animalName;

    @FXML
    private ComboBox<String> diteTXT;

    @FXML
    private TableView<SpeciesDto> itemTable;

    @FXML
    private TextField statusTXT;

    @FXML
    private TableColumn<SpeciesDto, Integer> speciesCount;

    @FXML
    private TableColumn<SpeciesDto, String> speciesDite;

    @FXML
    private TableColumn<SpeciesDto, String> speciesName;

    @FXML
    private TableColumn<SpeciesDto, String> speciesStatus;

    @FXML
    private TextField speciesTXT;

    SpeciesModel speciesModel = new SpeciesModel();

    @Setter
    private int role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        speciesName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("speciesName"));
        speciesDite.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("speciesDiet"));
        speciesStatus.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("speciesStatus"));
        speciesCount.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("speciesCount"));
        try{
            loadSpecies();
            loadDiets();
        } catch (Exception e) {
            System.out.println("error : "+ e.getMessage());
        }
    }

    private void loadSpecies() {
        ArrayList<SpeciesDto> species = speciesModel.getSpecies();
        itemTable.getItems().clear();
        itemTable.getItems().addAll(species);
    }

    private void loadDiets() {
        ArrayList<String> diets = speciesModel.getDiets();
        diteTXT.getItems().clear();
        diteTXT.getItems().addAll(diets);
    }


    @FXML
    void addSpecies(ActionEvent event) {
        String speciesName = speciesTXT.getText();
        String speciesDiet = diteTXT.getValue();
        String speciesStatus = statusTXT.getText();
        boolean b = speciesModel.addSpecies(speciesName, speciesDiet, speciesStatus);
        if (b) {
            loadSpecies();
            new ShowNotification("Species Added",
                    "Species has been added successfully",
                    "success.png",
                    "he he login notification eka click kala"
            ).start();
        }else{
            new ShowNotification("Species Added Failed",
                    "Species has not been added successfully",
                    "unsuccess.png",
                    "he he login notification eka click kala"
            ).start();
        }
    }

    @FXML
    void getAnimals(MouseEvent event) {
        animalName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        SpeciesDto selectedItem = itemTable.getSelectionModel().getSelectedItem();
        ArrayList<AnimalTDto> animals = speciesModel.getAnimals(selectedItem.getSpeciesID());
        animalTable.getItems().clear();
        animalTable.getItems().addAll(animals);
    }

}
