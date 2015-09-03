package com.rajumoh.cryptnoob;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

/**
 * Created by rajumoh on 4.8.2015.
 */
public class Fragment2View {

    static NfcAdapter nfcAdapter = null;

    public Fragment2View() {

    }

    public static View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_2, container, false);
        TextView temp = (TextView)rootView.findViewById(R.id.section_label);
        temp.setText("Fragment Two");
        BigInteger bigInt = new BigInteger("34234234");//Some bull number
        NdefRecord ndefRecord = NdefRecord.createExternal("com.rajumoh.cryptonoob", "encryptedKey", bigInt.toByteArray());
        final NdefMessage nfedMessage = new NdefMessage(new NdefRecord[]{ndefRecord});
        nfcAdapter = NfcAdapter.getDefaultAdapter(rootView.getContext());
        Button button = (Button)rootView.findViewById(R.id.nfc_init);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nfcAdapter == null) {
                    Toast.makeText(rootView.getContext(), "NFC Not Available", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(rootView.getContext(), "NFC Available", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
}