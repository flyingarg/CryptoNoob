package com.rajumoh.cryptnoob.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by rajumoh on 9/10/15.
 */
public class DatabaseUtils {

    public static synchronized boolean isEntryAvailable(String userName, Context context){
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(userName == null)
            userName = "all";
        try/*(
                Cursor cursor = db.rawQuery("select * from " + SqlStore.AlgoStore.TABLE_NAME, new String[0])
        )*/{
            Cursor cursor = db.rawQuery("select * from " + SqlStore.AlgoStore.TABLE_NAME + " where " + SqlStore.AlgoStore.CONTACT_NAME + " = '" + userName + "'", new String[0]);
            if(cursor.getCount() == 0) {
                Log.i("rajumoh", "No entry in database");
                db.close();
                return false;
            }else {
                Log.i("rajumoh","Entry present in database");
                db.close();
                return true;
            }
        }catch (SQLException e){
            Log.e("rajumoh", e.getMessage());
            db.close();
            return false;
        }
    }

    public static synchronized String getAlgoFromDb(String userName, Context context){
        String returnString = "";
        if(userName==null)
            userName="all";
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try/*(
                Cursor cursor = db.query(SqlStore.AlgoStore.TABLE_NAME, null, SqlStore.AlgoStore.CONTACT_NAME + "='" + userName + "'", null, null, null, null, null);)*/ {
            Cursor cursor = db.query(SqlStore.AlgoStore.TABLE_NAME, null, SqlStore.AlgoStore.CONTACT_NAME + "='" + userName + "'", null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() != 1) {
                    Log.e("rajumoh", "cursor returned n rows : " + cursor.getCount());
                    returnString = "";
                } else {
                    Log.i("rajumoh", "cursor returned one row as expected");
                    cursor.moveToFirst();
                    returnString = cursor.getString(2);
                }
            }
        }catch(SQLException e){
            db.close();
            Log.e("rajumoh", e.getMessage());
        }
        db.close();
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
            if (!isEntryAvailable(userName, context))
                db.insertOrThrow(SqlStore.AlgoStore.TABLE_NAME, null, contentValues);
            else
                updateAlgoToDb(userName, data, context);
            Log.i("rajumoh","Successfull entry into the database");
        }catch(SQLException e){
            Log.e("rajumoh", "Could not enter algo to database " + e.getMessage());
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
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
                db.close();
                return true;
            }else{
                Log.e("rajumoh", "Failed entry into the database, updated entries : " + updatedEntries);
                db.close();
                return false;
            }
        }catch(SQLException e){
            Log.e("rajumoh", "Could not enter algo to database " + e.getMessage());
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    public static synchronized Cursor getMessages(Context context){
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try/*(
            Cursor cursor = db.query(SqlStore.MessageStore.TABLE_NAME,
                    new String[]{SqlStore.MessageStore.MESSAGE_CONTACT, SqlStore.MessageStore.MESSAGE_CONTENT},//columns
                    null,//Where clause without the where
                    null,//Where arguments
                    null,//group by
                    null,//having
                    null,//order by
                    null);) */{//limit
            Cursor cursor = db.query(SqlStore.MessageStore.TABLE_NAME,
                    new String[]{SqlStore.MessageStore.MESSAGE_ID, SqlStore.MessageStore.MESSAGE_CONTACT, SqlStore.MessageStore.MESSAGE_CONTENT},//columns
                    null,//Where clause without the where
                    null,//Where arguments
                    null,//group by
                    null,//having
                    null,//order by
                    null);
            if(cursor.getCount()==0) {
                db.close();
                return null;
            }else {
                db.close();
                return cursor;
            }
        }catch(SQLException e){
            Log.e("rajumoh", e.getMessage());
            db.close();
            return null;
        }
    }

    public static synchronized boolean saveMessage(Context context, String messageContact, String messageContent){
        SqlDbHelper dbHelper = new SqlDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlStore.MessageStore.MESSAGE_CONTACT,messageContact);
        contentValues.put(SqlStore.MessageStore.MESSAGE_CONTENT, messageContent);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(Calendar.getInstance().getTime());
        contentValues.put(SqlStore.MessageStore.MESSAGE_DATE_TIME,currentTime);
        try {
            db.insertOrThrow(SqlStore.MessageStore.TABLE_NAME, null, contentValues);
            Log.i("rajumoh","Message "+messageContact+" with content " +messageContent + " stored in database.");
            db.close();
        }catch(SQLException e){
            Log.e("rajumoh", "Could not enter message to database " + e.getMessage());
            db.close();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
