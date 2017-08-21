package com.duongstudio.mvp.main;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainActivityView,
        OnGetDataSucssec {
    NavigationView navigationView;
    MainActivityPresenter mainActivityPresenter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


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
            return itemCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return itemCategories.get(position).nameCategory;
        }
    }

    public void initViewIntro() {
        setContentView(R.layout.intro_layout);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
//        getWindow().setFlags(FLAG_TRANSLUCENT_NAVIGATION, FLAG_TRANSLUCENT_NAVIGATION);
        mainActivityPresenter = new MainActicityPresenterInmpl(this);
        mainActivityPresenter.setOnGetDataSucsec(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewIntro();
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

    private void rate() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        setCategory(item.getTitleCondensed().toString());
        rate();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //    public AlertDialog.Builder builderDialogLoading;
    public ProgressDialog progressDialog;

    @Override
    public void showDialogLoadData() {
//        builderDialogLoading = new AlertDialog.Builder(this);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle(getResources().getString(R.string.dialog_loading_data_title));
//        progressDialog.setMessage(getResources().getString(R.string.dialog_loading_data_message));
//        progressDialog.setCancelable(false);
//        progressDialog.show();
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

    @Override
    public void hideDialogLoadData() {
        try {
//            progressDialog.dismiss();
            getWindow().clearFlags(FLAG_FULLSCREEN);
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
//        navigationView.setClickable(true);
            tabLayout = (TabLayout) findViewById(R.id.tb_layout);
            mViewPager = (ViewPager) findViewById(R.id.container);
            tabLayout.setSelectedTabIndicatorHeight(0);
            toggle.syncState();

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setBarTitle(getResources().getString(R.string.app_name) + " - " + itemCategories.get(position).nameCategory);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {

        }

    }

    @Override
    public void setCategory(String jsonCategory) {
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
