package com.example.iem.test.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by iem on 31/01/2018.
 */

public class StartServiceRandomPokemon extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, RandomPokemon.class);
        startWakefulService(context,startServiceIntent);
    }
}
