package com.rajumoh.cryptnoob.databases;

import android.provider.BaseColumns;

/**
 * Created by rajumoh on 9/9/15.
 */
public final class SqlStore {
    public SqlStore(){

    }

    public static abstract class AlgoStore implements BaseColumns{
        public static final String TABLE_NAME = "algostore";
        public static final String ALGO_ID = "_id";
        public static final String CONTACT_NAME = "algo_contact";
        public static final String ALGO = "algo";
    }

    public static abstract class MessageStore implements BaseColumns{
        public static final String TABLE_NAME = "messagestore";
        public static final String MESSAGE_ID = "_id";
        public static final String MESSAGE_CONTACT = "message_contact";
        public static final String MESSAGE_CONTENT = "message_content";
        public static final String MESSAGE_DATE_TIME = "message_datetime";
    }
}
