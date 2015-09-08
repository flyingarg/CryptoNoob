package com.rajumoh.cryptnoob;

import android.app.Activity;
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
        button.setOnClickListener(new MyOnclickListener(this));
        return rootView;
    }

    class MyOnclickListener implements View.OnClickListener{
        private Activity activity;
        public MyOnclickListener(Activity activity){
            this.activity = activity;
        }
        @Override
        public void onClick(View view) {
            byte[] data = "253".getBytes();
            NdefRecord record = NdefRecord.createExternal("com.rajumoh.cryptonoob","encryptedKey", data);
            //TODO : Would need to also have the code be able to send the algorithms.
            NdefMessage message = new NdefMessage(record);
            NfcAdapter.getDefaultAdapter(getApplicationContext()).setNdefPushMessage(message, activity);
            Log.i("rajumoh", "Active to send NdefMessage......");
            Toast.makeText(getApplicationContext(), "Well It now seems to be ready send message", Toast.LENGTH_LONG).show();
        }

    }
}
