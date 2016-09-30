package com.namclu.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by namlu on 25-Sep-16.
 */

public class Crime {
    /*
     * @param mID the Crime ID
     * @param mTitle the Crime title
     * @param mDate the date Crime occurred
     * @param mSolved is the Crime solved or not
     */
    private UUID mID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;


    public Crime() {
        // Generate unique identifier
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
