package com.duongstudio.mvp.main;

import android.os.Handler;

import com.duongstudio.task.GetDataImpl;

/**
 * Created by D on 8/11/2017.
 */

public class MainActicityModelImpl implements MainActivityModel {
    GetDataImpl getData;

    @Override
    public void getVideos(Handler callBack, MainActivity mainActivity) {
        getData = new GetDataImpl();
        getData.getVideos(callBack,mainActivity);
    }

    @Override
    public void getCategorys(Handler callBack, MainActivity mainActivity) {
        getData = new GetDataImpl();
        getData.getCategorys(callBack,mainActivity);
    }
}
