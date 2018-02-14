package com.example.android.workinghours.Persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by MariaKorneeva on 05.02.2018.
 */

public class WHoursContract {
    public static final String AUTHORITY = "com.example.android.workinghours";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_WORKING_HOURS = "whours";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class WHoursEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKING_HOURS).build();
        public static final String TABLE_NAME = "work";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_WEEK = "week";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_HOURS = "hours";
        public static final String COLUMN_COMMENT = "comment";
    }
}
