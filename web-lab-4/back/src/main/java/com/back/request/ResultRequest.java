package com.back.request;


public class ResultRequest {
    private double x;
    private double y;
    private double r;
    private String username;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "ResultRequest{" +
                "x =" + x +
                ", y =" + y +
                ", r =" + r +
                ", username ='" + username + '\'' +
                '}';
    }
}
