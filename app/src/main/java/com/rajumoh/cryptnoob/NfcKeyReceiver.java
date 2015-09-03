package com.rajumoh.cryptnoob;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class NfcKeyReceiver extends MainActivity implements NfcAdapter.CreateNdefMessageCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_nfc_key_receiver);
        Log.i("rajumoh", "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_nfc_key_receiver, menu);
        //return true;
        Log.i("rajumoh", "onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

        Log.i("rajumoh", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
