package com.back.rest;

import com.back.ejb.ResultServiceEJB;

import com.back.ejb.UserServiceEJB;
import com.back.entity.Result;
import com.back.entity.User;
import com.back.request.ResultRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.back.util.CheckPoint.*;

@Path("/results")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResultResource {
    @EJB
    private ResultServiceEJB ResultServiceEJB;
    @EJB
    private UserServiceEJB userServiceEJB;

    @POST
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCheckResult(ResultRequest resultRequest) {
        String username = resultRequest.getUsername();
        Map<String, String> response = new HashMap<>();
        User user = userServiceEJB.check(username);
        if (user == null) {
            response.put("message", "User not found");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }

        double x = resultRequest.getX();
        double y = resultRequest.getY();
        double r = resultRequest.getR();
        if (!isValidX(x)){
            response.put("message","Invalid input, value x must between -4 and 4");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        if (!isValidY(y)){
            response.put("message","Invalid input, value y must between -3 and 3");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        if (!isValidR(r)){
            response.put("message","Invalid input, value r must between 1 and 5");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        Result result = new Result(x, y, r, inGraph(x,y,r), user);
        ResultServiceEJB.saveResult(result);
        response.put("message","Save result successfully");
        response.put("result", result.toString());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllResultsByUserName(ResultRequest resultRequest) {
        String username = resultRequest.getUsername();
        Map<String, Object> response = new HashMap<>();
        Gson gson = new Gson();
        if (username == null) {
            response.put("message", "User not logged in");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }else{
            List<Result> results = ResultServiceEJB.getAllResultsByUsername(username);
            JsonArray jsonArray = gson.toJsonTree(results).getAsJsonArray();
            response.put("message", "User " + username);
            response.put("results", jsonArray);
            String jsonResponse = gson.toJson(response);
            return Response.ok(jsonResponse).build();
        }
    }

    @POST
    @Path("/clear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllResultsByUserName(ResultRequest resultRequest) {
        String username = resultRequest.getUsername();
        Map<String, String> response = new HashMap<>();
        if (username == null) {
            response.put("message", "User not logged in");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }else{
            ResultServiceEJB.deleteAllResultsByUsername(username);
            response.put("message", "Delete result successfully");
            return Response.ok(response).build();
        }
    }
}
