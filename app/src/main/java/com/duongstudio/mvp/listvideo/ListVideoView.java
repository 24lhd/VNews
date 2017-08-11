package com.duongstudio.mvp.listvideo;

import com.duongstudio.obj.ItemVideo;

import java.util.ArrayList;

/**
 * Created by D on 8/11/2017.
 */

interface ListVideoView {

    public void initView();

    public ArrayList<ItemVideo> getListVideoFromIDCategory(String idCate);

    public void showLoadingList();

    public void hideLoadingList();

    public void showListVideo();

    public void setTitleBar();

    public void startVideoView(String jsonVideo);

    public void loadAdsFacebook();

    public void showAdsFacebook();

    public void loadAdsAdmod();

    public void showAdsAdmod();
}
