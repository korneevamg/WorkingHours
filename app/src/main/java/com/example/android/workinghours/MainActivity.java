package com.example.android.workinghours;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.workinghours.Persistence.WHoursContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView weekView;
    private int currentWeekNumber;
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView kwNumber;
    private static final int WORK_LOADER_ID = 0;
    Cursor workCursor;
    private WorkAdapter workAdapter;
    String[] weekDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weekView = findViewById(R.id.weekView);
        weekView.setLayoutManager(new LinearLayoutManager(this));
        currentWeekNumber = getCurrentKwNumber();
        weekDates = new String[7];
        setWeekDates(weekDates, currentWeekNumber);
        workAdapter = new WorkAdapter(this, weekDates);
        weekView.setAdapter(workAdapter);
        kwNumber = findViewById(R.id.kw_number);
        kwNumber.setText(String.valueOf(currentWeekNumber));
    }

    @Override
    protected void onResume(){
        super.onResume();
        getSupportLoaderManager().restartLoader(WORK_LOADER_ID, null, this);
    }

    public void setWeekDates(String[] weekToSet, int weekNumber){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dayOfWeek = df.format(cal.getTime());
        weekToSet[0] = dayOfWeek;
        for (int i=1; i<weekToSet.length; i++){
            cal.add(Calendar.DATE, 1);
            dayOfWeek = df.format(cal.getTime());
            weekToSet[i] = dayOfWeek;
        }
        for (int i=0; i<weekToSet.length; i++){
            Log.e(TAG, "------------------" + weekToSet[i]);
        }
    }

    public void backToCurrent(View view){
        currentWeekNumber = getCurrentKwNumber();
        kwNumber.setText(String.valueOf(currentWeekNumber));
        setWeekDates(weekDates, currentWeekNumber);
        workAdapter = new WorkAdapter(this, weekDates);
        weekView.setAdapter(workAdapter);
        workAdapter.swapCursor(loadWeekData(currentWeekNumber));
    }

    public void goToForecast(View view){
        if (currentWeekNumber<52) currentWeekNumber++; else currentWeekNumber=1;
        kwNumber.setText(String.valueOf(currentWeekNumber));
        setWeekDates(weekDates, currentWeekNumber);
        workAdapter = new WorkAdapter(this, weekDates);
        weekView.setAdapter(workAdapter);
        workAdapter.swapCursor(loadWeekData(currentWeekNumber));
    }

    public void goBack(View view){
        if (currentWeekNumber>=2) currentWeekNumber--; else currentWeekNumber=52;
        kwNumber.setText(String.valueOf(currentWeekNumber));
        setWeekDates(weekDates, currentWeekNumber);
        workAdapter = new WorkAdapter(this, weekDates);
        weekView.setAdapter(workAdapter);
        workAdapter.swapCursor(loadWeekData(currentWeekNumber));
    }

    public int getCurrentKwNumber(){
        Calendar cal = new GregorianCalendar(Locale.GERMAN);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    private Cursor loadWeekData(int weekNo){
        try {
            return getContentResolver().query(WHoursContract.WHoursEntry.CONTENT_URI,
                    null,
                    "week=?",
                    new String[]{"" + weekNo},
                    WHoursContract.WHoursEntry.COLUMN_DAY);

        } catch (Exception e) {
            Log.e(TAG, "Failed to asynchronously load data.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mWorkdata = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mWorkdata != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mWorkdata);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data
                Log.e(TAG, "&&&&&&&&&&&&&&&&&&&&&&&  " + currentWeekNumber);
                try {
                    return getContentResolver().query(WHoursContract.WHoursEntry.CONTENT_URI,
                            null,
                            "week=?",
                            new String[]{"" + currentWeekNumber},
                            WHoursContract.WHoursEntry.COLUMN_DAY);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mWorkdata = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e(TAG,"$$$$$$$$$$$$$$$$$$$$$ " + data.getCount());
        workAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        workCursor = null;
    }
}
