package com.BackPrimeflix.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDto {
    //members
    private String year;
    private String month;
    private String day;

    //constructor
    public DateDto() {
    }

    //getters setters
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


}
