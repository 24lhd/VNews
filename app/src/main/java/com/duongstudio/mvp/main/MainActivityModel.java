package com.duongstudio.mvp.main;

import android.os.Handler;

/**
 * Created by D on 8/11/2017.
 */

public interface MainActivityModel {

    public void getVideos(Handler callBack, MainActivity mainActivity);

    public void getCategorys(Handler callBack, MainActivity mainActivity);
}
