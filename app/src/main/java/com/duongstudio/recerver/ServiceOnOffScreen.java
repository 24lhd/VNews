package com.duongstudio.recerver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by d on 8/26/2017.
 */

public class ServiceOnOffScreen extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        onOffScreen = new OnOffScreen();
        registerReceiver(onOffScreen, intentFilter);
        Log.e("vnews", " onStartCommand");
        return START_STICKY;
    }

    OnOffScreen onOffScreen;

    @Override
    public void onDestroy() {
        unregisterReceiver(onOffScreen);
    }

    public static boolean isRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (MyServiceGetData.class.getName().equals(
                    service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    class OnOffScreen extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e("vnews", " ACTION_SCREEN_OFF");
                Log.e("vnews", " ACTION_SCREEN_OFF isRunning(context) " + isRunning(context));
                boolean isRun = new Random().nextInt(6) == 2;
                Log.e("vnews", " ACTION_SCREEN_OFF isRun" + isRun);
                if (!isRunning(context)) {
                    if (isRun) {
                        Intent intent1 = new Intent(context, MyServiceGetData.class);
                        context.startService(intent1);
                    }

                }


            }
        }
    }
}
