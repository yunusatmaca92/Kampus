package com.gtu.yunus.kampus.Dining;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.gtu.yunus.kampus.InformationSingleton;
import com.gtu.yunus.kampus.R;
import com.gtu.yunus.kampus.Request.DownloadImg;
import com.gtu.yunus.kampus.Request.HTTPRequest;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

public class KelebekCafe extends AppCompatActivity {

    //private getFoodList foodList;
    private final String FOOD_LIST_URL = "http://www.gtu.edu.tr/kategori/2590/0/display.aspx?languageId=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelebek_cafe);

        init();
    }

    @SuppressLint("StaticFieldLeak")
    private void init() {
        final Bitmap[] img = new Bitmap[1];
        final ImageView ımageView = findViewById(R.id.kelebek_image);

        if(InformationSingleton.getInstance().getImgKelebekCafe() == null){

            new AsyncTask<Void,Void,Void>(){
                //private Bitmap img;
                @Override
                protected Void doInBackground(Void... voids) {

                    Connection.Response urls = null;
                    Document urlsDoc = null;
                    HTTPRequest httpRequest = new HTTPRequest(urlsDoc);
                    httpRequest.GET(FOOD_LIST_URL);
                    urlsDoc = httpRequest.getDocument();

                    String imageURL = "";

                    if (urlsDoc != null) {
                        String temp = "http://www.gtu.edu.tr";
                        imageURL = urlsDoc.select("div[id=main-content] > p > img").last().attr("src");
                        Log.d("ImgURL","img--"+imageURL);
                        if(imageURL.charAt(imageURL.length()-1) == '/') {
                            temp += imageURL;
                            imageURL = temp;
                        }
                        else {
                            temp += "/" + imageURL;
                            imageURL = temp;
                        }
                    }
                    DownloadImg downloadImg = new DownloadImg(imageURL);
                    downloadImg.download();
                    img[0] = downloadImg.getBitmap();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    InformationSingleton.getInstance().setImgKelebekCafe(img[0]);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ımageView.setImageBitmap(img[0]);
                        }
                    });
                    //super.onPostExecute(aVoid);
                }
            }.execute();


        }
        else{
            img[0] = InformationSingleton.getInstance().getImgKelebekCafe();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ımageView.setImageBitmap(img[0]);
                }
            });
        }
    }
}
