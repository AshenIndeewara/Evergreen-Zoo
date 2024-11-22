package com.evergreen.zoo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.evergreen.zoo.dto.tanleDto.DashboardDto;
import com.evergreen.zoo.util.CrudUtil;
import javafx.scene.chart.XYChart;

public class DashboardModel {
    public HashMap<String, String> status() {
        HashMap<String, String> status = new HashMap<>();
        String sql = "select count(*) as count from ";
        String sql1 = "select count(*) as count from ";
        String sql3 = "select sum(qty) as count from ";
        String[] tables = {"employee", "animal", "visitordetails"};
        String[] sqls = {sql, sql1, sql3};
        try {
            for (int i = 0; i < tables.length; i++) {
                ResultSet rs = CrudUtil.execute(sqls[i] + tables[i]);
                if (rs.next()) {
                    status.put(tables[i], rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public ArrayList<DashboardDto> getSpecies() {
        String sql = "SELECT * FROM species";
        String speciesCount = "SELECT COUNT(*) FROM animal WHERE speciesID = ?";
        ArrayList<DashboardDto> dashboardDtos = new ArrayList<>();
        try {
            ResultSet rs = CrudUtil.execute(sql);
            while (rs.next()) {
                DashboardDto dashboardDto = new DashboardDto();
                dashboardDto.setSpecies(rs.getString(2));
                ResultSet rs2 = CrudUtil.execute(speciesCount, rs.getString(1));
                if (rs2.next()) {
                    dashboardDto.setCount(rs2.getInt(1));
                }
                dashboardDto.setStatus(rs.getString(3));
                dashboardDtos.add(dashboardDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dashboardDtos;
    }

    public XYChart.Series<String, Number> getData() {
        String query = "SELECT DATE_FORMAT(v.date, '%Y-%m') AS month, SUM(vd.qty) AS total_qty FROM visitorDetails vd JOIN visitor v ON vd.visitorID = v.visitorID GROUP BY DATE_FORMAT(v.date, '%Y-%m') ORDER BY month";
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        try {
            ResultSet resultSet = CrudUtil.execute(query);
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int totalQty = resultSet.getInt("total_qty");
                System.out.println(month + " " + totalQty);
                series.getData().add(new XYChart.Data<>(month, totalQty));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return series;
    }
}
