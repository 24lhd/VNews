package com.duongstudio.task;

import android.content.Context;
import android.os.Handler;

/**
 * Created by D on 8/11/2017.
 */

public interface GetData {

    public void getVideos(Handler callback, Context mainActivity);

    public void getCategorys(Handler callback, Context mainActivity);
}
