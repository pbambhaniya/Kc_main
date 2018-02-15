package com.multipz.kc.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import com.multipz.kc.Activity.AddMaterialActivity;

import java.util.Calendar;

/**
 * Created by Admin on 22-12-2017.
 */

public class DatePickerForTextSet {

    TextView textView;
    int mYear, mMonth, mDay;
    Context context;

    public DatePickerForTextSet(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
        openDialog();
    }

    private void openDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

//                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
