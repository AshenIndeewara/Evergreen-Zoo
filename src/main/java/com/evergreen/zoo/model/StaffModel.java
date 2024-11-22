package com.evergreen.zoo.model;

import com.evergreen.zoo.dto.tanleDto.StaffDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffModel {
    public ArrayList<StaffDto> getAllStaff() throws SQLException {
        String sql = "select * from employee";
        ResultSet rs = CrudUtil.execute(sql);
        ArrayList<StaffDto> staff = new ArrayList<>();
        while (rs.next()) {
            StaffDto newStaff = new StaffDto(
                    rs.getString("name"),
                    getRoleDescription(rs.getInt("position")),
                    rs.getString("phone"),
                    rs.getString("email")
            );
            staff.add(newStaff);
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

    public void addStaff(StaffDto staff) throws SQLException {
        String sql = "insert into employee values(?,?,?,?)";
        CrudUtil.execute(sql, staff.getStaffName(), staff.getStaffRole(), staff.getStaffContact(), staff.getStaffEmail());
    }

    public ArrayList<StaffDto> searchStaff(String name) throws SQLException {
        String sql = "select * from employee where name like ?";
        ResultSet rs = CrudUtil.execute(sql, name+"%");
        ArrayList<StaffDto> staff = new ArrayList<>();
        while (rs.next()) {
            StaffDto newStaff = new StaffDto(
                    rs.getString("name"),
                    getRoleDescription(rs.getInt("position")),
                    rs.getString("phone"),
                    rs.getString("email")
            );
            staff.add(newStaff);
        }
        return staff;
    }

    public Boolean deleteStaff(StaffDto staffDto) {
        String sql = "delete from employee where phone=?, where email=?, where name=?";
        try {
            CrudUtil.execute(sql, staffDto.getStaffContact(), staffDto.getStaffEmail(), staffDto.getStaffName());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getEmployeeId(StaffDto staffDto) {
        String sql = "SELECT id FROM employee WHERE email = ? AND name = ? AND phone = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, staffDto.getStaffEmail(), staffDto.getStaffName(), staffDto.getStaffContact());
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateStaff(StaffDto staffDto, int userId) {
        System.out.println(staffDto.toString());
        String getROleId = "select role_id from role where description=?";
        String sql = "update employee set name=?, position=?, phone=?, email=? where id=?";
        try {
            ResultSet resultSet = CrudUtil.execute(getROleId, staffDto.getStaffRole());
            if (resultSet.next()) {
                CrudUtil.execute(sql, staffDto.getStaffName(), resultSet.getInt("role_id"), staffDto.getStaffContact(), staffDto.getStaffEmail(), userId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
