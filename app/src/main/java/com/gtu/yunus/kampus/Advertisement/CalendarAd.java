package com.gtu.yunus.kampus.Advertisement;

public class CalendarAd {
    public static final String MONDAY = "Pzt";
    public static final String TUESDAY = "Sal";
    public static final String WEDNESDAY = "Çar";
    public static final String THURSDAY = "Per";
    public static final String FRIDAY = "Cum";
    public static final String SATURDAY = "Cmt";
    public static final String SUNDAY = "Pzr";

    public static final String JANUARY = "Oca";
    public static final String FEBRUARY = "Şub";
    public static final String MARCH = "Mar";
    public static final String APRIL = "Nis";
    public static final String MAY = "May";
    public static final String JUNE = "Haz";
    public static final String JULY = "Tem";
    public static final String AUGUST = "Ağu";
    public static final String SEPTEMBER = "Eyl";
    public static final String OCTOBER = "Eki";
    public static final String NOVEMBER = "Kas";
    public static final String DECEMBER = "Ara";

    private String day;
    private String dayWithNumber;
    private String month;
    private String year;

    public CalendarAd(){

    }
    public CalendarAd(String day, String dayWithNumber, String month, String year) {
        this.day = day;
        this.dayWithNumber = dayWithNumber;
        this.month = month;
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayWithNumber() {
        return dayWithNumber;
    }

    public void setDayWithNumber(String dayWithNumber) {
        this.dayWithNumber = dayWithNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
