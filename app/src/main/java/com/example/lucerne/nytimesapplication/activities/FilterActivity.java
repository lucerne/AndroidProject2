package com.example.lucerne.nytimesapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.lucerne.nytimesapplication.R;
import com.example.lucerne.nytimesapplication.models.FilterDialogFragment;

/**
 * Created by lucerne on 7/30/16.
 */
// Note: `FragmentActivity` works here as well
public class FilterActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_name);
        showEditDialog();
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment editNameDialogFragment = FilterDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }
}