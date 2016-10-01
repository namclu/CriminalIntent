package com.namclu.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by namlu on 01-Oct-16.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

    //
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // FragmentManager handles: a list of fragments and a back stack of fragment instructions
        FragmentManager fragmentManager = getSupportFragmentManager();

        // When you need to retrieve CrimeFragment from FragmentManger
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            // Create and commit a fragment transaction
            // Fragment transactions are used to add, remove, attach, detach, or replace
            // fragments in the fragment list.
            fragmentManager.beginTransaction()
                    // FragmentTransaction add(@IdRes int containerViewId, Fragment fragment);
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
