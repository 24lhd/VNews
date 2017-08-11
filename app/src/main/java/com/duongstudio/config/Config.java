package com.duongstudio.config;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class Config {

    public static final String URL_CATEGORYS = "https://apivideos.herokuapp.com/cates";
    public static final String URL_VIDEOS = "https://apivideos.herokuapp.com/videos";
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_VIDEO = "KEY_VIDEO";


    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }
    public static void downloadToFoderDownload(final String fileName, String url, final Context context) {
        url=url.replace("\\","");
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);

        File file = new File(destination);
        if (file.exists())
            file.delete();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("File cửa bạn sẽ được lưu tại thư mục Downloads");
        request.setTitle("Đang tải " + fileName);
        request.setDestinationUri(uri);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
//                Snackbar.make(view,"Already downloaded " + fileName + " to folder  Download",Snackbar.LENGTH_SHORT).show();
                Toast.makeText(ctxt, "Already downloaded " + fileName + " to folder  Download", Toast.LENGTH_SHORT).show();
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
