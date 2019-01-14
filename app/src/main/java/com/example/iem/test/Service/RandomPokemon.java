package com.example.iem.test.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.iem.test.Constants;
import com.example.iem.test.R;

/**
 * Created by iem on 31/01/2018.
 */

public class RandomPokemon extends IntentService {
    public RandomPokemon() {
        super("RandomPokemon");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


       NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID);
                                   notificationCompatBuilder.setSmallIcon(R.mipmap.ic_launcher);
                                   notificationCompatBuilder.setContentTitle("PokeCard");
                                   notificationCompatBuilder.setContentText("Tu as reçu un nouveau Pokemon dans ton Pokedex ! ");
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0,notificationCompatBuilder.build());


    }
}
