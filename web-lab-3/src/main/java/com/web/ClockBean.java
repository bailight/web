package com.web;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@SessionScoped
public class ClockBean implements Serializable {
    private String currentDateTime;

    public ClockBean() {
        updateDateTime();
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentDateTime = now.format(formatter);
    }
}
