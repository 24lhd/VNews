package com.duongstudio.sqlite;

import com.duongstudio.obj.ItemCategory;
import com.duongstudio.obj.ItemVideo;

import java.util.ArrayList;

/**
 * Created by d on 8/26/2017.
 */

interface MySQLInter {
    public void insertVideos(String json);
    public void initDatabase();

    public void insertCates(String json);

    public void deletaAllCates();

    public void deletaAllVideos();

    public ArrayList<ItemVideo> selectVideos();

    public ArrayList<ItemCategory> selectCates();
}
