package com.namclu.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by namlu on 01-Oct-16.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
