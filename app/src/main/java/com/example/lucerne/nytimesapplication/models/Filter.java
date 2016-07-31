package com.example.lucerne.nytimesapplication.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by lucerne on 7/30/16.
 */
@Parcel
public class Filter {

    int month;
    int year;
    int day;
    String sortOrder;
    ArrayList<String> newsValues;

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
