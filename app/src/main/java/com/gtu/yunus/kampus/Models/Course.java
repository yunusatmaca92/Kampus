package com.gtu.yunus.kampus.Models;

public class Course {
    private String name;
    private String informations;

    public Course(String name, String informations) {
        this.name = name;
        this.informations = informations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }
}
