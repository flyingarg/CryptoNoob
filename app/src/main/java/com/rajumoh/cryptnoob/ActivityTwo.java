package com.rajumoh.cryptnoob;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rajumoh.cryptnoob.databases.DatabaseUtils;

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

    @Override
    public View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View)inflater.inflate(R.layout.fragment_2, container, false);
        TextView temp = (TextView)rootView.findViewById(R.id.section_label);
        temp.setText("Fragment Two");
        Button button = (Button)rootView.findViewById(R.id.nfc_init);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] data = "253".getBytes();
                byte[] algoData = DatabaseUtils.getAlgoFromDb(null, getApplicationContext()).getBytes();
                NdefRecord keyRecord = NdefRecord.createExternal("com.rajumoh.cryptnoob", "externaltype", data);
                NdefRecord algoRecord = NdefRecord.createMime("text/plain", algoData);
                NdefMessage message = new NdefMessage(new NdefRecord[]{keyRecord, algoRecord});
                NfcAdapter.getDefaultAdapter(getApplicationContext()).setNdefPushMessage(message, ActivityTwo.this);
                Log.i("rajumoh", "Active to send NdefMessage......");
                Toast.makeText(getApplicationContext(), "Well It now seems to be ready send message", Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("rajumoh", "Intent invoked");
        Log.i("rajumoh", "" + NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()));
        setIntent(intent);
    }

}
