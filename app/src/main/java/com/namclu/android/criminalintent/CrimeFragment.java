package com.namclu.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by namlu on 25-Sep-16.
 *
 * CrimeFragment.java is a Fragment that contains a single Crime along with all of Crime's details
 */

public class CrimeFragment extends Fragment {

    // String key for Crime ID
    private static final String ARG_CRIME_ID = "crime_id";
    // 12.3: String parameter uniquely identifies DialogFragment in FragmentManager's list
    private static final String DIALOG_DATE = "DialogDate";
    // 12.8: Request code used to identify target fragment
    private static final int REQUEST_DATE = 0;

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

    // Bundle contains key-value pairs for Fragments, similar to how intent extras behave for Activities
    // Activities can call CrimeFragment.newInstance(UUID) to create a CrimeFragment rather than
    // calling the constructor directly
    // The Activity can pass in any required parameters to newInstance() that fragment needs to
    // create its arguments
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        // .putSerializable(String key, Serializable value);
        args.putSerializable(ARG_CRIME_ID, crimeId);

        // Attaching arguments to a fragment must be done after fragment is create but before it is
        // added to an activity
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // CrimeFragment's onCreate() must be public (vs. protected) because they will be called by
    // whatever activity is hosting the fragment
    // Retrieve the EXTRA and fetch the crime
    // getArguments() plus type-specific "get" methods of Bundle used by Fragments to access its arguments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
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
        updateDate();
        // 12.3 Set DatePickerFragment button to active and show DialogFragment
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                // 12.6: DatePickerFragment.newInstance(mCrime.getDate()) replaces new DatePickerFragment()
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                // 12.8: setTargetFragment() accepts fragment that will be the target and request code
                //      is used to identify which fragment is reporting back
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                // 12.3: public void show(FragmentManager manager, String tag)
                // 12.3: Using FragmentManager, a transaction will be automatically be created
                //      and committed vs. using FragmentTransaction where you're responsible
                dialog.show(manager, DIALOG_DATE);
            }
        });

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

    // 12.11: Activity.onActivityResult() is the method ActivityManager calls on parent activity
    //      after child activity dies. With fragments, after activity receives the call, activity's
    //      FragmentManager then calls Fragment.onActivityResult() on appropriate fragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
