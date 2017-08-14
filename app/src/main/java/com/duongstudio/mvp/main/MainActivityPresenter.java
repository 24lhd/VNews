package com.duongstudio.mvp.main;

import com.duongstudio.listener.OnGetDataSucssec;

/**
 * Created by D on 8/11/2017.
 */

public interface MainActivityPresenter {
    public void setOnGetDataSucsec(OnGetDataSucssec onGetDataSucsec);

    public void setListVideos();

    public void setListCategorys();

}
