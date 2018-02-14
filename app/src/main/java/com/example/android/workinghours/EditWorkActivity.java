package com.example.android.workinghours;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.workinghours.Persistence.WHoursContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MariaKorneeva on 05.02.2018.
 */

public class EditWorkActivity extends AppCompatActivity{
    EditText editDate, editHours, editComment;
    private static final String TAG = EditWorkActivity.class.getSimpleName();
    String year, month, week, day;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);
        editDate = (EditText) findViewById(R.id.edit_date);
        editHours = (EditText) findViewById(R.id.edit_hours);
        editComment = (EditText) findViewById(R.id.edit_comment);

        Intent intent = getIntent();
        String currentDateInfo = intent.getStringExtra("date");
        String currentHoursInfo = intent.getStringExtra("hours");
        editDate.setText(currentDateInfo);
        editHours.setText(currentHoursInfo);

    }

    public void onClickSave(View view) {
        String inputDate = ((EditText) findViewById(R.id.edit_date)).getText().toString();
        String inputHoursString = ((EditText) findViewById(R.id.edit_hours)).getText().toString();
        int inputHours = 0;
        String inputComment = ((EditText) findViewById(R.id.edit_comment)).getText().toString();
        if (inputDate.length() == 0 || inputHoursString.length() == 0) {
            return;
        } else
            try {
                inputHours = Integer.parseInt(inputHoursString);
            } catch (NumberFormatException  e) {
                Log.e(TAG, "Error parsing numeric input: " + e.getMessage());
                inputHours = 0;
            }
        DateFormat df = new SimpleDateFormat("dd.mm.yy", Locale.getDefault());
        DateFormat dfYear = new SimpleDateFormat("yyyy", Locale.getDefault());
        DateFormat dfMonth = new SimpleDateFormat("mm", Locale.getDefault());
        DateFormat dfDay = new SimpleDateFormat("dd", Locale.getDefault());
        DateFormat dfWeek = new SimpleDateFormat("ww", Locale.getDefault());

        Date dayDate;
        try {dayDate = df.parse(inputDate);} catch(ParseException e){
            Log.e(TAG, "Error parsing the date: " + e.toString()); dayDate=null;}
        if (dayDate == null) {
            return;
        } else {
            year = dfYear.format(dayDate);
            month = dfMonth.format(dayDate);
            Calendar cal = Calendar.getInstance(Locale.GERMAN);
            int weekint = cal.get(Calendar.WEEK_OF_YEAR);
            week = "" + weekint;
            day = dfDay.format(dayDate);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(WHoursContract.WHoursEntry.COLUMN_YEAR, year);
        contentValues.put(WHoursContract.WHoursEntry.COLUMN_MONTH, month);
        contentValues.put(WHoursContract.WHoursEntry.COLUMN_WEEK, week);
        contentValues.put(WHoursContract.WHoursEntry.COLUMN_DAY, day);
        contentValues.put(WHoursContract.WHoursEntry.COLUMN_HOURS, inputHours);
        contentValues.put(WHoursContract.WHoursEntry.COLUMN_COMMENT, inputComment);
        Log.e(TAG," year " + year + " month " + month + " week " + week + " day " + day + " inputHours " + inputHours);
        Uri uri = getContentResolver().insert(WHoursContract.WHoursEntry.CONTENT_URI, contentValues);
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
