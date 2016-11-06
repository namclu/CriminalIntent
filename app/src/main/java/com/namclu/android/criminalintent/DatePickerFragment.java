package com.namclu.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by namlu on 06-Nov-16.
 *
 * 12: DatePickerFragment is the fragment that appears when clicking on the date button
 *      of a CrimeFragment.
 */

public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 12: Inflate the view...and set view below in AlertDialog.Builder
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        // 12: Pass a Context into the AlertDialog.Builder constructor, which returns an instance of
        //      AlertDialog.Builder
        // public Builder setTitle(@StringRes int titleId)
        // public Builder setPositiveButton(CharSequence text, final OnClickListener listener)
        // create() returns the configured AlertDialog instance
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();

        // 12:

    }
}
