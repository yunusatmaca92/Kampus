package com.gtu.yunus.kampus.Request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImg {
    private String imgURL;
    private Bitmap bitmap;

    public DownloadImg(String url){
        this.imgURL = url;
    }
    public void download(){
        try{
            URL url = new URL(imgURL);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
            bitmap = bmp;

            connection.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
