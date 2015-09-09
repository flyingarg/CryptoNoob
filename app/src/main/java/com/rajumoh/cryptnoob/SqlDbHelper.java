package com.rajumoh.cryptnoob;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajumoh on 9/9/15.
 */
public class SqlDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SqlStore.db";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            SqlStore.AlgoStore.TABLE_NAME + " ( " +
            SqlStore.AlgoStore.ALGO_ID + " INTEGER PRIMARY KEY, " +
            SqlStore.AlgoStore.CONTACT_NAME + " TEXT, "+
            SqlStore.AlgoStore.ALGO + " TEXT )";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + SqlStore.AlgoStore.TABLE_NAME;


    public SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
