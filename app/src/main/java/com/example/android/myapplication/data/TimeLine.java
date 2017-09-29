package com.example.android.myapplication.data;

/**
 * Created by Avilash on 29-09-2017.
 */

public class TimeLine {

    private String description,day;

    public TimeLine(String description, String day) {
        this.description = description;
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
