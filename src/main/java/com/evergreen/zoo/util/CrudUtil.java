package com.evergreen.zoo.util;

import com.evergreen.zoo.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        if(connection.isClosed()){
            connection = DBConnection.getInstance().getConnection();
        }
        PreparedStatement pst = connection.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
            pst.setObject((i + 1), obj[i]);
        }
        System.out.println(pst.toString());
        try {
            if (sql.startsWith("SELECT") || sql.startsWith("select")) {
                ResultSet resultSet = pst.executeQuery();
                return (T) resultSet;
            } else {
                int i = pst.executeUpdate();
                boolean isDone = i > 0;
                return (T) ((Boolean) isDone);
            }
        } catch (SQLException e) {
            throw new SQLException("Error executing CrudUtil: " + e.getMessage(), e);
        }
    }
}
