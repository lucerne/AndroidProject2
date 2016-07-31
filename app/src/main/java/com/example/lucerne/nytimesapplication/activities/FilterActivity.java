package com.example.lucerne.nytimesapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lucerne.nytimesapplication.R;
import com.example.lucerne.nytimesapplication.models.FilterDialogFragment;

/**
 * Created by lucerne on 7/30/16.
 */
// Note: `FragmentActivity` works here as well
public class FilterActivity extends AppCompatActivity implements FilterDialogFragment.FilterDialogListener {
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

    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }

}