package com.gtu.yunus.kampus.menuFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gtu.yunus.kampus.CheckNetwork;
import com.gtu.yunus.kampus.R;
import com.gtu.yunus.kampus.Request.HTTPRequest;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GTURadio extends AppCompatActivity {

    private ProgressBar progressBarPlay;
    private ProgressBar progressBarMessage;
    private ProgressBar progressBarStreams;
    private ScrollView scrollView;
    private boolean isInForeground = false;
    private boolean prepared = false;
    private ImageButton buttonPlay;
    private TextView textTouch;
    private TextView messages;
    private boolean playing = false;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private SeekBar sBar;
    private List<String> radioMessages = new ArrayList<>() ;
    private List<String> radioStreams = new ArrayList<>() ;
    private Button sendMessage;
    private Dialog sendMessagePop;
    private WebView webView;
    private TextView txtClose;
    private ProgressBar pb_per;
    private final String url = "http://94.102.5.183:9928/;stream.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CheckNetwork.isNetworkAvailable(this)) {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            setContentView(R.layout.activity_gturadio);

            sendMessagePop = new Dialog(this);
            initializeUIElements();

            new PlayerMusic().execute(url);
        }
        else {
            setContentView(R.layout.gturadio_no_network);
        }
    }

    private void initializeUIElements() {
        sendMessage = findViewById(R.id.send_message);
        sendMessage.setEnabled(false);

        progressBarPlay = findViewById(R.id.progb1);
        progressBarMessage = findViewById(R.id.prog_bar_messages);
        progressBarStreams = findViewById(R.id.prog_bar_streams);

        scrollView = findViewById(R.id.streams);
        buttonPlay = findViewById(R.id.play_button);

        textTouch = findViewById(R.id.touch_me);
        messages = findViewById(R.id.home_fragment_radio_text);
        sBar = findViewById(R.id.volumeControl1);

        sBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing)
                    stopPlaying();
                else
                    startPlaying();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp("http://radyogtu.com/mesajistek.asp?sayfa=yaz");
            }
        });
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void showPopUp(String url){
        sendMessagePop.setContentView(R.layout.pop_up_web_view);

        webView = sendMessagePop.findViewById(R.id.pop_up_web_view);
        pb_per = sendMessagePop.findViewById(R.id.progressBar_web);
        txtClose = sendMessagePop.findViewById(R.id.pop_up_close);

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
                sendMessagePop.dismiss();
            }
        });
        sendMessagePop.show();
    }
    private void startPlaying() {
        playing = true;

        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_button);
        buttonPlay.startAnimation(startRotateAnimation);

        mediaPlayer.start();

    }

    private void stopPlaying() {
        playing = false;
        textTouch.setText("Dokun bana!");
        mediaPlayer.pause();

        buttonPlay.clearAnimation();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playing)
            mediaPlayer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(prepared)
            mediaPlayer.release();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(setIntent);
    }*/

    private class getMessages extends  AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarMessage.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getMessages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBarMessage.setVisibility(View.GONE);

            messages.setSelected(true);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messages.setText(radioMessages.toString());
                }
            });

            new getStreams().execute();
        }
    }
    private class getStreams extends  AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarStreams.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setStreams();
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBarStreams.setVisibility(View.GONE);
        }
    }
    private class PlayerMusic extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBarPlay.setVisibility(View.VISIBLE);

            buttonPlay.setEnabled(false);
            sendMessage.setEnabled(false);

        }

        @Override
        protected Boolean doInBackground(String... voids) {
            try {

                mediaPlayer.setDataSource(voids[0]);
                mediaPlayer.prepare();
                prepared = true;
                isInForeground = true;

                //getMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            progressBarPlay.setVisibility(View.INVISIBLE);
            buttonPlay.setEnabled(true);
            sendMessage.setEnabled(true);

            new getMessages().execute();
            //setText Play
            //messages.setSelected(true);
            //messages.setText(radioMessages.toString());
            //setStreams();
        }
    }
    private void getMessages(){
        Connection.Response gtuRadio = null;
        Document gtuRadioDoc = null;
        HTTPRequest httpRequest = new HTTPRequest(gtuRadioDoc);
        httpRequest.GET("http://radyogtu.com/");

        gtuRadioDoc = httpRequest.getDocument();

        if(gtuRadioDoc != null){
            radioMessages = gtuRadioDoc.select("marquee[onmouseover=this.stop();]").eachText();
            Elements stream = gtuRadioDoc.select("table[width=98%]>tbody>tr>td");

            for(int i= 0;i<stream.size();++i) {
                radioStreams.add(stream.get(i).text());
                // Log.d("YUNUS", stream.get(i).text());
            }
            /*for(int i=0;i<radioMessages.size();++i)
                Log.d("RadyoStr",String.valueOf(i) + radioMessages.get(i).toString());*/
        }else{
            radioMessages = null;
        }
    }
    private void setStreams(){
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for(int i=1;i<radioStreams.size();++i){
            TextView textView = makeTextView(15,radioStreams.get(i),radioStreams.get(i+1),params);
            linearLayout.addView(textView);
            ++i;
        }
        scrollView.addView(linearLayout);

    }
    private TextView makeTextView(int textSize, String text1 ,String text2, LinearLayout.LayoutParams params){
        params.setMarginStart(5);
        params.gravity =Gravity.START;
        TextView textView = new TextView(this);
        textView.setText(text1+"    "+text2);
        textView.setTextSize(textSize);
        textView.setLayoutParams(params);
        textView.setTextColor(getResources().getColor(R.color.mainColor));
        return textView;
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
}
