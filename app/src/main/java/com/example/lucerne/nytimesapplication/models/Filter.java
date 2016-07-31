package com.example.lucerne.nytimesapplication.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by lucerne on 7/30/16.
 */
@Parcel
public class Filter {

    String month;
    String year;
    String day;
    String sortOrder;
    ArrayList<String> NewsValues;

    // empty constructor needed by the Parceler library
    public Filter() {
        NewsValues = new ArrayList<String>();
//        NewsValues.add("Arts");

    }

    public String getDay() {
        return day;
    }

    public String getMonth() { return month; }

    public String getYear() { return year; }

    public String getSortOrder() {
        return sortOrder;
    }

    public ArrayList<String> getNewsValues() {
        return NewsValues;
    }

}
