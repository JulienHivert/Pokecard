package com.example.iem.test.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.iem.test.Constants;
import com.example.iem.test.R;

/**
 * Created by iem on 31/01/2018.
 */

public class AlarmManagerPokemon extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RandomPokemon.class);
        context.startService(i);
       }
}
