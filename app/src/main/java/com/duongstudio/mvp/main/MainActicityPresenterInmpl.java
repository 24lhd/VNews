package com.duongstudio.mvp.main;

import android.os.Handler;
import android.os.Message;

import com.duongstudio.listener.OnGetDataSucssec;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;

import java.util.ArrayList;

/**
 * Created by D on 8/11/2017.
 */

public class MainActicityPresenterInmpl implements MainActivityPresenter {
    MainActivity mainActivity;
    OnGetDataSucssec onGetDataSucsec;
    MainActivityModel mainActivityModel;

    public MainActicityPresenterInmpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mainActivityModel = new MainActicityModelImpl();
    }

    private ArrayList<ItemCategory> itemCategories;
    private ArrayList<ItemVideo> itemVideos;

    @Override
    public void setOnGetDataSucsec(OnGetDataSucssec onGetDataSucsec) {
        this.onGetDataSucsec = onGetDataSucsec;
        mainActivity.showDialogLoadData();
        mainActivityModel.getCategorys(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    itemCategories = (ArrayList<ItemCategory>) msg.obj;
                    mainActivityModel.getVideos(new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            itemVideos = (ArrayList<ItemVideo>) msg.obj;
                            setListCategorys();
                            setListVideos();
                            mainActivity.setMenuDrawView();
                            mainActivity.onSucsec();
                        }
                    });
                } catch (Exception e) {
                    mainActivity.onFail();
                }


            }
        });


    }


    @Override
    public void setListVideos() {
        mainActivity.setItemVideos(itemVideos);
    }

    @Override
    public void setListCategorys() {
        mainActivity.setItemCategories(itemCategories);
    }
}
