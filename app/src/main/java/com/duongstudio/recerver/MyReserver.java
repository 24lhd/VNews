package com.duongstudio.recerver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Random;

import static com.duongstudio.recerver.MyServiceGetData.isRunning;


/**
 * Created by Faker on 9/5/2016.
 */
public class MyReserver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getAction();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetInfo != null) {
            Intent my = new Intent(context, MyServiceGetData.class);
            boolean b = activeNetInfo.isConnectedOrConnecting();
            if (b) {
                if (!isRunning(context) && new Random().nextInt(6) == 2) {
                    context.startService(my);
                }
            }
            Log.e("vnews", " activeNetInfo" + b);
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("vnews", " ACTION_SCREEN_OFF");
            Intent intent1 = new Intent(context, MyServiceGetData.class);
            context.startService(intent1);
        }
    }

    private void rate() {

    }
}
