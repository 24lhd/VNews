package com.duongstudio.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.duongstudio.obj.DuongHTTP;

/**
 * Created by D on 8/11/2017.
 */

public class GetJsonFromURL extends AsyncTask<String, Void, String> {
    private Handler handler;
    private DuongHTTP duongHTTP;

    public GetJsonFromURL(Handler handler) {
        this.handler = handler;
        duongHTTP = new DuongHTTP();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
//                JSONObject jsonObject = new JSONObject(duongHTTP.getHTTP(strings[0]));
            return duongHTTP.getHTTP(strings[0]);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String json) {
        if (json instanceof String) {
            Message message = new Message();
            message.obj = json;
            handler.sendMessage(message);
        } else {
            handler.sendEmptyMessage(2);
        }
    }
}