package com.rajumoh.cryptnoob;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public void onResume() {
        Log.i("rajumoh","onResume");
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    NdefRecord[] record = msgs[i].getRecords();
                    Log.i("rajumoh", "Records id : " + record[i].getId());
                    Log.i("rajumoh", "Record payload : " + record[i].getPayload().toString());
                }
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("rajumoh", "Intent invoked");
        Log.i("rajumoh",""+NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()));
        setIntent(intent);
    }

    @Override
    public View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_3, container, false);
        TextView temp = (TextView)rootView.findViewById(R.id.section_label);
        temp.setText("Fragment Three");
        return rootView;
    }

}
