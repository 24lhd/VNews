package com.duongstudio.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by d on 17/01/2017.
 */

public class DuongSQLite {
    public DuongSQLite() {
    }

    public void cloneDatabase() {
        database.close();
    }

    public void createTable(String tbname, String clum) {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + tbname + " (" +
                "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
                clum +
                ");");
    }

    private SQLiteDatabase database;

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public void runCommand(String command) {
        database.execSQL(command);

    }

    public void createOrOpenDataBases(Context context, String nameDatabases) {
        try {
            database = context.openOrCreateDatabase(nameDatabases + ".sqlite", Context.MODE_APPEND, null);
        } catch (Exception e) {
            database = context.openOrCreateDatabase(nameDatabases + ".sqlite", Context.MODE_APPEND, null);
        }
    }

    public void copyDataBase(Context context, String dataBaseName) throws IOException {
        String outFileName = "/data/data/" + context.getPackageName() + "/databases/" + dataBaseName;
        if (checkDataBase(context, outFileName)) return;
        InputStream myInput = context.getAssets().open(dataBaseName);
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public boolean checkDataBase(Context context, String pathDatabase) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(pathDatabase + ".sqlite", null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

}
