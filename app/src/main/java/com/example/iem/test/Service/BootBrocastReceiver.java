package com.example.iem.test.Service;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by iem on 02/03/2018.
 */

public class BootBrocastReceiver extends android.content.BroadcastReceiver {
    private long time;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BOOT", "Boot completed");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //Intent
        time = System.currentTimeMillis();
    }
}
