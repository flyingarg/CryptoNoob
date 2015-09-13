package com.rajumoh.cryptnoob;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.rajumoh.cryptnoob.databases.DatabaseUtils;
import com.rajumoh.cryptnoob.grooid.GrooidShell;

/**
 * Created by rajumoh on 9/4/15.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("rajumoh", "Got a SMS Message");
        SmsMessage msgs[] = getMessagesFromIntent(intent);
        String message_contact = "";
        String message_content = "";
        if (msgs != null) {
            for (SmsMessage msg : msgs) {
                message_contact = msg.getOriginatingAddress();
                message_content += msg.getMessageBody();
            }
        }
        Log.i("rajumoh","Message received : '" + message_content + "'");
        if(message_content.startsWith("$$")){
            String message = message_content.substring(2);
            Log.i("rajumoh", "Message : '" + message +"' selected for decryption");
            String decryptMethod = "decryptTest(testString);\n";
            GrooidShell shell = new GrooidShell(context.getDir("dynclasses", 0), this.getClass().getClassLoader());
            String algo = DatabaseUtils.getAlgoFromDb(null, context);
            String decryptedText = shell.evaluate(algo +"\ntestString = \""+ message +"\"\n"+decryptMethod).getResult();
            Log.i("rajumoh", "Decrypted message : '" + decryptedText + "'. Saving to Database");

            DatabaseUtils.saveMessage(context, message_contact, decryptedText);

            Log.i("rajumoh", "Creating and sending notification");
            Intent toIntent = new Intent(context, ActivityFour.class);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("New Encrypted Message");
            mBuilder.setContentText(decryptedText);
            mBuilder.setSmallIcon(R.drawable.ic_notification);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(ActivityFour.class);
            stackBuilder.addNextIntent(toIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,mBuilder.build());
        }
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
