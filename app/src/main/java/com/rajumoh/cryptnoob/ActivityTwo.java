package com.rajumoh.cryptnoob;

import android.os.Bundle;
import android.util.Log;

public class ActivityTwo extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        navigationDrawerFragment = new NavigationDrawerFragment();
        Bundle argsNDF = new Bundle();
        argsNDF.putInt("position", 1);
        navigationDrawerFragment.setArguments(getIntent().getExtras());
        navigationDrawerFragment.setArguments(argsNDF);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, navigationDrawerFragment).addToBackStack(null).commit();
    }

    public void onNavigationDrawerItemSelected(int position) {
        Log.i("rajumoh", "ActivityTwo position : " + position);
        super.onNavigationDrawerItemSelected(position);
    }

}
