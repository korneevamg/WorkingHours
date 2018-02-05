package com.example.android.workinghours;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements WeeksAdapter.ListItemClickListener{

    private WeeksAdapter wAdapter;
    private RecyclerView weekView;
    private int currentWeekNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weekView = (RecyclerView) findViewById(R.id.rv_kw);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        weekView.setLayoutManager(layoutManager);
        weekView.setHasFixedSize(false);
        currentWeekNumber = getCurrentKwNumber();
        wAdapter = new WeeksAdapter(currentWeekNumber, this);
        weekView.setAdapter(wAdapter);
    }

    public void editWorkingHours(View view){
        Context context = view.getContext();
        Toast.makeText(context, "Okeeeeey", Toast.LENGTH_SHORT).show();
    }

    public void backToCurrent(View view){
        currentWeekNumber = getCurrentKwNumber();
        wAdapter = new WeeksAdapter(currentWeekNumber, this);
        weekView.setAdapter(wAdapter);
    }

    public void goToForecast(View view){
        if (currentWeekNumber<52) currentWeekNumber++; else currentWeekNumber=1;
        wAdapter = new WeeksAdapter(currentWeekNumber, this);
        weekView.setAdapter(wAdapter);
    }

    public int getCurrentKwNumber(){
        Calendar cal = new GregorianCalendar(Locale.GERMAN);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Toast.makeText(this, "Item # " +  clickedItemIndex, Toast.LENGTH_SHORT).show();
    }
}
