package com.example.lucerne.nytimesapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.lucerne.nytimesapplication.models.Filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    EditText tvBeginDate;
    Spinner spinner;

    final Calendar c = Calendar.getInstance();
    Map<String, Boolean> checkboxes = new HashMap<String, Boolean>();
    String sortOrder = "newest";
    Filter filter = new Filter();
    ArrayList<String> newsValues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // old settings
        Filter old_filter = (Filter) getIntent().getSerializableExtra("filter");

        // initialize settings, listener runs before this
        setupCheckboxes();
        initCheckboxes(old_filter.getNewsValues());

        if (old_filter.getSortOrder() != null){
            sortOrder = old_filter.getSortOrder();
        }
        setupSpinner();

        if (old_filter.getCalendar() != null){
            c.set(Calendar.YEAR, old_filter.getCalendar().get(Calendar.YEAR));
            c.set(Calendar.MONTH, old_filter.getCalendar().get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, old_filter.getCalendar().get(Calendar.DAY_OF_MONTH));
        }
        displayDate(c);

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
        CheckBox checkSport = (CheckBox) findViewById(R.id.checkbox_sport);
        assert checkArts != null;
        assert checkFashion != null;
        checkArts.setOnCheckedChangeListener(checkListener);
        checkFashion.setOnCheckedChangeListener(checkListener);
        checkSport.setOnCheckedChangeListener(checkListener);
    }


    public void initCheckboxes(ArrayList<String> values){
        CheckBox checkArts = (CheckBox) findViewById(R.id.checkbox_arts);
        CheckBox checkFashion = (CheckBox) findViewById(R.id.checkbox_fashion);
        CheckBox checkSport = (CheckBox) findViewById(R.id.checkbox_sport);

        Log.d("DEBUG", (String) checkArts.getText());
        // better way to do this?
        for (int i=0; i < values.size(); ++i){
            if (values.get(i).equals(checkArts.getText().toString())){
                checkArts.setChecked(Boolean.TRUE);
            }
            if (values.get(i).equals(checkFashion.getText().toString())){
                checkFashion.setChecked(Boolean.TRUE);
            }
            if (values.get(i).equals(checkSport.getText().toString())){
                checkSport.setChecked(Boolean.TRUE);
            }
        }
    }

    public void setupSpinner(){
        List<String> list = new ArrayList<String>();
        list.add("Newest");
        list.add("Oldest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.sort_spinner, list);

        spinner = (Spinner) findViewById(R.id.sort_spinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        if (sortOrder.equals("newest")){
            spinner.setSelection(0);
        }
        else {
            spinner.setSelection(1);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                sortOrder = spinner.getSelectedItem().toString().toLowerCase();
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

        String date = new SimpleDateFormat("MMddyyyy").format(c.getTime());
        filter.setCalendar(c);

        displayDate(c);
    }

    public void displayDate(Calendar c){
        // convert date into displayable string format
        String date = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
        // display selected date
        tvBeginDate = (EditText) findViewById(R.id.startDate);
        tvBeginDate.setText(date);
    }

    // Give data to main
    public void onSave(View view){
        // closes the activity and returns to first screen
        filter.setSortOrder((String.valueOf(spinner.getSelectedItem())).toLowerCase());

        CheckBox checkArts = (CheckBox) findViewById(R.id.checkbox_arts);
        CheckBox checkFashion = (CheckBox) findViewById(R.id.checkbox_fashion);
        CheckBox checkSport = (CheckBox) findViewById(R.id.checkbox_sport);


        if (checkArts.isChecked()){
            newsValues.add(checkArts.getText().toString());
        }
        if (checkFashion.isChecked()){
            newsValues.add(checkFashion.getText().toString());
        }
        if (checkSport.isChecked()){
            newsValues.add(checkSport.getText().toString());
        }

        filter.setNewsValues(newsValues);

        Intent i = new Intent();
        i.putExtra("filter", filter);
        setResult(RESULT_OK, i);
        this.finish();
    }
}


