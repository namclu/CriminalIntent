package com.namclu.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Calendar;

/**
 * Created by namlu on 12-Nov-16.
 *
 * 12.Challenge Add a TimePickerFragment that allows the user to select time that crime occurred
 *      using a TimePicker widget. Add another button to CrimeFragment that will display the
 *      TimePicker widget.
 */

public class TimePickerFragment extends DialogFragment {
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.time_picker_title)
                .setView(R.layout.dialog_time)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}
