package com.rajumoh.cryptnoob;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int section = this.getArguments().getInt(ARG_SECTION_NUMBER);
            if ( section == 1){
                //TODO : Need Groovy code up again.
                final View rootView = inflater.inflate(R.layout.fragment_1, container, false);
                return rootView;
            }else if( section == 2){
                final View rootView = inflater.inflate(R.layout.fragment_2, container, false);
                TextView temp = (TextView)rootView.findViewById(R.id.section_label);
                temp.setText("Fragment Two");
                Button button = (Button)rootView.findViewById(R.id.nfc_resp);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        byte[] data = "253".getBytes();
                        NdefRecord record = NdefRecord.createMime("text/plain", data);
                        //TODO : Would need to also have the code be able to send the algorithms.
                        NdefMessage message = new NdefMessage(record);
                        NfcAdapter.getDefaultAdapter(getActivity().getApplicationContext()).setNdefPushMessage(message, getActivity());
                        Log.i("rajumoh", "Active to send NdefMessage......");
                        Toast.makeText(getActivity().getApplicationContext(), "Well It now seems to be ready send message", Toast.LENGTH_LONG);
                    }
                });
                return rootView;
            }else {
                final View rootView = inflater.inflate(R.layout.fragment_3, container, false);
                TextView temp = (TextView)rootView.findViewById(R.id.section_label);
                temp.setText("Fragment Three");
                return rootView;
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
}
