package com.example.lucerne.nytimesapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.lucerne.nytimesapplication.R;
import com.example.lucerne.nytimesapplication.models.Filter;
import com.example.lucerne.nytimesapplication.models.FilterDialogFragment;
/**
 * Created by lucerne on 7/30/16.
 */
// Note: `FragmentActivity` works here as well
public class FilterActivity extends AppCompatActivity implements
        FilterDialogFragment.FilterDialogListener {
    Filter filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        showEditDialog();

        filter = new Filter();
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        filter = (Filter) getIntent().getSerializableExtra("filter");
        FilterDialogFragment editNameDialogFragment = FilterDialogFragment.newInstance(filter);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishEditDialog(Filter filter) {
//        Toast.makeText(this, "Saved " + inputText, Toast.LENGTH_SHORT).show();

    }

}