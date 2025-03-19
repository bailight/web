package com.web;

import java.io.Serializable;

public class Point implements Serializable {
    private int id;
    private double x;
    private double y;
    private double r;
    private boolean check;
    private String timeNow;
    private String executionTime;

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getCheck() {
        return check;
    }

    public String getTimeNow() {
        return this.timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }
}
