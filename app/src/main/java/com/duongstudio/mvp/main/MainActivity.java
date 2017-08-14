package com.duongstudio.mvp.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.duongstudio.config.Config;
import com.duongstudio.listener.OnGetDataSucssec;
import com.duongstudio.mvp.listvideo.ListVideoFragment;
import com.duongstudio.mvp.videoview.VideoViewActivity;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.videotintuc.R;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainActivityView,
        OnGetDataSucssec {
    NavigationView navigationView;
    MainActivityPresenter mainActivityPresenter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tb_layout2);
        mViewPager = (ViewPager) findViewById(R.id.container2);
        mainActivityPresenter = new MainActicityPresenterInmpl(this);
        mainActivityPresenter.setOnGetDataSucsec(this);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<ItemCategory> itemCategories;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<ItemCategory> itemCategories) {
            super(fm);
            this.itemCategories = itemCategories;
        }

        @Override
        public Fragment getItem(int position) {
            return ListVideoFragment.newInstance(itemCategories.get(position));
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return itemCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return itemCategories.get(position).nameCategory;
        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Log.e("CharSequence",itemCategories.get(position).nameCategory);
//
////            switch (position) {
////                case 0:
////                    return "First Tab";
////                case 1:
////                default:
////                    return "Second Tab";
////            }
//        }
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
//        setContentView(R.layout.activity_main);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.setStatusBarBackgroundColor(getResources().getColor(R.color.colorNone));
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        mainActivityPresenter = new MainActicityPresenterInmpl(this);
//        mainActivityPresenter.setOnGetDataSucsec(this);
//    }
    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
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
        try {
            progressDialog.dismiss();
        } catch (Exception e) {

        }

    }

    @Override
    public void setCategory(String jsonCategory) {
        ListVideoFragment listVideoFragment = new ListVideoFragment();
        Bundle bundle = new Bundle();
        ItemCategory itemCategory = new Gson().fromJson(jsonCategory, ItemCategory.class);
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

//        for (ItemCategory itemCategory : itemCategories) {
//            navigationView.getMenu().add("")
//                    .setTitle(itemCategory.nameCategory)
//                    .setIcon(R.drawable.ic_ondemand_video_white_36dp)
//                    .setCheckable(true)
//                    .setTitleCondensed(gson.toJson(itemCategory));
//        }
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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getItemCategories());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFail() {
        Toast.makeText(this, "Error, Kiểm tra lại kết nối internet và thử lại sau", Toast.LENGTH_SHORT).show();
    }

    public Gson getGson() {
        return gson;
    }
}
