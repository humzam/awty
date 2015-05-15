package edu.washington.humzam.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by humzamangrio on 5/14/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String phoneNum = intent.getStringExtra("phoneNum");
        Toast.makeText(context, phoneNum +" : " + message, Toast.LENGTH_LONG).show();
    }
}
