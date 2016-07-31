package com.example.lucerne.nytimesapplication.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lucerne.nytimesapplication.R;
import com.example.lucerne.nytimesapplication.models.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    final Calendar c = Calendar.getInstance();
    EditText tvBeginDate;
    Map<String, Boolean> checkboxes;
    String sortOrder;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupCheckboxes();

        // initialize settings
        checkboxes = new HashMap<String, Boolean>();
        sortOrder = "newest";
        displayDate(c);

        setupSpinner();

    }

    // Fires every time a checkbox is checked or unchecked
    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean checked) {
            // compoundButton is the checkbox
            // boolean is whether or not checkbox is checked
            // Check which checkbox was clicked
            CheckBox h;
            switch(view.getId()) {
                case R.id.checkbox_arts:
                    h = (CheckBox) findViewById(R.id.checkbox_arts);
                    if (checked) {
                        checkboxes.put(h.getText().toString(), Boolean.TRUE);
                    } else {
                        checkboxes.put(h.getText().toString(), Boolean.FALSE);
                    }
                    break;
                case R.id.checkbox_fashion:
                    h = (CheckBox) findViewById(R.id.checkbox_fashion);
                    if (checked) {
                        checkboxes.put(h.getText().toString(), Boolean.TRUE);
                    } else {
                        checkboxes.put(h.getText().toString(), Boolean.FALSE);
                    }
                    break;
                case R.id.checkbox_sport:
                    h = (CheckBox) findViewById(R.id.checkbox_sport);
                    if (checked) {
                        checkboxes.put(h.getText().toString(), Boolean.TRUE);
                    } else {
                        checkboxes.put(h.getText().toString(), Boolean.FALSE);
                    }
                    break;
            }
        }
    };

    public void setupCheckboxes() {
        CheckBox checkArts = (CheckBox) findViewById(R.id.checkbox_arts);
        CheckBox checkFashion = (CheckBox) findViewById(R.id.checkbox_fashion);
        assert checkArts != null;
        assert checkFashion != null;
        checkArts.setOnCheckedChangeListener(checkListener);
        checkFashion.setOnCheckedChangeListener(checkListener);
    }

    public void setupSpinner(){
        List<String> list = new ArrayList<String>();
        list.add("Newest");
        list.add("Oldest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.sort_spinner, list);
        spinner = (Spinner) findViewById(R.id.spinner_sort);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                sortOrder = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        displayDate(c);
//        // convert date into displayable string format
//        String date = Integer.toString(c.get(Calendar.MONTH)) + "/" +
//                Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" +
//                Integer.toString(c.get(Calendar.YEAR));
//
//        // display selected date
//        tvBeginDate = (EditText) findViewById(R.id.startDate);
//        tvBeginDate.setText(date);
    }

    public void displayDate(Calendar c){
        // convert date into displayable string format
        String date = Integer.toString(c.get(Calendar.MONTH)) + "/" +
                Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(c.get(Calendar.YEAR));

        // display selected date
        tvBeginDate = (EditText) findViewById(R.id.startDate);
        tvBeginDate.setText(date);
    }


}


