package com.example.android.workinghours;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MariaKorneeva on 04.02.2018.
 */

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeekViewHolder>{

    private static final String TAG = WeeksAdapter.class.getSimpleName();
    int currentWeek;
    int weekNumber;
    final private ListItemClickListener listener;

    public WeeksAdapter(int currentWeek, ListItemClickListener listener) {
        this.currentWeek = currentWeek;
        if (currentWeek <=52) this.weekNumber = currentWeek;
        else weekNumber =1;
        this.listener = listener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }


    @Override
    public WeekViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.weekdays_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        WeekViewHolder weekholder = new WeekViewHolder(view);
        weekholder.kwNumber.setText(String.valueOf(weekNumber));
        if (weekNumber > 1) weekNumber--;
        else weekNumber = 52;
        return setWeekDays(weekholder, context);

    }

    public boolean dateInFuture(String date){
        Date todayDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date weekDate;
        try {weekDate = df.parse(date);} catch(ParseException e){Log.e(TAG, "Error parsing the date: " + e.toString()); weekDate=null;}
        Log.i(TAG, "--------------- dayofWeek: " + date + " weekDate: " + weekDate + "today: " + todayDate);
        return todayDate.before(weekDate);
    }

    public void markAsFuture(Context context, WeekViewHolder weekholder, String weekDay){
        switch(weekDay){
            case "mon": {
                weekholder.monBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            case "tue":{
                weekholder.tueBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            case "wen":{
                weekholder.wenBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            case "thu":{
                weekholder.thuBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            case "fri":{
                weekholder.friBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            case "sat":{
                weekholder.satBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            case "sun":{
                weekholder.sunBack.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                break;
            }
            default: break;
        }
    }

    public WeekViewHolder setWeekDays(WeekViewHolder weekholder, Context context){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "mon");
        weekholder.monday.setText(String.valueOf(dayOfWeek));
        cal.add(Calendar.DATE, 1);
        dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "tue");
        weekholder.tuesday.setText(String.valueOf(dayOfWeek));
        cal.add(Calendar.DATE, 1);
        dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "wen");
        weekholder.wensday.setText(String.valueOf(dayOfWeek));
        cal.add(Calendar.DATE, 1);
        dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "thu");
        weekholder.thursday.setText(String.valueOf(dayOfWeek));
        cal.add(Calendar.DATE, 1);
        dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "fri");
        weekholder.friday.setText(String.valueOf(dayOfWeek));
        cal.add(Calendar.DATE, 1);
        dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "sat");
        weekholder.saturday.setText(String.valueOf(dayOfWeek));
        cal.add(Calendar.DATE, 1);
        dayOfWeek = df.format(cal.getTime());
        if (dateInFuture(dayOfWeek)) markAsFuture(context, weekholder, "sun");
        weekholder.sunday.setText(String.valueOf(dayOfWeek));
        return weekholder;
    }

    @Override
    public void onBindViewHolder(WeekViewHolder holder, int position) {
        return;
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class WeekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView monday, tuesday, wensday, thursday, friday, saturday, sunday;
        TextView mon_work, tue_work, wen_work, thu_work, fri_work, sat_work, sun_work;
        ImageView monBack, tueBack, wenBack, thuBack, friBack, satBack, sunBack;
        TextView kwNumber;

        public WeekViewHolder(View itemView) {
            super(itemView);
            kwNumber = (TextView) itemView.findViewById(R.id.kw_number);
            monday = (TextView) itemView.findViewById(R.id.mo_day_data);
            tuesday = (TextView) itemView.findViewById(R.id.tue_day_data);
            wensday = (TextView) itemView.findViewById(R.id.wen_day_data);
            thursday = (TextView) itemView.findViewById(R.id.thu_day_data);
            friday = (TextView) itemView.findViewById(R.id.fri_day_data);
            saturday = (TextView) itemView.findViewById(R.id.sat_day_data);
            sunday = (TextView) itemView.findViewById(R.id.sun_day_data);
            mon_work = (TextView) itemView.findViewById(R.id.mo_working_hours);
            tue_work = (TextView) itemView.findViewById(R.id.tue_working_hours);
            wen_work = (TextView) itemView.findViewById(R.id.wen_working_hours);
            thu_work = (TextView) itemView.findViewById(R.id.thu_working_hours);
            fri_work = (TextView) itemView.findViewById(R.id.fri_working_hours);
            sat_work = (TextView) itemView.findViewById(R.id.sat_working_hours);
            sun_work = (TextView) itemView.findViewById(R.id.sun_working_hours);
            monBack = (ImageView) itemView.findViewById(R.id.mo_day_background);
            tueBack = (ImageView) itemView.findViewById(R.id.tue_day_background);
            wenBack = (ImageView) itemView.findViewById(R.id.wen_day_background);
            thuBack = (ImageView) itemView.findViewById(R.id.thu_day_background);
            friBack = (ImageView) itemView.findViewById(R.id.fri_day_background);
            satBack = (ImageView) itemView.findViewById(R.id.sat_day_background);
            sunBack = (ImageView) itemView.findViewById(R.id.sun_day_background);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            listener.onListItemClick(clickedPosition);
        }
    }

}