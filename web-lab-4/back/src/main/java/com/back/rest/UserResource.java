package com.back.rest;

import com.back.ejb.ResultServiceEJB;
import com.back.ejb.UserServiceEJB;
import com.back.entity.User;
import com.back.request.UserRequest;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @EJB
    private UserServiceEJB userServiceEJB;
    @EJB
    private ResultServiceEJB ResultServiceEJB;


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        Map<String, String> response = new HashMap<>();
        try {
            User user = userServiceEJB.login(username, password);
            if (user != null) {
                response.put("message", "Login successful");
                response.put("status", "200");
                response.put("username", username);
                return Response.ok().entity(response).build();
            } else {
                response.put("message", "Invalid username or password.");
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } catch (Exception e) {
            response.put("message","An error occurred during login: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response.ok().entity("Logout successful").build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        Map<String, String> response = new HashMap<>();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.put("message","Username and password cannot be empty.");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        try{
            User user = userServiceEJB.register(username, password);
            if (user != null) {
                response.put("message","Registration successful");
                return Response.status(Response.Status.CREATED).entity(response).build();
            }else{
                response.put("message","Username already exists.");
                return Response.status(Response.Status.CONFLICT).entity(response).build();
            }
        } catch (Exception e) {
            response.put("message","An error occurred during registration: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }

    }
}
