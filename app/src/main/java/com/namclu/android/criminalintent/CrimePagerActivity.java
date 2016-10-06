package com.namclu.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by namlu on 04-Oct-16.
 *
 * CrimePagerActivity.java replaces Crime Activity.java to allow users to navigate to a different
 * CrimeFragment by swiping right/left.
 */

public class CrimePagerActivity extends FragmentActivity {

    // Unique key to retrieve crime ID
    private static final String EXTRA_CRIME_ID = "com.namclu.android.criminalintent.crime_id";

    /*
     * @param mViewPager an instance of ViewPager
     * @param mCrimes a List of crimes
     */
    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    //
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        // new Intent(Context packageContext, Class<?> cls)
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);

        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // Obtain the crime ID
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();

        // This activity's instance of FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // FragmentStatePagerAdapter manages the conversation with ViewPager
        // fragmentManager is needed to add FragmentStatePagerAdapter and its methods to activity
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            // getItem() returns a Crime instance for the given position in dataset
            // Then uses Crime's ID to create and return properly configured CrimeFragment
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
