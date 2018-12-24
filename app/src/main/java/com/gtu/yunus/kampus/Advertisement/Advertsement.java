package com.gtu.yunus.kampus.Advertisement;

public class Advertsement {
    private String id;
    private String title;
    private CalendarAd startDate;
    private CalendarAd endDate;
    private String place;
    private String details;

    public Advertsement(){

    }
    public Advertsement(String title, CalendarAd startDate, CalendarAd endDate, String place, String details,String id) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;

        this.place = place;
        this.details = details;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CalendarAd getStartDate() {
        return startDate;
    }

    public void setStartDate(CalendarAd startDate) {
        this.startDate = startDate;
    }

    public CalendarAd getEndDate() {
        return endDate;
    }

    public void setEndDate(CalendarAd endDate) {
        this.endDate = endDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
