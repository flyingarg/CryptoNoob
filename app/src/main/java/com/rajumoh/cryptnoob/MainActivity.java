package com.rajumoh.cryptnoob;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        navigationDrawerFragment = new NavigationDrawerFragment();
        Bundle argsNDF = new Bundle();
        argsNDF.putInt("position", 0);
        navigationDrawerFragment.setArguments(getIntent().getExtras());
        navigationDrawerFragment.setArguments(argsNDF);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, navigationDrawerFragment).addToBackStack(null).commit();
    }

    public void onNavigationDrawerItemSelected(int position) {
        Log.i("rajumoh", "MainActivity position : " + position);
        super.onNavigationDrawerItemSelected(position);
    }

    @Override
    public View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO : Need Groovy code up again.
        return (View)inflater.inflate(R.layout.fragment_1, container, false);
    }
}
