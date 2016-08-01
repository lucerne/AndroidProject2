package com.example.lucerne.nytimesapplication.models;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lucerne.nytimesapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucerne on 7/30/16.
 */
public class FilterDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;
    Button btnSave;
    static Filter filter;

    EditText tvBeginDate;
    Spinner spinner;

    final Calendar c = Calendar.getInstance();
    Map<String, Boolean> checkboxes = new HashMap<String, Boolean>();
    String sortOrder = "newest";
    ArrayList<String> newsValues = new ArrayList<String>();

    // 1. Defines the listener interface with a method passing back data result.
    public interface FilterDialogListener {
        void onFinishEditDialog(Filter filter);
    }

    public FilterDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterDialogFragment newInstance(Filter f) {
        FilterDialogFragment frag = new FilterDialogFragment();
        Bundle args = new Bundle();

        filter = f;
        args.putString("title", "Filter");
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);

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
                        h = (CheckBox) view.findViewById(R.id.checkbox_arts);
                        if (checked) {
                            checkboxes.put(h.getText().toString(), Boolean.TRUE);
                        } else {
                            checkboxes.put(h.getText().toString(), Boolean.FALSE);
                        }
                        break;
                    case R.id.checkbox_fashion:
                        h = (CheckBox) view.findViewById(R.id.checkbox_fashion);
                        if (checked) {
                            checkboxes.put(h.getText().toString(), Boolean.TRUE);
                        } else {
                            checkboxes.put(h.getText().toString(), Boolean.FALSE);
                        }
                        break;
                    case R.id.checkbox_sport:
                        h = (CheckBox) view.findViewById(R.id.checkbox_sport);
                        if (checked) {
                            checkboxes.put(h.getText().toString(), Boolean.TRUE);
                        } else {
                            checkboxes.put(h.getText().toString(), Boolean.FALSE);
                        }
                        break;
                }
            }
        };

        return view;
//        return inflater.inflate(R.layout.fragment_edit_name, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // 2. Setup a callback when the "Done" button is pressed on keyboard
        btnSave = (Button) getView().findViewById(R.id.btnSave);
//        btnSave.setOnEditorActionListener(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialogListener listener = (FilterDialogListener) getActivity();

                listener.onFinishEditDialog(filter);
                // Close the dialog and return back to the parent activity
                dismiss();
            }
        });

    }

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            FilterDialogListener listener = (FilterDialogListener) getActivity();
            listener.onFinishEditDialog(filter);
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }
}