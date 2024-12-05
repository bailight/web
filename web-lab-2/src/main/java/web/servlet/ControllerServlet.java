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

@WebServlet("/Ctrl")
public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Result> results = (List<Result>) session.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
        }
        request.setAttribute("results", results);

        String xText = request.getParameter("x");
        String yText = request.getParameter("y");
        String rText = request.getParameter("r");

        if(xText != null || yText != null || rText != null) {
            request.getRequestDispatcher("/Area").forward(request, response);
        }else{
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}
