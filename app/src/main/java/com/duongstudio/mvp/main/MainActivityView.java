package com.duongstudio.mvp.main;

import com.duongstudio.obj.ItemVideo;

/**
 * Created by D on 8/11/2017
 */

interface MainActivityView {

    public void showDialogLoadData();

    public void hideDialogLoadData();

    public void setCategory(String idCategory);

    public void startSetting();

    public void startRate();

    public void setBarTitle(String title);

    public void setMenuDrawView();

    public void startViewVideo(ItemVideo itemVideo);


}
