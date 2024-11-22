package com.evergreen.zoo.model;

import com.evergreen.zoo.dto.TicketDto;
import com.evergreen.zoo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
        String sql = "insert into visitor (name, email, number) values (?,?,?)";
        try {
            CrudUtil.execute(sql, ticketDto.getName(), ticketDto.getEmail(), ticketDto.getNum());
            ResultSet rs = CrudUtil.execute("select max(visitorID) from visitor");
            if (rs.next()) {
                int visitorID = rs.getInt(1);
                if(ticketDto.getAdult() > 0) {
                    String sql1 = "insert into visitordetails (visitorID, ticketID, qty) values (?, ?, ?)";
                    CrudUtil.execute(sql1, visitorID, 1, ticketDto.getAdult());
                }
                if(ticketDto.getChild() > 0) {
                    String sql1 = "insert into visitordetails (visitorID, ticketID, qty) values (?, ?, ?)";
                    CrudUtil.execute(sql1, visitorID, 2, ticketDto.getChild());
                }
                if(ticketDto.getStudent() > 0) {
                    String sql1 = "insert into visitordetails (visitorID, ticketID, qty) values (?, ?, ?)";
                    CrudUtil.execute(sql1, visitorID, 3, ticketDto.getStudent());
                }
                if(ticketDto.getForeigner() > 0) {
                    String sql1 = "insert into visitordetails (visitorID, ticketID, qty) values (?, ?, ?)";
                    CrudUtil.execute(sql1, visitorID, 4, ticketDto.getForeigner());
                }
                if(ticketDto.getForeignerChild() > 0) {
                    String sql1 = "insert into visitordetails (visitorID, ticketID, qty) values (?, ?, ?)";
                    CrudUtil.execute(sql1, visitorID, 5, ticketDto.getForeignerChild());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Map<String, String> getUser(String number) {
        Map<String, String> user = new HashMap<>();
        String sql = "select * from visitor where number=?";
        try {
            ResultSet rs = CrudUtil.execute(sql, number);
            if (rs.next()) {
                user.put("name", rs.getString("name"));
                user.put("email", rs.getString("email"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
