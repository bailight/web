package com.back.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "resultPoints")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double x;
    private double y;
    private double r;
    private boolean is_check;
    private String timeNow;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Result() {}

    public Result(double x, double y, double r, boolean is_check, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.is_check = is_check;
        this.timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.user = user;
    }

    public int getPointId() {
        return id;
    }

    public void setPointId(int pointId) {
        this.id = pointId;
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

    public void setCheck(boolean is_check) {
        this.is_check = is_check;
    }

    public boolean getCheck() {
        return is_check;
    }

    public String getTimeNow() {
        return this.timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", is_check=" + is_check +
                "}";
    }

}
