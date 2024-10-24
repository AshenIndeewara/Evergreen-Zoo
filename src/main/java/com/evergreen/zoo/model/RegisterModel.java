package com.evergreen.zoo.model;

import com.evergreen.zoo.db.DBConnection;
import com.evergreen.zoo.dto.RegisterDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterModel {
    public int getLastUserId() throws SQLException {
        String SqlGetID = "SELECT LAST_INSERT_ID() AS `id`";
        ResultSet execute = CrudUtil.execute(SqlGetID);
        if(execute.next()){
            return execute.getInt("id");
        }
        return -1;
    }

    public int getRoleIdByDescription(String description) throws SQLException {
        String sql = "select role_id from role where description=?";
        ResultSet resultSet = CrudUtil.execute(sql, description);
        if (resultSet.next()) {
            return resultSet.getInt("role_id");
        }
        return -1;
    }

    public ArrayList<String> getAllRoles() throws SQLException {
        ArrayList<String> roles = new ArrayList<>();
        String sqlAllDiscription = "select description from role";
        ResultSet resultSet = CrudUtil.execute(sqlAllDiscription);
        while (resultSet.next()) {
            roles.add(resultSet.getString("description"));
        }
        return roles;
    }

    public Boolean registorUser(RegisterDto registerDto) throws SQLException {
        System.out.println("registerUser run wenawa");
        Connection connection = DBConnection.getInstance().getConnection();

        String sqlAdd = "INSERT INTO `users` (`username`, `password`) VALUES (?, ?)";
        String SqlGetID = "SELECT LAST_INSERT_ID() AS `id`";
        try {
            connection.setAutoCommit(false);
            if(CrudUtil.execute(sqlAdd, registerDto.getUsername(), registerDto.getPassword())){
                System.out.println("user table ekata data add una");
                ResultSet execute = CrudUtil.execute(SqlGetID);
                if(execute.next()){
                    System.out.println("last id eka gatta "+ execute.getString("id"));
                    int userId = execute.getInt("id");
                    String sqlAddEmployee = "INSERT INTO `employee` (`name`, `email`, `phone`, `address`, `position`, `hire_date`, `userId`) VALUES (?, ?, ?, ?, ?,?, ?)";
                    if(CrudUtil.execute(sqlAddEmployee, registerDto.getName(), registerDto.getEmail(),registerDto.getPhone(),registerDto.getAddress(),registerDto.getRole(), LocalDate.now().toString(), userId)){
                        System.out.println("employee table ekata data add una");
                        connection.commit();
                        return true;
                    }
                }
            }
            throw new SQLException("Fail to save data...!");
        } catch (SQLException e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
