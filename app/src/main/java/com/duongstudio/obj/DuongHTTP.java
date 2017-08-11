package com.duongstudio.obj;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by D on 07/03/2017.
 * lưu ý sử dụng 1 luồng để gọi các phương thức
 */
public class DuongHTTP {
    public DuongHTTP() {
        client = new OkHttpClient();
    }
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;

    /**
     * post dữ liệu định dạng json lên server
     * @param url link dẫn
     * @param json gửi dũ liệu lên bằng string json
     * @throws IOException
     */
    public String postHTTP(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * phương thức get http trả về nội dung của body thông qua 1 urlm
     * @param url đường dẫn
     * @return nội dung của dữ liệu trả về
     * @throws IOException
     */
    public String getHTTP(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * put update dữ liệu
     */
    public String putHTTP(String url, String jsonBody)throws IOException  {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .put(body) //PUT
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }



}