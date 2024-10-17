package com.evergreen.zoo.model;
import com.evergreen.zoo.db.DBConnection;
import com.evergreen.zoo.dto.ForgotDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotModel {

    public static Boolean isChangeUserPW(ForgotDto forgotDto, String newPW) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newPW);
        preparedStatement.setString(2, forgotDto.getUsername());

        return preparedStatement.executeUpdate() > 0;
    }

    public static ForgotDto getUserData(String username) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from users where username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fogrtSql = "select * from employee where userId=?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(fogrtSql);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet1.next()) {
                ForgotDto forgotDto = new ForgotDto();
                forgotDto.setId(resultSet1.getString("id"));
                forgotDto.setEmail(resultSet1.getString("email"));
                forgotDto.setPhoneNumber(resultSet1.getString("phone"));
                forgotDto.setUsername(username);
                System.out.println(forgotDto.toString());
                return forgotDto;

            }

        }
        return null;
    }

}
