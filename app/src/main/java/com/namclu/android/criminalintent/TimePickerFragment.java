package com.namclu.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by namlu on 12-Nov-16.
 *
 * 12.C Challenge Add a TimePickerFragment that allows the user to select time that crime occurred
 *      using a TimePicker widget. Add another button to CrimeFragment that will display the
 *      TimePicker widget.
 */

public class TimePickerFragment extends DialogFragment {

    // 12.C
    public static final String EXTRA_TIME = "com.namclu.android.criminalintent.time";

    private static final String ARG_TIME = "time";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);

        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setArguments(args);
        return timeFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_TIME);

        // Use the current time as the default values for the picker
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_time_picker);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour;
                        int minute;

                        int currentApiVersion = Build.VERSION.SDK_INT;
                        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1){
                            hour = mTimePicker.getHour();
                            minute = mTimePicker.getMinute();
                        } else {
                            hour = mTimePicker.getCurrentHour();
                            minute = mTimePicker.getCurrentMinute();
                        }
                        //Date time = new GregorianCalendar(hour, minute).getTime();
                        //Calendar time = new GregorianCalendar(0, 0, 0, hour, minute, 0);
                        Date time = new GregorianCalendar(year, month, day, hour, minute, 0).getTime();

                        sendTimeResult(Activity.RESULT_OK, time);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void sendTimeResult(int resultCode, Date time) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);
        // public void onActivityResult(int requestCode, int resultCode, Intent data)
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
