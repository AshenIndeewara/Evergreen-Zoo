package com.evergreen.zoo.model;
import com.evergreen.zoo.dto.LoginDto;
import com.evergreen.zoo.db.DBConnection;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginModel {
    public ResultSet checkLogin(LoginDto loginDto) throws Exception{
        String sql = "SELECT e.name, e.email, e.phone, e.position, users.password FROM users JOIN employee e ON users.id = e.userId WHERE users.username = ?";
        ResultSet rst = CrudUtil.execute(sql, loginDto.getUsername());
        if (rst.next()) {
            return rst;
        }
        return null;
    }
}
