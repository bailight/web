package com.web;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


@Named
@ApplicationScoped
public class PointBean implements Serializable {

    private double x = 0.0;
    private double xHidden = 0.0;
    private double y = 0.0;
    private double r = 1.0;
    private boolean check;
    private String timeNow;
    private String executionTime;
    private TabManager DBManager;

    public PointBean() throws SQLException {
    }

    public void setX(double x){
        this.x = x;
    }

    public double getX() {
        return x;
    }

    @PostConstruct
    public void init() throws SQLException {
        this.DBManager = new TabManager();
    }

    public void setXHidden(double xHidden){
        this.xHidden = xHidden;
        this.x = xHidden;
    }

    public double getXHidden() {
        return xHidden;
    }

    public void setY(double y){
        this.y = y;
    }
    public double getY() {
        return y;
    }

    public void setR(double r) {
        this.r = r;
    }
    public double getR() {
        return r;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
    public boolean getCheck() {
        return check;
    }

    public void setTimeNow(String timeNow){
        this.timeNow = timeNow;
    }
    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getTimeNow() {
        return timeNow;
    }


    private static boolean inGraph(double x, double y, double r){
        if (x<=0 && y>=0){
            return -r/2<x && y<r; //第二象限 三角形
        } else if (x>=0 && y>=0) {
            return y<r && x<r; //第一象限 正方形
        } else if (x >= 0 && y<=0) {
            return x * x + y * y < r * r; //第四象限 1/4圆形
        }
        return false; //第三象限 无色块
    }

    public void checkPoint() throws SQLException {
        long startTime = System.nanoTime();

        this.check = inGraph(x, y, r);

        timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        long executionTime = System.nanoTime() - startTime;
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        point.setCheck(this.check);
        point.setTimeNow(timeNow);
        point.setExecutionTime(String.valueOf(executionTime));
        DBManager.Add(point);
    }

    public void checkPoint2() throws SQLException {
        long startTime = System.nanoTime();
        this.check = inGraph(xHidden, y, r);

        timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        long executionTime = System.nanoTime() - startTime;
        Point point = new Point();
        point.setX(xHidden);
        point.setY(y);
        point.setR(r);
        point.setCheck(this.check);
        point.setTimeNow(timeNow);
        point.setExecutionTime(String.valueOf(executionTime));
        DBManager.Add(point);
    }

    public String getPointsAsJson() throws SQLException {
        ArrayList<Point> points = DBManager.show();
        JSONArray jsonArray = new JSONArray();
        for (Point point : points) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", point.getX());
            jsonObject.put("y", point.getY());
            jsonObject.put("r", point.getR());
            jsonObject.put("check", point.getCheck());
            jsonObject.put("timeNow", point.getTimeNow());
            jsonObject.put("executionTime", point.getExecutionTime());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    public ArrayList<Point> getPoints() throws SQLException{
        return DBManager.show();
    }

    public void clearPoint() {
        DBManager.clearAll();
    }

}
