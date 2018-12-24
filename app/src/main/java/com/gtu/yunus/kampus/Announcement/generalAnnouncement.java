package com.gtu.yunus.kampus.Announcement;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.gtu.yunus.kampus.Adapters.AnnouncementListAdapter;
import com.gtu.yunus.kampus.InformationSingleton;
import com.gtu.yunus.kampus.R;
import com.gtu.yunus.kampus.Request.DownloadImg;
import com.gtu.yunus.kampus.Request.HTTPRequest;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class generalAnnouncement extends AppCompatActivity {

    private ListView announcementListView;
    private AnnouncementListAdapter adapter;
    private final String url = "http://www.gtu.edu.tr/?languageId=1";
    private List<String> mAnnouncements = new ArrayList<>();
    private List<String> mAnnouncementsURLS = new ArrayList<>();
    private List<String> mImgURLS = new ArrayList<>();
    private List<Bitmap> imageArr = new ArrayList<>();
    private Dialog popUp;
    private ProgressBar pb_per;
    private WebView webView;
    private TextView txtClose;
    private ViewFlipper viewFlipper;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_announcement);

        initialUI();

        getUI getUI = new getUI();
        getUI.execute();

    }
    private void initialUI(){
        popUp = new Dialog(this);
        viewFlipper = findViewById(R.id.image_flipper);
        announcementListView = (ListView)findViewById(R.id.listview_product);
        progressBar = findViewById(R.id.progBaar);
    }
    private void init() {

        for(int i = 0;i<imageArr.size();++i){
            //Log.d("SİZE--",String.valueOf(imageArr.size()));
            flipperImage(imageArr.get(i));
        }
        viewFlipper.startFlipping();
        adapter = new AnnouncementListAdapter(getApplicationContext(), mAnnouncements);
        announcementListView.setAdapter(adapter);
        announcementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = mAnnouncementsURLS.get((Integer) view.getTag());
                if (!url.substring(0, 7).equals("http://")) {
                    String temp = "http://www.gtu.edu.tr";
                    temp += url;
                    url = temp;
                }
                if(url.substring(url.length()-4).equals(".pdf")){
                    String temp = "http://drive.google.com/viewerng/viewer?embedded=true&url=";
                    temp += url;
                    url = temp;
                }
                //Log.d("URL - WEB", url);
                showPopUp(url);
                // }
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showPopUp(String url){
        popUp.setContentView(R.layout.pop_up_web_view);

        webView = popUp.findViewById(R.id.pop_up_web_view);
        pb_per = popUp.findViewById(R.id.progressBar_web);
        txtClose = popUp.findViewById(R.id.pop_up_close);

        webView.setWebViewClient(new myWebClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(false);

        webView.loadUrl(url);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });
        popUp.show();
    }
   public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            pb_per.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            pb_per.setVisibility(View.GONE);
        }
    }
    public void flipperImage(Bitmap bitmap){
        ImageView ımageView = new ImageView(this);
        ımageView.setImageBitmap(bitmap);
        //Log.d("SIZE-BITMAP",String.valueOf(bitmap.getAllocationByteCount()));
        viewFlipper.addView(ımageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    private class getUI extends AsyncTask<Void,Void,Void>{

        private List<String> imgURLS = new ArrayList<>();
        private boolean errorFlag = false;

        @Override
        protected void onPreExecute() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

        }
        @Override
        protected Void doInBackground(Void... voids) {
            //Log.d("isEMPTY--",String.valueOf(InformationSingleton.getInstance().isEmpty()));
            if(InformationSingleton.getInstance().isEmpty()) {
                InformationSingleton.getInstance().setEmpty(false);
                Connection.Response urls = null;
                Document urlsDoc = null;

                HTTPRequest httpRequest = new HTTPRequest(urlsDoc);
                httpRequest.GET(url);
                urlsDoc = httpRequest.getDocument();

                if (urlsDoc != null) {
                    Elements urlsExplain = urlsDoc.select("div[class=col-sm-3] > ul > li");
                    for (int i = 0; i < urlsExplain.size(); ++i) {
                            //Log.d("urlsExplain--", urlsExplain.get(i).text());
                        mAnnouncements.add(urlsExplain.get(i).text());
                    }

                    Elements urlsValue = urlsDoc.select("div[class=col-sm-3] > ul > li > a");
                    for (int i = 0; i < urlsValue.size(); ++i) {
                            //Log.d("urlsValue--",urlsValue.get(i).attr("href"));
                        mAnnouncementsURLS.add(urlsValue.get(i).attr("href"));
                    }

                    Elements urlsImg = urlsDoc.select("div[class=carousel-inner] > div > a > img");
                    for (int i = 0; i < urlsImg.size(); ++i) {
                            //Log.d("urlsValue--", urlsImg.get(i).attr("src"));
                        imgURLS.add(urlsImg.get(i).attr("src"));
                    }

                    for (int i = 0; i < imgURLS.size(); ++i) {
                        if(!imgURLS.get(i).isEmpty()) {
                            if (imgURLS.get(i).charAt(0) != '/') {
                                String temp = "http://www.gtu.edu.tr/";
                                temp += imgURLS.get(i);
                                //Log.d("ANNNOUNCEMENT", temp);
                                DownloadImg downloadImg = new DownloadImg(temp);
                                downloadImg.download();
                                imageArr.add(downloadImg.getBitmap());
                                //downloadImg(temp);
                            } else {
                                String temp = "http://www.gtu.edu.tr";
                                temp += imgURLS.get(i);
                                //Log.d("ANNNOUNCEMENT", temp);
                                DownloadImg downloadImg = new DownloadImg(temp);
                                downloadImg.download();
                                imageArr.add(downloadImg.getBitmap());
                                //downloadImg(temp);
                            }
                        }
                    }
                    InformationSingleton.getInstance().setAnnouncements((ArrayList<String>) mAnnouncements);
                    InformationSingleton.getInstance().setAnnouncementsImgURLs((ArrayList<String>) mImgURLS);
                    InformationSingleton.getInstance().setAnnouncementsURLs((ArrayList<String>) mAnnouncementsURLS);
                    InformationSingleton.getInstance().setImageArr((ArrayList<Bitmap>) imageArr);
                } else {
                    errorFlag = true;
                }
            }
            else {
                mAnnouncements = InformationSingleton.getInstance().getAnnouncements();
                mAnnouncementsURLS = InformationSingleton.getInstance().getAnnouncementsURLs();
                mImgURLS = InformationSingleton.getInstance().getAnnouncementsImgURLs();
                imageArr = InformationSingleton.getInstance().getImageArr();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

            if(errorFlag){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(generalAnnouncement.this,
                                "Sayfa yüklenemiyor..!",Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
                init();
        }
    }
}
