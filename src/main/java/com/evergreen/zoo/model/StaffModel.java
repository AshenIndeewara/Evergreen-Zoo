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
                    rs.getInt("position"),
                    rs.getString("phone"),
                    rs.getString("email")
            );
            System.out.println(newStaff.toString());
            staff.add(newStaff);
        }
        return staff;
    }
}
