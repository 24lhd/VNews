package com.duongstudio.mvp.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.duongstudio.listener.OnGetDataSucssec;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.recerver.MyServiceGetData;
import com.duongstudio.sqlite.MySQL;

import java.util.ArrayList;

import static com.duongstudio.recerver.MyServiceGetData.isRunning;

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
        class ExeTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                MySQL mySQL = new MySQL(mainActivity);
                itemCategories = mySQL.selectCates();
                itemVideos = mySQL.selectVideos();
                if (itemVideos.size() > 0) {
                    setListCategorys();
                    setListVideos();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (itemVideos.size() > 0) {
                    mainActivity.setMenuDrawView();
                    mainActivity.onSucsec();
                } else {
                    try {
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
                                            if (!isRunning(mainActivity)) {
                                                Intent intent1 = new Intent(mainActivity, MyServiceGetData.class);
                                                mainActivity.startService(intent1);
                                            }
                                        }
                                    }, mainActivity);
                                } catch (Exception e) {
                                    mainActivity.onFail();
                                }
                            }
                        }, mainActivity);
                    } catch (Exception e) {
                        Log.e("vnews", e.getMessage());
//            setOnGetDataSucsec(onGetDataSucsec);
                    }
                }


            }
        }
        new ExeTask().execute();


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
