package com.evergreen.zoo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.evergreen.zoo.util.CrudUtil;
public class DashboardModel {
    public HashMap<String, String> status() {
        HashMap<String, String> status = new HashMap<>();
        String sql = "select count(*) as count from ";
        String sql1 = "select count(*) as count from ";
        String sql2 = "select count(*) as count from ";
        String sql3 = "SELECT SUM(child) + SUM(adult) + SUM(foreigner) + SUM(foreignerChild) + SUM(student) AS total_visitors FROM ";
        String[] tables = {"employee", "animal", "eventPrograms", "visitor"};
        String[] sqls = {sql, sql1, sql2, sql3};
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
}
