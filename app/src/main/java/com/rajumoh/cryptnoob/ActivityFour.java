package com.rajumoh.cryptnoob;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.rajumoh.cryptnoob.databases.DatabaseUtils;
import com.rajumoh.cryptnoob.databases.SqlStore;

/**
 * Created by rajumoh on 9/11/15.
 */
public class ActivityFour extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        navigationDrawerFragment = new NavigationDrawerFragment();
        Bundle argsNDF = new Bundle();
        argsNDF.putInt("position", 3);
        navigationDrawerFragment.setArguments(getIntent().getExtras());
        navigationDrawerFragment.setArguments(argsNDF);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, navigationDrawerFragment).addToBackStack(null).commit();
    }

    public void onNavigationDrawerItemSelected(int position) {
        Log.i("rajumoh", "ActivityFour position : " + position);
        super.onNavigationDrawerItemSelected(position);
    }

    //TODO : Might have conflict with when invoked by user select.. may be !!
    @Override
    public View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Cursor cursor = DatabaseUtils.getMessages(getApplicationContext());
        if(cursor==null || cursor.getCount()==0)
            return inflater.inflate(R.layout.fragment_4, container, false);
        CursorAdapter dataAdapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.message_field,
                cursor,
                new String[]{SqlStore.MessageStore.MESSAGE_CONTACT, SqlStore.MessageStore.MESSAGE_CONTENT},
                new int[]{R.id.message_contact, R.id.message_content},
                0);
        View rootView =  inflater.inflate(R.layout.fragment_4, container, false);
        ListView temp = (ListView)rootView.findViewById(R.id.list_messages);
        temp.setAdapter(dataAdapter);
        return rootView;
    }
}
