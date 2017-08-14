package com.duongstudio.videotintuc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.duongstudio.config.Config;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.task.GetJsonFromURL;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by D on 8/11/2017.
 */

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_layout);
        for (int i = 0; i < 10000; i++) {
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

//                    callback.sendMessage(message);
                    } catch (Exception e) {
//                    callback.sendEmptyMessage(0);
                    }
                }
            }).execute(Config.URL_VIDEOS);

        }

    }
}

