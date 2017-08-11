package com.duongstudio.mvp.main;

import android.os.Handler;

import com.duongstudio.task.GetDataImpl;

/**
 * Created by D on 8/11/2017.
 */

public class MainActicityModelImpl implements MainActivityModel {
    GetDataImpl getData;

    @Override
    public void getVideos(Handler callBack) {
        getData = new GetDataImpl();
        getData.getVideos(callBack);
    }

    @Override
    public void getCategorys(Handler callBack) {
        getData = new GetDataImpl();
        getData.getCategorys(callBack);
    }
}
