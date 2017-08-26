package com.duongstudio.recerver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duongstudio.config.Config;
import com.duongstudio.sqlite.MySQL;
import com.duongstudio.task.GetJsonFromURL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by d on 8/26/2017.
 */

public class MyServiceGetData extends Service {

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("vnews", "onStartCommand MyServiceGetData");
        new GetJsonFromURL(new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final String jsonCallback = (String) msg.obj;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(jsonCallback);
                            MySQL mySQL = new MySQL(MyServiceGetData.this);
                            mySQL.deletaAllCates();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                mySQL.insertCates(jsonObject.toString());
                            }
                        } catch (Exception e) {

                        }
                    }
                }.start();
            }
        }).execute(Config.URL_CATEGORYS);
        new GetJsonFromURL(new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final String jsonCallback = (String) msg.obj;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(jsonCallback);
                            MySQL mySQL = new MySQL(MyServiceGetData.this);
                            mySQL.deletaAllVideos();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                mySQL.insertVideos(jsonObject.toString());
                            }
                            stopSelf();
                        } catch (Exception e) {

                        }
                    }
                }.start();
            }
        }).execute(Config.URL_VIDEOS);
        return START_NOT_STICKY;
    }
}
