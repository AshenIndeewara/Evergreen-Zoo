package com.evergreen.zoo.model;

import com.evergreen.zoo.dto.TicketDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TicketModel {
    public HashMap<String, Integer> getTicketPrice() throws SQLException {
        HashMap<String, Integer> ticketPrice = new HashMap<>();
        String sql = "select * from ticket";
        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()) {
            ticketPrice.put(rs.getString("ticketType"), rs.getInt("price"));
        }
        return ticketPrice;
    }

    public void updateTicketPrice(String adult, double v) {
        String sql = "update ticket set price=? where ticketType=?";
        try {
            CrudUtil.execute(sql, v, adult);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addTicket(TicketDto ticketDto) {
        //String sql = "insert into ticket values(?,?,?,?,?,?,?,?)";
        String sql = "INSERT INTO `visitor` (`total`, `child`, `adult`, `foreigner`, `foreignerChild`, `student`) VALUES (?, ?, ?, ?, ?,?)";
        try {
            CrudUtil.execute(sql, ticketDto.getTotal(), ticketDto.getChild(), ticketDto.getAdult(), ticketDto.getForeigner(), ticketDto.getForeignerChild(), ticketDto.getStudent());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
