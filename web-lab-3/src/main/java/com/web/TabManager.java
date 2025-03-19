package com.web;

import java.sql.*;
import java.util.ArrayList;

public class TabManager {
//    public static final String DATABASE_URL_HELIOS = "jdbc:postgresql://pg/studs";
    public static final String DATABASE_URL_LOCAL = "jdbc:postgresql://localhost:11001/test";
    private final Connection connection;

    public TabManager() throws SQLException {
        this.connection = DriverManager.getConnection(DATABASE_URL_LOCAL,"Baili","aaa111...");
        createDataTable();
    }

    private void createDataTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Points"
                + "(id SERIAL PRIMARY KEY, "
                + " x double precision NOT NULL, "
                + " y double precision NOT NULL, "
                + " r double precision NOT NULL, "
                + " Result BOOLEAN NOT NULL DEFAULT false, "
                + " CurTime TEXT NOT NULL,"
                + " ExecutionTime TEXT NOT NULL)");
    }

    public ArrayList<Point> show() throws SQLException {
        ArrayList<Point> points = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Points");
        while (result.next()) {
            points.add(getPoint(result));
        }
        return points;
    }

    private Point getPoint(ResultSet result) throws SQLException {
        Point point = new Point();
        point.setId(result.getInt("id"));
        point.setX(result.getDouble("x"));
        point.setY(result.getDouble("y"));
        point.setR(result.getDouble("r"));
        point.setCheck(result.getBoolean("Result"));
        point.setTimeNow(result.getString("CurTime"));
        point.setExecutionTime(result.getString("ExecutionTime"));
        return point;
    }

    public void Add(Point point) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Points"
                    + "(x, y, r, Result, CurTime, ExecutionTime) "
                    + "VALUES (?,?,?,?,?,?) RETURNING id");
            statement.setDouble(1, point.getX());
            statement.setDouble(2, point.getY());
            statement.setDouble(3, point.getR());
            statement.setBoolean(4, point.getCheck());
            statement.setString(5, point.getTimeNow());
            statement.setString(6, point.getExecutionTime());
            ResultSet result = statement.executeQuery();
            result.next();
        } catch (SQLException e) {
            System.out.println("Элемент не добавлен, ошибка в sql");
        }
    }

    public void clearAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM points");
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
