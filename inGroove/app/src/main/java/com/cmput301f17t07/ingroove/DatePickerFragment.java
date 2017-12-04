package com.cmput301f17t07.ingroove;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.cmput301f17t07.ingroove.avehabit.AddHabitActivity;

import java.util.Calendar;

/**
 * [Boundary Class]
 * This class is used to select a date from a calendar.  Useful for selecting when a habit should start or
 * When a habit should occur.
 *
 */

public class DatePickerFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (AddHabitActivity)getActivity(), year, month, day);
    }


}
