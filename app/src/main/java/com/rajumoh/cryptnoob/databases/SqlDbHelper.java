package com.rajumoh.cryptnoob.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajumoh on 9/9/15.
 */
public class SqlDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SqlStore.db";
    private static final String CREATE_ALGO_TABLE = "CREATE TABLE " +
            SqlStore.AlgoStore.TABLE_NAME + " ( " +
            SqlStore.AlgoStore.ALGO_ID + " INTEGER PRIMARY KEY, " +
            SqlStore.AlgoStore.CONTACT_NAME + " TEXT UNIQUE, "+
            SqlStore.AlgoStore.ALGO + " TEXT )";
    private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE " +
            SqlStore.MessageStore.TABLE_NAME + " ( " +
            SqlStore.MessageStore.MESSAGE_ID + " INTEGER PRIMARY KEY, " +
            SqlStore.MessageStore.MESSAGE_CONTACT + " TEXT , "+
            SqlStore.MessageStore.MESSAGE_CONTENT + " TEXT, "+
            SqlStore.MessageStore.MESSAGE_DATE_TIME + " DATETIME)";

    private static final String DEFAULT_ALGO =
            "public encryptTest(plainText){\n" +
                    " String response = \"\";\n" +
                    " for(int i=0; i<plainText.length(); i++){\n" +
                    "  tempChar = plainText.charAt(i);\n"+
                    "  if(tempChar<65 || tempChar>122){\n"+
                    "   response+=tempChar;\n"+
                    "   continue;\n" +
                    "}\n" +
                    "  tempChar = tempChar-65;" +
                    "  response += (char)(((((int)tempChar)+21)%58)+65);" +
                    " }\n" +
                    " return response;\n" +
                    "}\n" +
                    "\n" +
                    "public decryptTest(encString){\n" +
                    " String response = \"\";\n" +
                    " for(int i=0; i<encString.length(); i++){\n" +
                    "  tempChar = encString.charAt(i);\n" +
                    "  if(tempChar<65 || tempChar>122){\n" +
                    "   response+=tempChar;\n" +
                    "   continue;\n" +
                    "  }\n"+
                    "  tempChar = tempChar-65;\n" +
                    "  response += (char)(((((int)tempChar)+37)%58)+65);\n" +
                    " }\n" +
                    " return response;\n" +
                    "}\n";

    private static final String INSERT_DEFAULT_ALGO = "INSERT INTO " + SqlStore.AlgoStore.TABLE_NAME + " (" +
            SqlStore.AlgoStore.CONTACT_NAME+","+
            SqlStore.AlgoStore.ALGO+") values ('all', '" +
            DEFAULT_ALGO + "')";

    private static final String DROP_ALGO_TABLE = "DROP TABLE IF EXISTS " + SqlStore.AlgoStore.TABLE_NAME;
    private static final String DROP_MESSAGE_TABLE = "DROP TABLE IF EXISTS " + SqlStore.MessageStore.TABLE_NAME;


    public SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ALGO_TABLE);
        db.execSQL(CREATE_MESSAGE_TABLE);
        db.execSQL(INSERT_DEFAULT_ALGO);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ALGO_TABLE);
        db.execSQL(DROP_MESSAGE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
