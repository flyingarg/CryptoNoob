package com.rajumoh.cryptnoob;

import android.provider.BaseColumns;

/**
 * Created by rajumoh on 9/9/15.
 */
public final class SqlStore {
    public SqlStore(){

    }

    public static abstract class AlgoStore implements BaseColumns{
        public static final String TABLE_NAME = "algostore";
        public static final String ALGO_ID = "algoid";
        public static final String CONTACT_NAME = "algo_for_contact";
        public static final String ALGO = "algo";
    }
}
