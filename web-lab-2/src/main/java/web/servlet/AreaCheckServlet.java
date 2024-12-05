package web.servlet;

import web.data.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Area")
public class AreaCheckServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.nanoTime();

        try{
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));
            if (x < -3 || x > 5) {
                request.setAttribute("error", "x must be between -3 and 5.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } else if (y < -3 || y > 3) {
                request.setAttribute("error", "y must be between -3 and 3.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } else if (r < 0 || r > 5){
                request.setAttribute("error", "r must be between 0 and 5.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            HttpSession session = request.getSession();
            List<Result> results = (List<Result>) session.getAttribute("results");
            if (results == null) {
                results = new ArrayList<>();
            }
            Result result = new Result(x, y, r, inGraph(x, y, r), startTime);
            results.add(result);
            session.setAttribute("results", results);
            request.setAttribute("x", x);
            request.setAttribute("y", y);
            request.setAttribute("r", r);
            request.setAttribute("check", result.check);
            request.setAttribute("CurTime", result.timeNow);
            request.setAttribute("executionTime", result.executionTime);
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        }catch(NumberFormatException e){
            request.setAttribute("error", "Invalid number format for x, y, or r.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private static boolean inGraph(double x, double y, double r){
        if (x<=0 && y<=0){
            return y<r/2 && x<r; //第三象限 长方形
        } else if (x<=0 && y>=0) {
            return -r/2<x && y<r/2; //第二象限 三角形
        } else if (x >= 0 && y<=0) {
            return x * x + y * y < r * r; //第四象限 1/4圆形
        }else{
            return false;
        }
    }

}
