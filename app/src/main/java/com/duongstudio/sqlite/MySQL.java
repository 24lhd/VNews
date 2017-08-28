package com.duongstudio.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.duongstudio.config.Config;
import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by d on 8/26/2017.
 */

public class MySQL extends DuongSQLite implements MySQLInter {

    public MySQL(Context context) {
        this.context = context;
        initDatabase();
    }

    private Context context;

    @Override
    public void insertVideos(final String json) {
        runCommand("INSERT INTO `videos`(`stt`,`videos`) VALUES (NULL,'" + json.replace("'", "\"") + "')");
//        Log.e("vnews", "INSERT INTO `videos`");

    }

    @Override
    public void initDatabase() {
        createOrOpenDataBases(context, Config.NAME_DATEBASE);
        runCommand(Config.CMD_CREATE_TABLE_CATEGORYS);
        runCommand(Config.CMD_CREATE_TABLE_VIDEOS);
    }

    @Override
    public void insertCates(final String json) {
        runCommand("INSERT INTO `categorys`(`stt`,`categorys`) VALUES (NULL,'" + json.replace("'", "\"") + "')");
//        Log.e("vnews", "INSERT INTO `categorys`");
    }

    @Override
    public void deletaAllCates() {
        runCommand("DROP TABLE `categorys`");
        runCommand(Config.CMD_CREATE_TABLE_CATEGORYS);
    }

    @Override
    public void deletaAllVideos() {
        runCommand("DROP TABLE `videos`");
        runCommand(Config.CMD_CREATE_TABLE_VIDEOS);
    }

    @Override
    public ArrayList<ItemVideo> selectVideos() {
        ArrayList<ItemVideo> itemVideos = new ArrayList<>();
        Gson gson = new Gson();
        initDatabase();
        Cursor cursor = getDatabase().query(Config.CL_VIDEOS, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                ItemVideo itemVideo = gson.fromJson(cursor.getString(cursor.getColumnIndex(Config.CL_VIDEOS)), ItemVideo.class);
                itemVideos.add(itemVideo);
            } catch (Exception e) {
                Log.e("vnews",e.getMessage());
            }
            cursor.moveToNext();
        }
        getDatabase().close();
        return itemVideos;
    }

    @Override
    public ArrayList<ItemCategory> selectCates() {
        ArrayList<ItemCategory> itemCategories = new ArrayList<>();
        Gson gson = new Gson();
        initDatabase();
        Cursor cursor = getDatabase().query(Config.CL_CATEGORYS, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                itemCategories.add(gson.fromJson(cursor.getString(cursor.getColumnIndex(Config.CL_CATEGORYS)), ItemCategory.class));
            } catch (Exception e) {
                Log.e("vnews",e.getMessage());
            }
            cursor.moveToNext();
        }
        getDatabase().close();
        return itemCategories;
    }
}
