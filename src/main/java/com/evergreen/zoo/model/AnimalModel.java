package com.evergreen.zoo.model;

import com.evergreen.zoo.db.DBConnection;
import com.evergreen.zoo.dto.AnimalDto;
import com.evergreen.zoo.dto.tanleDto.AnimalTDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class AnimalModel {
    public ArrayList<AnimalTDto> getAnimals() {
        String sql = "SELECT * FROM animal";
        String speciesNameAQL = "SELECT * FROM species WHERE id = ?";
        String foodNameSQL = "SELECT * FROM food WHERE foodId = ?";
        String healthNameSQL = "SELECT * FROM healthrecords WHERE animalId = ?";
        ArrayList<AnimalTDto> animals = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(sql);
            while (rs.next()) {
                AnimalTDto animal = new AnimalTDto();
                animal.setAnimalID(rs.getString(1));
                animal.setName(rs.getString(2));
                animal.setGender(rs.getString(4));
                animal.setAge(rs.getInt(5));
                ResultSet rs2 = CrudUtil.execute(speciesNameAQL, rs.getString(3));
                if (rs2.next()) {
                    animal.setSpecies(rs2.getString(2));
                    ResultSet rs3 = CrudUtil.execute(foodNameSQL, rs2.getString(4));
                    if (rs3.next()) {
                        animal.setDiet(rs3.getString(2));
                    }
                }
                ResultSet rs4 = CrudUtil.execute(healthNameSQL, rs.getString(1));
                if (rs4.next()) {
                    animal.setHealth(rs4.getString(5));
                    animal.setHealthDescription(rs4.getString(2));
                }
                animals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animals;
    }

    public ArrayList<String> getSpecies() {
        String sql = "SELECT * FROM species";
        ArrayList<String> species = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(sql);
            while (rs.next()) {
                species.add(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return species;
    }

    public Boolean isAddAnimal(AnimalDto animalDto) throws SQLException {
        String speciesSQL = "SELECT id FROM species WHERE name = ?";
        String animalSQL = "INSERT INTO animal (nickName, speciesId, gender, age) VALUES (?,?,?,?)";
        String animalHealthSQL = "INSERT INTO healthrecords (animalId, date, description, type) VALUES (?,?,?,?)";
        String lastAnimalIdSQL = "SELECT LAST_INSERT_ID() AS `id`";
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try{
            ResultSet rs = CrudUtil.execute(speciesSQL, animalDto.getSpecies());
            if(rs.next()){
                if(CrudUtil.execute(animalSQL, animalDto.getName(), rs.getString(1), animalDto.getGender(), animalDto.getAge())){
                    ResultSet rs2 = CrudUtil.execute(lastAnimalIdSQL);
                    if(rs2.next()){
                        if(CrudUtil.execute(animalHealthSQL, rs2.getString(1), sqlDate, animalDto.getHealthDescription(), animalDto.getHealth())){
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in isAddAnimal : "+ e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public String getDiet(String species) {
        String sql = "SELECT foodId FROM species WHERE name = ?";
        String foodNameSQL = "SELECT name FROM food WHERE foodId = ?";
        try {
            ResultSet rs = CrudUtil.execute(sql, species);
            if (rs.next()) {
                ResultSet rs2 = CrudUtil.execute(foodNameSQL, rs.getString(1));
                if (rs2.next()) {
                    return rs2.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<AnimalTDto> searchAnimal(String search) {
        String sql = "SELECT * FROM animal WHERE nickName LIKE ?";
        String speciesNameAQL = "SELECT * FROM species WHERE id = ?";
        String foodNameSQL = "SELECT * FROM food WHERE foodId = ?";
        String healthNameSQL = "SELECT * FROM healthrecords WHERE animalId = ?";
        ArrayList<AnimalTDto> animals = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(sql, "%" + search + "%");
            while (rs.next()) {
                AnimalTDto animal = new AnimalTDto();
                animal.setAnimalID(rs.getString(1));
                animal.setName(rs.getString(2));
                animal.setGender(rs.getString(4));
                animal.setAge(rs.getInt(5));
                ResultSet rs2 = CrudUtil.execute(speciesNameAQL, rs.getString(3));
                if (rs2.next()) {
                    animal.setSpecies(rs2.getString(2));
                    ResultSet rs3 = CrudUtil.execute(foodNameSQL, rs2.getString(4));
                    if (rs3.next()) {
                        animal.setDiet(rs3.getString(2));
                    }
                }
                ResultSet rs4 = CrudUtil.execute(healthNameSQL, rs.getString(1));
                if (rs4.next()) {
                    animal.setHealth(rs4.getString(5));
                    animal.setHealthDescription(rs4.getString(2));
                }
                animals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animals;
    }

    public Boolean isUpdateAnimal(String animalID, AnimalDto animalDto) {
        String updateAnimalSQL = "UPDATE animal SET nickName = ?, speciesId = ?, gender = ?, age = ? WHERE animalId = ?";
        String updateHealthSQL = "UPDATE healthrecords SET description = ?, type = ? WHERE animalId = ?";
        try {
            if(CrudUtil.execute(updateAnimalSQL, animalDto.getName(), animalDto.getSpecies(), animalDto.getGender(), animalDto.getAge(), animalID)){
                if(CrudUtil.execute(updateHealthSQL, animalDto.getHealthDescription(), animalDto.getHealth(), animalID)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean isDeleteAnimal(String animalID) throws SQLException {
        String animalSQL = "DELETE FROM animal WHERE animalId = ?";
        String healthSQL = "DELETE FROM healthrecords WHERE animalId = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            if(CrudUtil.execute(healthSQL, animalID)){
                if(CrudUtil.execute(animalSQL, animalID)){
                    connection.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public String getSpeciesID(String selectedItem) {
        String sql = "SELECT id FROM species WHERE name = ?";
        try {
            ResultSet rs = CrudUtil.execute(sql, selectedItem);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
