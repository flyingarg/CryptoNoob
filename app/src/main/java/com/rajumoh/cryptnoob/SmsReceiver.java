package com.rajumoh.cryptnoob;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by rajumoh on 9/4/15.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("rajumoh", "Got a SMS Message");
        SmsMessage msgs[] = getMessagesFromIntent(intent);
        String address, str = "";
        int contactId = -1;
        if (msgs != null) {
            for (int i = 0; i < msgs.length; i++) {
                address = msgs[i].getOriginatingAddress();
                Log.i("rajumoh", msgs[i].getDisplayOriginatingAddress());
                Log.i("rajumoh", msgs[i].getMessageBody());
                //contactId = ContactsUtils.getContactId(context, address, "address");
                str += msgs[i].getMessageBody().toString();
                str += "\n";
            }
        }

/*        if(contactId != -1){
            Log.i("rajumoh", "Contact id : " + contactId);
        }*/
    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }

        return msgs;
    }
}
