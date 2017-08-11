package com.duongstudio.task;

import android.os.Handler;
import android.os.Message;

import com.duongstudio.config.Config;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by D on 8/11/2017.
 */

public class GetDataImpl implements GetData {
    @Override
    public void getVideos(final Handler callback) {
        new GetJsonFromURL(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String jsonCallback = (String) msg.obj;
                ArrayList<ItemVideo> itemVideos = new ArrayList<>();
                Gson gson = new Gson();
                try {
                    Message message = new Message();

                    JSONArray jsonArray = new JSONArray(jsonCallback);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        itemVideos.add(gson.fromJson(jsonObject.toString(), ItemVideo.class));
                    }
                    message.obj = itemVideos;

                    callback.sendMessage(message);
                } catch (Exception e) {
                    callback.sendEmptyMessage(0);
                }
            }
        }).execute(Config.URL_VIDEOS);
    }

    @Override
    public void getCategorys(final Handler callback) {
        new GetJsonFromURL(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String jsonCallback = (String) msg.obj;
                ArrayList<ItemCategory> itemCategories = new ArrayList<>();
                Gson gson = new Gson();
                try {
                    Message message = new Message();

                    JSONArray jsonArray = new JSONArray(jsonCallback);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        itemCategories.add(gson.fromJson(jsonObject.toString(), ItemCategory.class));
                    }
                    message.obj = itemCategories;
                    callback.sendMessage(message);
                } catch (Exception e) {
                    callback.sendEmptyMessage(0);
                }
            }
        }).execute(Config.URL_CATEGORYS);

    }
}
