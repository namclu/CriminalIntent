package com.namclu.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        // FragmentManager handles: a list of fragments and a back stack of fragment instructions
        FragmentManager fm = getSupportFragmentManager();

        // When you need to retrieve CrimeFragment from FragmentManger
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CrimeFragment();
            // Create and commit a fragment transaction
            // Fragment transactions are used to add, remove, attach, detach, or replace
            // fragments in the fragment list.
            fm.beginTransaction()
                    // FragmentTransaction add(@IdRes int containerViewId, Fragment fragment);
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
