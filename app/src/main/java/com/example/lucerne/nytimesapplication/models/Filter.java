package com.example.lucerne.nytimesapplication.models;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lucerne on 7/30/16.
 */
@Parcel
public class Filter  implements Serializable {

    int month;
    int year;
    int day;

    Calendar calendar;
    String sortOrder;
    ArrayList<String> newsValues;
    private static final long serialVersionUID = 5177222050535318633L;

    public Calendar getCalendar() { return calendar; }

    public void setCalendar(Calendar calendar) { this.calendar = calendar; }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setNewsValues(ArrayList<String> newsValues) {
        this.newsValues = newsValues;
    }

    // empty constructor needed by the Parceler library
    public Filter() {
        this.newsValues = new ArrayList<String>();
        this.day = -1;
//        NewsValues.add("Arts");
    }

    public Filter(int month, int year, int day, String sortOrder, ArrayList<String> newsValues){
        this.month = month;
        this.year = year;
        this.day = day;
        this.sortOrder = sortOrder;
        this.newsValues = newsValues;
    }


    public int getDay() {
        return day;
    }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public String getSortOrder() {
        return sortOrder;
    }

    public ArrayList<String> getNewsValues() {
        return newsValues;
    }

}
