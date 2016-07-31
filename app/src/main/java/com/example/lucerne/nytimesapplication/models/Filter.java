package com.example.lucerne.nytimesapplication.models;

import java.util.ArrayList;

/**
 * Created by lucerne on 7/30/16.
 */
public class Filter {

    String date;
    String sortOrder;
    ArrayList<String> NewsValues;

    public Filter(){
        NewsValues = new ArrayList<String>();
    };

    public String getDate() {
        return date;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public ArrayList<String> getNewsValues() {
        return NewsValues;
    }

}
