package com.example.android.workinghours.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MariaKorneeva on 05.02.2018.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "whours.db";
    private static final int VERSION = 2;
    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + WHoursContract.WHoursEntry.TABLE_NAME + " (" +
                WHoursContract.WHoursEntry._ID                + " INTEGER PRIMARY KEY, " +
                WHoursContract.WHoursEntry.COLUMN_DAY + " TEXT NOT NULL, " +
                WHoursContract.WHoursEntry.COLUMN_MONTH + " TEXT NOT NULL, " +
                WHoursContract.WHoursEntry.COLUMN_WEEK + " TEXT NOT NULL, " +
                WHoursContract.WHoursEntry.COLUMN_YEAR + " TEXT NOT NULL, " +
                WHoursContract.WHoursEntry.COLUMN_HOURS    + " INTEGER, " +
                WHoursContract.WHoursEntry.COLUMN_COMMENT    + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WHoursContract.WHoursEntry.TABLE_NAME);
        onCreate(db);
    }
}
