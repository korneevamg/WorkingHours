package com.example.android.workinghours;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.workinghours.Persistence.WHoursContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MariaKorneeva on 07.02.2018.
 */

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.weekViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private static final String TAG = WorkAdapter.class.getSimpleName();
    private int dayNumber;
    private String[] weekDates;

    public WorkAdapter(Context mContext, String[] weekDates) {
        dayNumber = 0;
        this.weekDates = weekDates;
        this.mContext = mContext;
        Log.e(TAG, "-------------------- WorkAdapter Constructor");
    }

    @Override
    public weekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "-------------------- onCreateViewHolder");
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.day_layout, parent, false);

        return new weekViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(final weekViewHolder holder, int position) {
        Log.e(TAG, "------------------- onBindViewHolder" + dayNumber);
        boolean markAsFuture;
        String hours = "0";

        if (dayNumber < 7) {
            markAsFuture = dateInFuture(weekDates[dayNumber]);
            holder.dayDate.setText(weekDates[dayNumber]);
        }
        else {
            dayNumber = 0;
            markAsFuture = dateInFuture(weekDates[dayNumber]);
            holder.dayDate.setText(weekDates[dayNumber]);

        }

        if (markAsFuture) holder.dayBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        if (mCursor != null){
            for (int i = 0; i<mCursor.getCount(); i++){
                int yearIndex = mCursor.getColumnIndex(WHoursContract.WHoursEntry.COLUMN_YEAR);
                int monthIndex = mCursor.getColumnIndex(WHoursContract.WHoursEntry.COLUMN_MONTH);
                int dayIndex = mCursor.getColumnIndex(WHoursContract.WHoursEntry.COLUMN_DAY);
                int hoursIndex = mCursor.getColumnIndex(WHoursContract.WHoursEntry.COLUMN_HOURS);
                mCursor.moveToPosition(i); // get to the right location in the cursor

                String year = mCursor.getString(yearIndex);
                String month = mCursor.getString(monthIndex);
                String day = mCursor.getString(dayIndex);

                String date = day + "." + month + "." + year;
                Log.e(TAG, "!!!!!!!!!!!!!!!!!!  " + date + "  !!!!!!!!!!!!!!!!!!!!!!!! " + weekDates[dayNumber]);
                if (date.equals(weekDates[dayNumber])) {
                    hours = mCursor.getString(hoursIndex);
                }
            }
        }

        dayNumber++;
        holder.dayHours.setText(hours);
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent editWorkIntent = new Intent(mContext, EditWorkActivity.class);
            editWorkIntent.putExtra("date", holder.dayDate.getText());
            editWorkIntent.putExtra("hours", holder.dayHours.getText());
            mContext.startActivity(editWorkIntent);
            }
        });
//        int hoursIndex = workCursor.getColumnIndex(WHoursContract.WHoursEntry.COLUMN_HOURS);
//        int hours = workCursor.getInt(hoursIndex);
//        String hoursString = "" + hours;

    }

    @Override
    public int getItemCount() {
//        Log.e(TAG, "-------------------- getItemCount");
//        if (mCursor == null) {
//            return 0;
//        }
  //      return mCursor.getCount();
        return 7;
    }

    public boolean dateInFuture(String date){
        Date todayDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date weekDate;
        try {weekDate = df.parse(date);} catch(ParseException e){
            Log.e(TAG, "Error parsing the date: " + e.toString()); weekDate=null;}
        return todayDate.before(weekDate);
    }

    public Cursor swapCursor(Cursor c) {
        Log.e(TAG, "-------------------- swapCursor $$$$$$$$$$$$$$$$" + c.getCount());
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        this.notifyDataSetChanged();
        return temp;
    }


    // Inner class for creating ViewHolders
    class weekViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView dayDate, dayHours;
        ImageView dayBackground;
        Button editBtn;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public weekViewHolder(View itemView) {
            super(itemView);
            Log.e(TAG, "-------------------- weekViewHolder Constructor");
            dayDate = itemView.findViewById(R.id.day_data);
            dayHours = itemView.findViewById(R.id.working_hours);
            dayBackground = (ImageView) itemView.findViewById(R.id.day_background);
            dayBackground.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
            editBtn = (Button) itemView.findViewById(R.id.edit);
        }
    }
}
