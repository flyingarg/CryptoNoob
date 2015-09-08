package com.rajumoh.cryptnoob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //protected NavigationDrawerFragment mNavigationDrawerFragment;
    protected NavigationDrawerFragment navigationDrawerFragment;
    protected CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Intent intent;
        if (position == 0 && !(this instanceof MainActivity)) {
            Log.i("rajumoh", "Switching to MainActivity");
            intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } else if (position == 1 && !(this instanceof ActivityTwo)){
            Log.i("rajumoh", "Switching to ActivityTwo");
            intent = new Intent(this, ActivityTwo.class);
            this.startActivity(intent);
        } else if(position == 2 && !(this instanceof ActivityThree)){
            Log.i("rajumoh", "Switching to ActivityThree");
            intent = new Intent(this, ActivityThree.class);
            this.startActivity(intent);
        }else {
            Log.i("rajumoh", "Commit Fragment to position : " + position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1, this))
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static BaseActivity baseActivity;
        public static PlaceholderFragment newInstance(int sectionNumber, BaseActivity activity) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            baseActivity = activity;
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int section = this.getArguments().getInt(ARG_SECTION_NUMBER);
            if ( section == 1){
                return baseActivity.createRootView(inflater, container, savedInstanceState);//inflater.inflate(R.layout.fragment_1, container, false);
            }else if( section == 2){
                return baseActivity.createRootView(inflater, container, savedInstanceState);/*inflater.inflate(R.layout.fragment_2, container, false);*/
            }else {
                return baseActivity.createRootView(inflater, container, savedInstanceState);/*inflater.inflate(R.layout.fragment_3, container, false);*/
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BaseActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if (!mNavigationDrawerFragment.isDrawerOpen()) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public abstract View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
