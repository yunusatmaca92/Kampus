package com.gtu.yunus.kampus.Models;

import java.util.ArrayList;

public class SelectedCoursePage {
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<String> topPanelInformations;
    private ArrayList<String> years;
    private ArrayList<String> terms;

    public SelectedCoursePage(ArrayList<Course> courses, ArrayList<String> topPanelInformations,
                              ArrayList<String> years, ArrayList<String> terms) {
        this.courses = courses;
        this.topPanelInformations = topPanelInformations;
        this.years = years;
        this.terms = terms;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getTopPanelInformations() {
        return topPanelInformations;
    }

    public void setTopPanelInformations(ArrayList<String> topPanelInformations) {
        this.topPanelInformations = topPanelInformations;
    }

    public ArrayList<String> getYears() {
        return years;
    }

    public void setYears(ArrayList<String> years) {
        this.years = years;
    }

    public ArrayList<String> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<String> terms) {
        this.terms = terms;
    }
}
