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
    }

    // 13.10: Method to add a new Crime, replacing code in CrimeLab() which generated 100 Crime
    public void addCrime(Crime crime) {
        mCrimes.add(crime);
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
