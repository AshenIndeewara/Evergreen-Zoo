package com.evergreen.zoo.model;

import com.evergreen.zoo.dto.reportDto.AnimalReportDto;
import com.evergreen.zoo.dto.reportDto.TicketReportDto;
import com.evergreen.zoo.dto.tanleDto.*;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportModel {
    public ArrayList<CustomerTDto> getCustomerReport(){
        ArrayList<CustomerTDto> customerTDto = new ArrayList<>();
        String query = "SELECT * FROM visitor";
        try {
            ResultSet resultSet = CrudUtil.execute(query);
            int count = 1;
            while (resultSet.next()) {
                CustomerTDto customer = new CustomerTDto();
                customer.setCustomerID(count);
                count++;
                customer.setName(resultSet.getString(3));
                customer.setEmail(resultSet.getString(5));
                customer.setPhone(resultSet.getString(3));
                customer.setDate(resultSet.getString(2));
                customerTDto.add(customer);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerTDto;

    }

    public ArrayList<TicketReportDto> getSalesReport() {
        ArrayList<TicketReportDto> ticketTDto = new ArrayList<>();
        String query = "SELECT t.ticketID, t.ticketType, t.price, SUM(vd.qty) AS totalQty, SUM(vd.qty * t.price) AS totalPrice FROM ticket t LEFT JOIN visitorDetails vd ON t.ticketID = vd.ticketID GROUP BY t.ticketID, t.ticketType, t.price";
        try {
            ResultSet resultSet = CrudUtil.execute(query);
            while (resultSet.next()) {
                TicketReportDto ticket = new TicketReportDto();
                ticket.setTicketID(resultSet.getInt(1));
                ticket.setTicketType(resultSet.getString(2));
                ticket.setPrice(resultSet.getDouble(3));
                ticket.setQty(resultSet.getInt(4));
                ticket.setTotalPrice(resultSet.getDouble(5));
                ticketTDto.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketTDto;

    }

    public ArrayList<AnimalReportDto> getAnimalReport() {
        ArrayList<AnimalReportDto> animalReportDtos = new ArrayList<>();
        String query = "SELECT a.animalId, a.nickName, s.name AS species, f.name AS foodName, a.age FROM animal a INNER JOIN species s ON a.speciesId = s.id INNER JOIN food f ON s.foodId = f.foodId";
        try {
            ResultSet resultSet = CrudUtil.execute(query);
            int count = 1;
            while (resultSet.next()) {
                AnimalReportDto animal = new AnimalReportDto();
                animal.setAnimalID(count++);
                animal.setName(resultSet.getString(2));
                animal.setSpecies(resultSet.getString(3));
                animal.setDiet(resultSet.getString(4));
                animal.setAge(resultSet.getInt(5));
                animalReportDtos.add(animal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animalReportDtos;
    }

    public ArrayList<StaffDto> getAllStaff(){
        ArrayList<StaffDto> staff = new ArrayList<>();
        String sql = "select * from employee";
        try {
            ResultSet rs = CrudUtil.execute(sql);
            int count = 1;
            while (rs.next()) {
                StaffDto newStaff = new StaffDto(
                        count++,
                        rs.getString("name"),
                        getRoleDescription(rs.getInt("position")),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                staff.add(newStaff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public String getRoleDescription(int roleID) throws SQLException {
        String sql = "select description from role where role_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, roleID);
        while (resultSet.next()) {
            return resultSet.getString("description");
        }
        return null;
    }

    public ArrayList<SupplierDto> getAllSupplier() {
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
}
