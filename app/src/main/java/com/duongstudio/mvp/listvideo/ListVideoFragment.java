package com.duongstudio.mvp.listvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.duongstudio.config.Config;
import com.duongstudio.mvp.main.MainActivity;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.videotintuc.R;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;

/**
 * Created by D on 8/11/2017.
 */

public class ListVideoFragment extends Fragment implements ListVideoView, ListVideoModel, ListVideoPresenter {
    private View viewContent;
    RecyclerView rcvVideo;
    ProgressBar pBarLoading;
    ArrayList<ItemVideo> itemVideos;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String keyCate = getArguments().getString(Config.KEY_CATEGORY);
        AdSettings.addTestDevice("bb7947a20f1c895c6110aebeb8a771a5");
        viewContent = inflater.inflate(R.layout.list_video_layout, null);
        mainActivity = (MainActivity) getActivity();
        initView();
        ItemCategory itemCategory = mainActivity.getGson().fromJson(keyCate, ItemCategory.class);
        itemVideos = getListVideoFromIDCategory(itemCategory.idCategory);
        showListVideo();
        hideLoadingList();
        return viewContent;
    }

    @Override
    public void initView() {
        rcvVideo = viewContent.findViewById(R.id.list_video_rcv);
        rcvVideo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        pBarLoading = viewContent.findViewById(R.id.list_video_prbar_loading);
    }

    @Override
    public ArrayList<ItemVideo> getListVideoFromIDCategory(String idCate) {
        ArrayList<ItemVideo> itemVideoFinter = new ArrayList<>();
        try {
            for (ItemVideo itemVideo : mainActivity.getItemVideos()) {
                if (itemVideo.getIdCategory().equals(idCate)) {
                    itemVideoFinter.add(itemVideo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemVideoFinter;
    }

    @Override
    public void showLoadingList() {
        pBarLoading.setVisibility(View.VISIBLE);
        rcvVideo.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingList() {
        rcvVideo.setVisibility(View.VISIBLE);
        pBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void showListVideo() {
        ArrayList<Object> listObjects = new ArrayList<>();
        listObjects.addAll(itemVideos);
        NativeAd nativeAd = null;
        rcvVideo.setAdapter(new AdaptorListVideo(rcvVideo, listObjects, nativeAd, 7, mainActivity));
    }

    @Override
    public void setTitleBar() {

    }

    @Override
    public void startVideoView(String jsonVideo) {

    }

    @Override
    public void loadAdsFacebook() {

    }

    @Override
    public void showAdsFacebook() {

    }

    @Override
    public void loadAdsAdmod() {

    }

    @Override
    public void showAdsAdmod() {

    }
}
