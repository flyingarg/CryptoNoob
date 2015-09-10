package com.rajumoh.cryptnoob.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by rajumoh on 9/10/15.
 */
public class DatabaseUtils {

    public static synchronized boolean isEntryAvailable(Context context){
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(
                Cursor cursor = db.rawQuery("select * from " + SqlStore.AlgoStore.TABLE_NAME, new String[0])
        ){
            if(cursor.getCount() == 0) {
                Log.i("rajumoh", "No entry in database");
                return false;
            }else {
                Log.i("rajumoh","Entry present in database");
                return true;
            }
        }catch (SQLException e){
            Log.e("rajumoh", e.getMessage());
            return false;
        }
    }

    public static synchronized String getAlgoFromDb(String userName, Context context){
        String returnString = "";
        if(userName==null)
            userName="all";
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(
                Cursor cursor = db.query(SqlStore.AlgoStore.TABLE_NAME, null, SqlStore.AlgoStore.CONTACT_NAME + "='" + userName + "'", null, null ,null, null, null);) {
            if (cursor != null) {
                if (cursor.getCount() != 1) {
                    Log.e("rajumoh", "cursor returned n rows : " + cursor.getCount());
                    returnString = "";
                } else {
                    Log.i("rajumoh", "cursor returned on row as expected");
                    cursor.moveToFirst();
                    returnString = cursor.getString(2);
                }
            }
        }catch(SQLException e){
            Log.e("rajumoh",e.getMessage());
        }
        return returnString;
    }

    public static synchronized boolean insertAlgoToDb(String userName, String data, Context context){
        if(userName==null)
            userName="all";
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlStore.AlgoStore.CONTACT_NAME,userName);
        contentValues.put(SqlStore.AlgoStore.ALGO, data);
        try {
            db.insertOrThrow(SqlStore.AlgoStore.TABLE_NAME, null, contentValues);
            Log.i("rajumoh","Successfull entry into the database");
        }catch(SQLException e){
            Log.e("rajumoh", "Could not enter algo to database " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized boolean updateAlgoToDb(String userName, String data, Context context){
        if(userName==null)
            userName="all";
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlStore.AlgoStore.CONTACT_NAME,userName);
        contentValues.put(SqlStore.AlgoStore.ALGO, data);
        int updatedEntries = db.updateWithOnConflict(SqlStore.AlgoStore.TABLE_NAME, contentValues, null, null, SQLiteDatabase.CONFLICT_REPLACE);
        try {
            if(updatedEntries == 1) {
                Log.i("rajumoh", "Successfull entry into the database");
                return true;
            }else{
                Log.e("rajumoh", "Failed entry into the database, updated entries : " + updatedEntries);
                return false;
            }
        }catch(SQLException e){
            Log.e("rajumoh", "Could not enter algo to database " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
