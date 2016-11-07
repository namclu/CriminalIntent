package com.namclu.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by namlu on 06-Nov-16.
 *
 * 12: DatePickerFragment is the fragment that appears when clicking on the date button
 *      of a CrimeFragment.
 */

public class DatePickerFragment extends DialogFragment {
    // 12.5: To get Crime's date to DatePickerFragment, we need a newInstance(Date) method and
    //      to make the Date an argument on the fragment
    private static String ARG_DATE = "date";
    private DatePicker mDatePicker;

    // 12.5: Creating and setting fragment arguments is typically done in a newInstance() method that
    //      replaces the fragment constructor.
    // 12.5: DatePickerFragment.newInstance() will replace instances of new DatePickerFragment()
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        // public void putSerializable(String key, Serializable value)
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 12.2: Fragment manager of hosting activity calls this method as part of putting the
    //      DialogFragment on screen
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 12.7: Extracting date and initializing DatePicker
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 12.4: Inflate the view...and set view below in AlertDialog.Builder
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        // 12.7:
        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        // 12.2: Pass a Context into the AlertDialog.Builder constructor, which returns an instance of
        //      AlertDialog.Builder
        // public Builder setTitle(@StringRes int titleId)
        // public Builder setPositiveButton(CharSequence text, final OnClickListener listener)
        // create() returns the configured AlertDialog instance
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
