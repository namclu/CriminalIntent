package com.namclu.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by namlu on 04-Oct-16.
 *
 * CrimePagerActivity.java replaces CrimeActivity.java to allow users to navigate to a different
 * CrimeFragment by swiping right/left.
 */

public class CrimePagerActivity extends FragmentActivity {

    /*
     * @param mViewPager an instance of ViewPager
     * @param mCrimes a List of Crime
     */
    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();

        // This activity's instance of FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // FragmentStatePagerAdapter manages the conversation with ViewPager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            // getItem() returns a Crime instance for the given position in dataset
            // uses Crime's ID to create and return properly configured CrimeFragment
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            // getCount() returns the number of items in the array list
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

    }


}
