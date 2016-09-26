package com.namclu.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by namlu on 25-Sep-16.
 */

public class CrimeFragment extends Fragment {

    // Member variable for an instance of Crime
    private Crime mCrime;

    private EditText mTitleField;

    // Fragment onCreate() must be public (vs. protected) because they will be called by whatever
    // activity is hosting the fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    // Views in fragment are not inflated inside onCreate(), but instead in this fragment
    // lifecycle method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        // After view is inflated, get a reference to EditText and add a listener
        mTitleField = (EditText) view.findViewById(R.id.crime_title);

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // This space left intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return  view;
    }
}
