package com.duongstudio.mvp.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.duongstudio.config.Config;
import com.duongstudio.listener.OnGetDataSucssec;
import com.duongstudio.mvp.listvideo.ListVideoFragment;
import com.duongstudio.mvp.videoview.VideoViewActivity;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.videotintuc.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainActivityView, OnGetDataSucssec {
    NavigationView navigationView;
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setStatusBarBackgroundColor(getResources().getColor(R.color.colorNone));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mainActivityPresenter = new MainActicityPresenterInmpl(this);
        mainActivityPresenter.setOnGetDataSucsec(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.action_rate) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        setCategory(item.getTitleCondensed().toString());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //    public AlertDialog.Builder builderDialogLoading;
    public ProgressDialog progressDialog;

    @Override
    public void showDialogLoadData() {
//        builderDialogLoading = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.dialog_loading_data_title));
        progressDialog.setMessage(getResources().getString(R.string.dialog_loading_data_message));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    // Add to each long-lived activity
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    // for Android, you should also log app deactivation
    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

////Gọi sau setContentView

    @Override
    public void hideDialogLoadData() {
        try{
            progressDialog.dismiss();
        }catch (Exception e){

        }

    }

    @Override
    public void setCategory(String jsonCategory) {
        ListVideoFragment listVideoFragment = new ListVideoFragment();
        Bundle bundle = new Bundle();
        ItemCategory itemCategory = gson.fromJson(jsonCategory, ItemCategory.class);
        setBarTitle(itemCategory.nameCategory);
        bundle.putString(Config.KEY_CATEGORY, jsonCategory);
        listVideoFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_view, listVideoFragment).commit();
    }

    @Override
    public void startSetting() {

    }

    @Override
    public void startRate() {

    }

    @Override
    public void setBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public ArrayList<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(ArrayList<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    public ArrayList<ItemVideo> getItemVideos() {
        return itemVideos;
    }

    public void setItemVideos(ArrayList<ItemVideo> itemVideos) {
        this.itemVideos = itemVideos;
    }

    private ArrayList<ItemVideo> itemVideos;
    private ArrayList<ItemCategory> itemCategories;
    Gson gson = new Gson();

    @Override
    public void setMenuDrawView() {

        for (ItemCategory itemCategory : itemCategories) {
            navigationView.getMenu().add("")
                    .setTitle(itemCategory.nameCategory)
                    .setIcon(R.drawable.ic_ondemand_video_white_36dp)
                    .setCheckable(true)
                    .setTitleCondensed(gson.toJson(itemCategory));
        }
    }

    @Override
    public void startViewVideo(ItemVideo itemVideo) {
        Intent intent = new Intent(this, VideoViewActivity.class);
        intent.putExtra(Config.KEY_VIDEO, gson.toJson(itemVideo));
        startActivity(intent);
    }

    @Override
    public void onSucsec() {
        hideDialogLoadData();
        setCategory(gson.toJson(itemCategories.get(0)));
    }

    @Override
    public void onFail() {
        Toast.makeText(this, "Error, Kiểm tra lại kết nối internet và thử lại sau", Toast.LENGTH_SHORT).show();
    }

    public Gson getGson() {
        return gson;
    }
}
