package com.evergreen.zoo.model;

import com.evergreen.zoo.dto.tanleDto.AnimalTDto;
import com.evergreen.zoo.dto.tanleDto.SpeciesDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SpeciesModel {

    public ArrayList<SpeciesDto> getSpecies() {
        String query = "SELECT * FROM species";
        String speciesCount = "SELECT COUNT(*) FROM animal WHERE speciesID = ?";
        String speciesDiet = "SELECT * FROM food WHERE foodId = ?";
        ArrayList<SpeciesDto> speciesDtos = new ArrayList<>();
        try {
             ResultSet rs = CrudUtil.execute(query);
             while (rs.next()) {
                 SpeciesDto speciesDto = new SpeciesDto();
                 speciesDto.setSpeciesID(rs.getString(1));
                 speciesDto.setSpeciesName(rs.getString(2));
                ResultSet rs2 = CrudUtil.execute(speciesCount, rs.getString(1));
                if (rs2.next()) {
                    speciesDto.setSpeciesCount(rs2.getInt(1));
                }
                ResultSet rs3 = CrudUtil.execute(speciesDiet, rs.getString(4));
                if (rs3.next()) {
                    speciesDto.setSpeciesDiet(rs3.getString(2));
                }
                 speciesDto.setSpeciesStatus(rs.getString(3));
                 speciesDtos.add(speciesDto);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return speciesDtos;
    }

    public ArrayList<AnimalTDto> getAnimals(String speciesID) {
        String query = "SELECT * FROM animal WHERE speciesID = ?";
        ArrayList<AnimalTDto> animals = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(query, speciesID);
            while (rs.next()) {
                AnimalTDto animal = new AnimalTDto();
                animal.setName(rs.getString(2));
                animals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animals;
    }

    public boolean addSpecies(String speciesName, String speciesDiet, String speciesStatus) {
        String query = "INSERT INTO species (name, foodId, ConservationStatus) VALUES (?, ?, ?)";
        String foodIdSQL = "SELECT * FROM food WHERE name = ?";
        try {
            ResultSet rs = CrudUtil.execute(foodIdSQL, speciesDiet);
            if (rs.next()) {
                String foodId = rs.getString(1);
                return CrudUtil.execute(query, speciesName, foodId, speciesStatus);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getDiets() {
        String query = "SELECT * FROM food";
        ArrayList<String> diets = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(query);
            while (rs.next()) {
                diets.add(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diets;
    }
}
