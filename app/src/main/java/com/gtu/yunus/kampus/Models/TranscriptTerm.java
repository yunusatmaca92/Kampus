package com.gtu.yunus.kampus.Models;

import java.util.ArrayList;

public class TranscriptTerm {
    private String head;
    private ArrayList<TranscriptCourse> courses;
    private String totalGTUKredi;
    private String totalAKTS;
    private String average1;
    private String average2;
    private String average3;
    private String average4;
    private String average5;

    public TranscriptTerm(String head, ArrayList<TranscriptCourse> courses, String totalGTUKredi,
                          String totalAKTS, String average1, String average2, String average3,
                          String average4, String average5) {
        this.head = head;
        this.courses = courses;
        this.totalGTUKredi = totalGTUKredi;
        this.totalAKTS = totalAKTS;
        this.average1 = average1;
        this.average2 = average2;
        this.average3 = average3;
        this.average4 = average4;
        this.average5 = average5;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public ArrayList<TranscriptCourse> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<TranscriptCourse> courses) {
        this.courses = courses;
    }

    public String getTotalGTUKredi() {
        return totalGTUKredi;
    }

    public void setTotalGTUKredi(String totalGTUKredi) {
        this.totalGTUKredi = totalGTUKredi;
    }

    public String getTotalAKTS() {
        return totalAKTS;
    }

    public void setTotalAKTS(String totalAKTS) {
        this.totalAKTS = totalAKTS;
    }

    public String getAverage1() {
        return average1;
    }

    public void setAverage1(String average1) {
        this.average1 = average1;
    }

    public String getAverage2() {
        return average2;
    }

    public void setAverage2(String average2) {
        this.average2 = average2;
    }

    public String getAverage3() {
        return average3;
    }

    public void setAverage3(String average3) {
        this.average3 = average3;
    }

    public String getAverage4() {
        return average4;
    }

    public void setAverage4(String average4) {
        this.average4 = average4;
    }

    public String getAverage5() {
        return average5;
    }

    public void setAverage5(String average5) {
        this.average5 = average5;
    }
}
