package com.back.util;

public class CheckPoint {
    public static boolean isValidX(double x) {
        return x >= -4 && x <= 4;
    }

    public static boolean isValidY(double y) {
        return y >= -3 && y <= 3;
    }

    public static boolean isValidR(double radius) {
        return radius >= -5 && radius <= 5;
    }

    public static boolean inGraph(double x, double y, double r){
        if (x<=0 && y>=0){
            return -r/2<x && y<r; //第二象限 三角形
        } else if (x>=0 && y>=0) {
            return y<r && x<r; //第一象限 正方形
        } else if (x >= 0 && y<=0) {
            return x * x + y * y < r * r; //第四象限 1/4圆形
        }
        return false; //第三象限 无色块
    }
}
