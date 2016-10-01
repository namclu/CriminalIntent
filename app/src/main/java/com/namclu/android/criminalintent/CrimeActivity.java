package com.namclu.android.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    // Calls SingleFragmentActivity's createFragment()
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
