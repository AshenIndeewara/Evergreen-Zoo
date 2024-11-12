package com.evergreen.zoo.model;

import com.evergreen.zoo.dto.FoodDto;
import com.evergreen.zoo.dto.tanleDto.SupplierDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SupplierModel {
    public ArrayList<SupplierDto> getSuppliers() {
        String sql = "SELECT * FROM supplier";
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(sql);
            while (rs.next()) {
                SupplierDto supplierDto = new SupplierDto();
                supplierDto.setSupplierID(rs.getString(1));
                supplierDto.setName(rs.getString(2));
                supplierDto.setAddress(rs.getString(5));
                supplierDto.setEmail(rs.getString(4));
                supplierDto.setPhone(rs.getString(3));
                supplierDto.setDescription(rs.getString(6));
                supplierDtos.add(supplierDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierDtos;
    }

    public Boolean addSupplier(SupplierDto supplierDto) {
        String sql = "INSERT INTO supplier (name, contact, email, address, description) VALUES (?,?,?,?,?)";
        try {
            return CrudUtil.execute(sql, supplierDto.getName(), supplierDto.getPhone(), supplierDto.getEmail(), supplierDto.getAddress(), supplierDto.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isUpdateSupplier(SupplierDto supplierDto) {
        String sql = "UPDATE supplier SET contact = ?, email = ?, address = ?, description = ? WHERE name = ?";
        try {
            return CrudUtil.execute(sql, supplierDto.getPhone(), supplierDto.getEmail(), supplierDto.getAddress(), supplierDto.getDescription(), supplierDto.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<FoodDto> getSupplierItems(String supplierID) {
        String sql = "SELECT * FROM food WHERE supplierID = ?";
        ArrayList<FoodDto> items = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(sql, supplierID);
            while (rs.next()) {
                FoodDto foodDto = new FoodDto();
                foodDto.setFoodID(rs.getString(1));
                foodDto.setName(rs.getString(2));
                foodDto.setQtyOnHand(rs.getInt(3));
                foodDto.setPrice(rs.getDouble(4));
                foodDto.setMinQty(rs.getInt(5));
                items.add(foodDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public Boolean isDeleteSupplier(SupplierDto supplierDto, ArrayList<FoodDto> items) {
        String sql = "DELETE FROM supplier WHERE name = ?";
        try {
            CrudUtil.execute(sql, supplierDto.getName());
            for (FoodDto item : items) {
                sql = "DELETE FROM food WHERE name = ?";
                CrudUtil.execute(sql, item.getName());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
