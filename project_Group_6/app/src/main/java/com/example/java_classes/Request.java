package com.example.java_classes;

import com.example.project_group_6.MenuAdaptor;

public class Request {
    public MenuAdaptor meal;
    private Status status;

    public Request() {
    }

    public Request(MenuAdaptor meal, Status status) {
        this.meal = meal;
        this.status = status;
    }

    public MenuAdaptor getMeal() {
        return meal;
    }

    public void setMeal(MenuAdaptor meal) {
        this.meal = meal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
