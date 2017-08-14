package com.duongstudio.mvp.videoview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duongstudio.config.Config;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.videotintuc.R;
import com.facebook.FacebookSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by D on 8/11/2017.
 */

public class VideoViewActivity extends AppCompatActivity implements VideoView {
    ImageView imvAddPlayList;
    ImageView imvShare;
    ImageView imvDownload;
    private NativeAd nativeAd;
    private ItemVideo itemVideo;
    private String TAG = "VideoViewActivity";

    private void showNativeAd() {
        nativeAd = new NativeAd(this, getResources().getString(R.string.facebook_ads_id_on_video_view));
        nativeAd.setAdListener(new AdListener() {

            @Override
            public void onError(Ad ad, AdError error) {
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(VideoViewActivity.this, nativeAd, NativeAdView.Type.HEIGHT_300);
                LinearLayout nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
                nativeAdContainer.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        // Request an ad
        nativeAd.loadAd();
    }

    AppEventsLogger logger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_layout);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        logger = AppEventsLogger.newLogger(this);
        setConfigPlay(getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initView();
//        AdSettings.addTestDevice("bb7947a20f1c895c6110aebeb8a771a5");
        showNativeAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void initView() {
        imvAddPlayList = (ImageView) findViewById(R.id.video_view_im_add_playlist);
        imvShare = (ImageView) findViewById(R.id.video_view_im_share);
        imvDownload = (ImageView) findViewById(R.id.video_view_im_download);
        imvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gửi mã tracking lên FB analytic
                logger.logEvent("Button_imvDownload_click");
                if (isStoragePermissionGranted()) {
                    Config.downloadToFoderDownload(itemVideo.getTitle() + ".mp4", itemVideo.getLinkVideo(), VideoViewActivity.this);
                }
//                if (ActivityCompat.shouldShowRequestPermissionRationale(VideoViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                } else
//                    xinQuyen(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
            }
        });
        imvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logger.logEvent("Button_imvShare_click");
                Toast.makeText(VideoViewActivity.this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
        imvAddPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logger.logEvent("Button_imvAddPlayList_click");
                Toast.makeText(VideoViewActivity.this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Bạn chưa cấp quyền cho xử lí này", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void xinQuyen(String quyen, int indexResult) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(quyen)
                    != PackageManager.PERMISSION_GRANTED) {
                //            if (ActivityCompat.shouldShowRequestPermissionRationale(this, quyen)) {
                //                Config.downloadToFoderDownload(itemVideo.getTitle() + ".mp4", itemVideo.getLinkVideo(), VideoViewActivity.this);
                //            } else {
                ActivityCompat.requestPermissions(this, new String[]{quyen}, indexResult);
                //            }
            }
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void setConfigPlay(Intent intent) {
        String jsonVideo = intent.getStringExtra(Config.KEY_VIDEO);
        itemVideo = new Gson().fromJson(jsonVideo, ItemVideo.class);
        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.video_view_videoplayer);
        jcVideoPlayerStandard.setUp(itemVideo.getLinkVideo().replace("\\", "")
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, itemVideo.getTitle());
        Glide.with(this).load(itemVideo.getImage()).into(jcVideoPlayerStandard.thumbImageView);
    }

    @Override
    public void downloadVideo() {

    }

    @Override
    public void addToPlayList() {

    }

    @Override
    public void shareVideo() {

    }

    @Override
    public void showNotiDownload() {

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
