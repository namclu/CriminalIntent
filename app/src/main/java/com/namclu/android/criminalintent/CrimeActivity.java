package com.namclu.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/*
 * CrimeActivity.java is an Activity that hosts an instance of CrimeFragment.java. It is launched
 * when the user clicks on a Crime from the CrimeListActivity.java
 */

public class CrimeActivity extends SingleFragmentActivity {

    // Unique identifier for crimeId
    private static final String EXTRA_CRIME_ID = "com.namclu.android.criminalintent.crime_id";

    // Tells CrimeFragment which Crime to display by passing it the Crime ID
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        // .putExtra(String name, Serializable value);
        // a UUID is a Serializable object
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    // Using CrimeFragment's newInstance()
    // getIntent() returns the intent that started this Activity
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
