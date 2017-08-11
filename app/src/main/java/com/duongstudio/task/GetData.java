package com.duongstudio.task;

import android.os.Handler;

/**
 * Created by D on 8/11/2017.
 */

public interface GetData {
    public void getVideos(Handler callback);

    public void getCategorys(Handler callback);
}
