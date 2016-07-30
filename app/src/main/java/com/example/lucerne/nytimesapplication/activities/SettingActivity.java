package com.example.lucerne.nytimesapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.lucerne.nytimesapplication.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupCheckboxes();
    }

    // Fires every time a checkbox is checked or unchecked
    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean checked) {
            // compoundButton is the checkbox
            // boolean is whether or not checkbox is checked
            // Check which checkbox was clicked
            switch(view.getId()) {
                case R.id.checkbox_arts:
                    if (checked) {
                        // Put some meat on the sandwich
                        Log.d("DEBUG", "hi");
                    } else {
                        // Remove the meat
                        Log.d("DEBUG", "hi");
                    }
                    break;
                case R.id.checkbox_fashion:
                    if (checked) {
                        // Cheese me
                        Log.d("DEBUG", "hi");
                    } else {
                        // I'm lactose intolerant
                        Log.d("DEBUG", "hi");
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


}
