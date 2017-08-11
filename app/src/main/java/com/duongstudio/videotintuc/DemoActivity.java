package com.duongstudio.videotintuc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.task.GetDataImpl;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by D on 8/11/2017.
 */

public class DemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_layout);
        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp("https://v.vnecdn.net//sohoa//video//web//mp4//2017//04//11//tren-tay-galaxy-s8-va-s8-1491887804.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "Duong");
        jcVideoPlayerStandard.thumbImageView.setImageDrawable(getResources().getDrawable(R.drawable.jc_add_volume));
        GetDataImpl getData = new GetDataImpl();
        getData.getVideos(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ArrayList<ItemVideo> itemVideos = new ArrayList<>();
                itemVideos = (ArrayList<ItemVideo>) msg.obj;
            }
        });
        getData.getCategorys(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ArrayList<ItemCategory> itemVideos = new ArrayList<>();
                itemVideos = (ArrayList<ItemCategory>) msg.obj;


            }
        });

    }
}

