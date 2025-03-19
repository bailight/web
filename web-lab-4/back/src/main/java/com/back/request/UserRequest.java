package com.back.request;

public class UserRequest {
    private String Username;
    private String password;

    public String getUsername() {
        return Username;
    }
    public String getPassword() {
        return password;
    }

    public void setUsername(String userName) {
        Username = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
