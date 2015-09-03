package com.rajumoh.cryptnoob;

import android.os.Bundle;
import android.util.Log;

public class ActivityThree extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        navigationDrawerFragment = new NavigationDrawerFragment();
        Bundle argsNDF = new Bundle();
        argsNDF.putInt("position", 2);
        navigationDrawerFragment.setArguments(getIntent().getExtras());
        navigationDrawerFragment.setArguments(argsNDF);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, navigationDrawerFragment).addToBackStack(null).commit();
    }

    public void onNavigationDrawerItemSelected(int position) {
        Log.i("rajumoh", "ActivityThree position : " + position);
        super.onNavigationDrawerItemSelected(position);
    }

}
