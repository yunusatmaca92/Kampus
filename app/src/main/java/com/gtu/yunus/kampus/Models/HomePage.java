package com.gtu.yunus.kampus.Models;

import java.util.ArrayList;

public class HomePage {
    ArrayList<String> homePageInformation = new ArrayList<>();

    public HomePage(ArrayList<String> homePageInformation) {
        this.homePageInformation = homePageInformation;
    }

    public ArrayList<String> getHomePageInformation() {
        return homePageInformation;
    }

    public void setHomePageInformation(ArrayList<String> homePageInformation) {
        this.homePageInformation = homePageInformation;
    }
}
