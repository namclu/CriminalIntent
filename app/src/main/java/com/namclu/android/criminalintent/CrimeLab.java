package com.namclu.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by namlu on 01-Oct-16.
 *
 * CrimeLab creates and stores a List of Crime objects
 */

public class CrimeLab {

    /*
     * @param sCrimeLab CrimeLab's static, singleton object
     * @param mCrimes a List of Crime objects
     */
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    // Accessor method for the singleton object
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    // CrimeLab's constructor
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        // Create a list of 100 Crime objects
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            // Every other crime (starting w/ 0) will be set to 'true'
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    // Returns a List of Crime objects
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    // Returns a specific Crime object
    public Crime getCrime(UUID id) {
        for (Crime crime: mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

}
