package com.duongstudio.mvp.videoview;

import android.content.Intent;

/**
 * Created by D on 8/11/2017.
 */

interface VideoView {
    public void setConfigPlay(Intent intent);

    public void downloadVideo();

    public void addToPlayList();

    public void shareVideo();

    public void showNotiDownload();

}
