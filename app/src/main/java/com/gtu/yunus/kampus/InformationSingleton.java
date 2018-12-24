package com.gtu.yunus.kampus;

import android.graphics.Bitmap;

import com.gtu.yunus.kampus.Models.HomePage;
import com.gtu.yunus.kampus.Models.SelectedCoursePage;
import com.gtu.yunus.kampus.Models.TranscriptPage;

import java.util.ArrayList;

public class InformationSingleton {
    private static InformationSingleton INSTANCE = null;

    private boolean rememberMe;

    private HomePage homePage;
    private SelectedCoursePage selectedCoursePage;
    private TranscriptPage transcriptPage;

    private ArrayList<String> announcements;
    private ArrayList<String> announcementsURLs;
    private ArrayList<String> announcementsImgURLs;
    private ArrayList<Bitmap> imageArr;
    private boolean isEmpty = true;

    private Bitmap imgGTUDining;
    private Bitmap imgKelebekCafe;

    private InformationSingleton() {}

    public static InformationSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InformationSingleton();
        }
        return(INSTANCE);
    }

    public boolean isRememberMe() {
        return rememberMe;
    }
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePageInformation) {
        this.homePage = homePageInformation;
    }

    public SelectedCoursePage getSelectedCoursePage() {
        return selectedCoursePage;
    }

    public void setSelectedCoursePage(SelectedCoursePage selectedCoursePage) {
        this.selectedCoursePage = selectedCoursePage;
    }

    public TranscriptPage getTranscriptPage() {
        return transcriptPage;
    }

    public void setTranscriptPage(TranscriptPage transcriptPage) {
        this.transcriptPage = transcriptPage;
    }

    public ArrayList<String> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(ArrayList<String> announcements) {
        this.announcements = announcements;
    }

    public ArrayList<String> getAnnouncementsURLs() {
        return announcementsURLs;
    }

    public void setAnnouncementsURLs(ArrayList<String> announcementsURLs) {
        this.announcementsURLs = announcementsURLs;
    }

    public ArrayList<String> getAnnouncementsImgURLs() {
        return announcementsImgURLs;
    }

    public void setAnnouncementsImgURLs(ArrayList<String> announcementsImgURLs) {
        this.announcementsImgURLs = announcementsImgURLs;
    }

    public ArrayList<Bitmap> getImageArr() {
        return imageArr;
    }

    public void setImageArr(ArrayList<Bitmap> imageArr) {
        this.imageArr = imageArr;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public Bitmap getImgGTUDining() {
        return imgGTUDining;
    }

    public void setImgGTUDining(Bitmap imgGTUDining) {
        this.imgGTUDining = imgGTUDining;
    }

    public Bitmap getImgKelebekCafe() {
        return imgKelebekCafe;
    }

    public void setImgKelebekCafe(Bitmap imgKelebekCafe) {
        this.imgKelebekCafe = imgKelebekCafe;
    }
}
