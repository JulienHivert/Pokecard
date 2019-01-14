package com.example.iem.test.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.iem.test.Service.RandomPokemon;

/**
 * Created by iem on 27/02/2018.
 */

public class BroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startService = new Intent(context, RandomPokemon.class);
    }
}
