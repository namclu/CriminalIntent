package com.namclu.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by namlu on 25-Sep-16.
 */

public class CrimeFragment extends Fragment {

    /*
     * @param mCrime a member variable for an instance of Crime
     * @param mTitleField a reference to the title EditText field
     * @param mDateButton a Button that also displays the date of the Crime
     * @param mSolvedCheckbox a Checkbox that indicates whether Crime is solved or not
     */
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    // Fragment onCreate() must be public (vs. protected) because they will be called by whatever
    // activity is hosting the fragment
    // Retrieve the EXTRA from CrimeActivity and fetch the crime
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getActivity().getIntent()
                .getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    // Views in fragment are not inflated inside onCreate(), but instead in this fragment
    // lifecycle method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        // After view is inflated, get a reference to EditText and add a listener
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());

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

        // Assign reference to mDateButton, set its text to Crime's mDate, and enabled to false
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        // Assign reference to mSolvedCheckBox, set a listener that will update Crime's mSolved field
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set Crime's solved property
                mCrime.setSolved(isChecked);
            }
        });

        return view;
    }
}
