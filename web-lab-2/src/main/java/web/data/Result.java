package web.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Result {
    public double x;
    public double y;
    public double r;
    public boolean check;
    public String timeNow;
    public String executionTime;

    public Result(double x, double y, double r, boolean check, long startTime){
        this.x = x;
        this.y = y;
        this.r = r;
        this.check = check;
        this.timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.executionTime = String.valueOf(System.nanoTime() - startTime);
    }

    @Override
    public String toString(){
        return "Result={" +
                "x=" + x + "," +
                "y=" + y + "," +
                "r=" + r + "," +
                "check=" + check + "," +
                "currentTime:"+ timeNow + "," +
                "executionTime:" + executionTime + "}";
    }
}
