package com.example.iem.test.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.iem.test.Service.RandomPokemon;

/**
 * Created by iem on 27/02/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
   //public static final int REQUEST_CODE_2 = 123456;
   //public static final int REQUEST_CODE_PAY = 2000;




    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RandomPokemon.class);
        context.startService(i);

        Log.d("RECEIVER", "bien reçu, terminé !");

    }
}