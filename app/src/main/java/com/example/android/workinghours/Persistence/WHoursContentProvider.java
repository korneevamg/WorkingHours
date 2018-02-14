package com.example.android.workinghours.Persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by MariaKorneeva on 05.02.2018.
 */

public class WHoursContentProvider extends ContentProvider{

    public static final int ALL_HOURS = 100;
    public static final int DAY_HOURS = 101;
    public static final int WEEK_HOURS = 102;
    public static final int MONTH_HOURS = 103;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHelper wHoursDBHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(WHoursContract.AUTHORITY, WHoursContract.PATH_WORKING_HOURS, ALL_HOURS);
        uriMatcher.addURI(WHoursContract.AUTHORITY, WHoursContract.PATH_WORKING_HOURS + "/#", DAY_HOURS);
        uriMatcher.addURI(WHoursContract.AUTHORITY, WHoursContract.PATH_WORKING_HOURS + "/month/#", MONTH_HOURS);
        uriMatcher.addURI(WHoursContract.AUTHORITY, WHoursContract.PATH_WORKING_HOURS + "/week/#", WEEK_HOURS);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        wHoursDBHelper = new DBHelper(context);
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = wHoursDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        switch (match) {
            case ALL_HOURS:
                long id = db.insert(WHoursContract.WHoursEntry.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(WHoursContract.WHoursEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = wHoursDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case ALL_HOURS:
                retCursor =  db.query(WHoursContract.WHoursEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case MONTH_HOURS:
                retCursor =  db.query(WHoursContract.WHoursEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case DAY_HOURS:
                retCursor =  db.query(WHoursContract.WHoursEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                retCursor = null;
                //throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
        //NOT RELEVANT
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
