package com.gtu.yunus.kampus.menuFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtu.yunus.kampus.CheckNetwork;
import com.gtu.yunus.kampus.InformationSingleton;
import com.gtu.yunus.kampus.Models.Course;
import com.gtu.yunus.kampus.Models.HomePage;
import com.gtu.yunus.kampus.Models.SelectedCoursePage;
import com.gtu.yunus.kampus.Models.TranscriptCourse;
import com.gtu.yunus.kampus.Models.TranscriptPage;
import com.gtu.yunus.kampus.Models.TranscriptTerm;
import com.gtu.yunus.kampus.R;
import com.gtu.yunus.kampus.Request.HTTPRequest;
import com.gtu.yunus.kampus.apolloFragments.SelectedCourse;
import com.gtu.yunus.kampus.apolloSystem;
import com.gtu.yunus.kampus.home;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {

    private final String TAG = "HOME_FRAGMENT";
    private View view;

    private Button login;
    private CheckBox rememberMe;
    private String email = "";
    private String password = "";
    private ProgressDialog pBar;

    private final String MY_PREFS_NAME = "apolloInformation";

    private SharedPreferences.Editor editor;

    public Home(){

    }

    @SuppressLint("CommitPrefEdits")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        editor = Objects.requireNonNull(getActivity()).getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        if(InformationSingleton.getInstance().isRememberMe()){
            view = inflater.inflate(R.layout.fragment_menu_home_2,container,false);
            initialUIIF();
        }else{
            view = inflater.inflate(R.layout.fragment_menu_home, container, false);
            initialUIElse();
        }

        return view;
    }
    private void loginClicked(){
        AutoCompleteTextView emailText = view.findViewById(R.id.userEmail);
        AutoCompleteTextView passwordText = view.findViewById(R.id.userPass);
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(getContext(), "Lütfen bütün alanları doldurunuz!", Toast.LENGTH_LONG).show();
        } else {
            //Log.d("isRememberMe",String.valueOf(rememberMe.isChecked()));
            InformationSingleton.getInstance().setRememberMe(rememberMe.isChecked());
            new getApolloSystem().execute();
        }
    }
    private void initialUIElse(){
        rememberMe = view.findViewById(R.id.login_check_box);
        login = view.findViewById(R.id.home_login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckNetwork.isNetworkAvailable(getActivity()))
                    loginClicked();
                else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Check Internet Connection",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        //Email and Password textBox
        AutoCompleteTextView textEmail = view.findViewById(R.id.userEmail);
        AutoCompleteTextView textPassword = view.findViewById(R.id.userPass);
        //Email or Password Clicked
        textEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = view.findViewById(R.id.wrongTextView);
                if (textView.getVisibility() == View.VISIBLE)
                    textView.setVisibility(View.GONE);
            }
        });
        textPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = view.findViewById(R.id.wrongTextView);
                if (textView.getVisibility() == View.VISIBLE)
                    textView.setVisibility(View.GONE);
            }
        });
    }
    private void initialUIIF(){

        TextView studentName = view.findViewById(R.id.student_name);
        Button login = view.findViewById(R.id.home_login_button);
        Button exit = view.findViewById(R.id.home_exit_button);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("homePage", "");

        HomePage homePage = gson.fromJson(json, HomePage.class);
        studentName.setText(homePage.getHomePageInformation().get(0));

        gson = new Gson();
        json = prefs.getString("selectedCoursePage", "");
        SelectedCoursePage selectedCoursePage = gson.fromJson(json, SelectedCoursePage.class);

        gson = new Gson();
        json = prefs.getString("transcriptPage", "");
        TranscriptPage transcriptPage = gson.fromJson(json, TranscriptPage.class);

        InformationSingleton.getInstance().setRememberMe(true);
        InformationSingleton.getInstance().setHomePage(homePage);
        InformationSingleton.getInstance().setSelectedCoursePage(selectedCoursePage);
        InformationSingleton.getInstance().setTranscriptPage(transcriptPage);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getActivity(), apolloSystem.class);
                startActivity(ıntent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.clear();
                editor.apply();
                InformationSingleton.getInstance().setRememberMe(false);
                Intent ıntent = new Intent(getActivity(), home.class);
                startActivity(ıntent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class getApolloSystem extends AsyncTask<Void,Void,Void>{

        private String loginFormUrl = "http://login.apollo.gtu.edu.tr/tr/Login?return=http%3a%2f%2flogin.apollo.gtu.edu.tr%2f";
        private String loginActionUrl = "http://login.apollo.gtu.edu.tr/tr/Login";
        private String homePageUrl = "http://ogr.apollo.gtu.edu.tr";
        private String selectedCourseUrl = "http://ogr.apollo.gtu.edu.tr/tr/Ders";
        private String transcriptUrl = "http://ogr.apollo.gtu.edu.tr/tr/Transkript";

        private HashMap<String, String> cookies = new HashMap<>();
        private HashMap<String, String> formData = new HashMap<>();

        private HomePage homePage = null;
        private SelectedCoursePage selectedCoursePage = null;
        private TranscriptPage transcriptPage = null;

        @Override
        protected void onPreExecute() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pBar = new ProgressDialog(getActivity());
                    pBar.setMessage("Loading");
                    pBar.setCancelable(false);
                    pBar.show();
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Connection.Response loginForm = null;
            Document loginDocument = null;

            HTTPRequest httpRequest = new HTTPRequest(loginDocument);

            loginForm = httpRequest.GET(loginFormUrl);

            loginDocument = httpRequest.getDocument();

            if(loginDocument != null) {
                cookies.putAll(loginForm.cookies());

                String returnURL = loginDocument.select("input[name=ReturnURL]").attr("value");
                String RequestVerificationToken = loginDocument.select("input[name=__RequestVerificationToken]").attr("value");
                String rememberMe = loginDocument.select("input[name=RememberMe]").attr("value");

                //Send login Request
                formData.put("rememberMe", rememberMe);
                formData.put("__RequestVerificationToken", RequestVerificationToken);
                formData.put("UserName", email);
                formData.put("Password", password);
                formData.put("ReturnURL", returnURL);

                //Log.d(TAG,rememberMe+RequestVerificationToken+email+password+returnURL);

                //getHomePage
                int checkLogin = login();
                //Log.d(TAG,String.valueOf(checkLogin));
                if (checkLogin == 1) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pBar.dismiss();
                            TextView textView = view.findViewById(R.id.wrongTextView);
                            textView.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (checkLogin == 2) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setMessage("Loading Home...");
                        }
                    });

                    getHomePage();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setMessage("Loading Selected Course...");
                        }
                    });

                    getSelectedCoursePage();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setMessage("Loading Transcript...");
                        }
                    });

                    getTranscriptPage();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pBar.dismiss();
                        }
                    });

                    Intent ıntent = new Intent(getActivity(),apolloSystem.class);
                    startActivity(ıntent);

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(getActivity(),"Something went wrong!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    Toast.makeText(getActivity(),"Something went wrong!",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pBar != null)
                pBar.dismiss();
            if(InformationSingleton.getInstance().isRememberMe())
                saveData();
        }
        private void saveData(){
            Gson gson = new Gson();

            String jsonH = gson.toJson(homePage);
            String jsonS = gson.toJson(selectedCoursePage);
            String jsonT = gson.toJson(transcriptPage);

            editor.putString("homePage",jsonH);
            editor.putString("selectedCoursePage", jsonS);
            editor.putString("transcriptPage",jsonT);
            editor.apply();
        }
        private int login(){
            Connection.Response homePageForm = null;
            Document homePageDoc = null;

            HTTPRequest httpRequest = new HTTPRequest(homePageDoc);
            homePageForm = httpRequest.POST(loginActionUrl,cookies,formData);
            homePageDoc = httpRequest.getDocument();

            String wrongPass = homePageDoc.select("div[class=alert alert-danger panel]").html();
            String loginHome = homePageDoc.select("a[class=dropdown-toggle username] > span").text();

            if (wrongPass.length() > 0)
                return 1;
            else if(loginHome.length()>0)
                return 2;
            else
                return 3;
        }
        private void getTranscriptPage(){
            Connection.Response transcriptForm = null;
            Document transcriptDocument = null;

            HTTPRequest httpRequest = new HTTPRequest(transcriptDocument);
            httpRequest.GET(transcriptUrl,cookies);
            transcriptDocument = httpRequest.getDocument();

            if(transcriptDocument != null) {
                Elements transcriptTable = transcriptDocument.select("div[id=transkriptTasiyici] > div[class=col-md-6]");

                ArrayList<TranscriptTerm> terms = new ArrayList<>();

                for(int i=1;i<transcriptTable.size();++i){

                    String head = transcriptTable.get(i).select("div > div > h5").text();
                    //Log.d(TAG,head);

                    Elements transcriptTableBody = transcriptTable.get(i).select("div[class=panel-body] > table > tbody > tr > td");

                    ArrayList<TranscriptCourse> courses = new ArrayList<>();
                    //Log.d(TAG,"Sİze   "+ transcriptTableBody.size());
                    for(int j=0;j<transcriptTableBody.size(); j+=6){
                        //String code = transcriptTableBody.get(j).text();
                        //String name = transcriptTableBody.get(j+1).text();
                        //String type = transcriptTableBody.get(j+2).text();
                        //String note = transcriptTableBody.get(j+3).text();
                        //String gtuKredi = transcriptTableBody.get(j+4).text();
                        //String aktsKredi = transcriptTableBody.get(j+5).text();
                        courses.add(new TranscriptCourse(transcriptTableBody.get(j).text(),
                                transcriptTableBody.get(j+1).text(),
                                transcriptTableBody.get(j+2).text(),
                                transcriptTableBody.get(j+3).text(),
                                transcriptTableBody.get(j+4).text(),
                                transcriptTableBody.get(j+5).text()));
                        //Log.d(TAG,j + " " + transcriptTableBody.get(j).text()
                              //  + " " + transcriptTableBody.get(j+1).text()
                               // + " " + transcriptTableBody.get(j+2).text()
                               // + " " + transcriptTableBody.get(j+3).text()
                               // + " " + transcriptTableBody.get(j+4).text()
                               // + " " + transcriptTableBody.get(j+5).text());
                        //Log.d(TAG,a);
                    }

                    Elements transcriptTableFoot = transcriptTable.get(i).select("div[class=panel-body] > table > tfoot > tr > td");

                    //String totalGTUKredi = transcriptTableFoot.get(1).text();
                    //String totalAKTSKredi = transcriptTableFoot.get(2).text();
                    //String average1 = transcriptTableFoot.get(9).text();
                    //String average2 = transcriptTableFoot.get(10).text();
                    //String average3 = transcriptTableFoot.get(11).text();
                    //String average4 = transcriptTableFoot.get(12).text();
                    //String average5 = transcriptTableFoot.get(13).text();

                    TranscriptTerm term = new TranscriptTerm(head,courses,transcriptTableFoot.get(1).text(),
                            transcriptTableFoot.get(2).text(),
                            transcriptTableFoot.get(11).text(),
                            transcriptTableFoot.get(12).text(),
                            transcriptTableFoot.get(13).text(),
                            transcriptTableFoot.get(14).text(),
                            transcriptTableFoot.get(15).text());
                    /*for(int j=0;j<transcriptTableFoot.size();++j){
                        String a = transcriptTableFoot.get(j).text();
                        Log.d(TAG,a);
                    }*/
                    terms.add(term);
                }
                transcriptPage = new TranscriptPage(terms);
                InformationSingleton.getInstance().setTranscriptPage(transcriptPage);
                //Log.d(TAG,String.valueOf(transcriptTable.size()));
            }else{
                InformationSingleton.getInstance().setTranscriptPage(null);
                Log.d(TAG,"Transcript null");
            }
        }


        private void getHomePage(){
            Connection.Response homePageForm = null;
            Document homePageDocument = null;

            HTTPRequest httpRequest = new HTTPRequest(homePageDocument);
            httpRequest.GET(homePageUrl,cookies);

            homePageDocument = httpRequest.getDocument();

            if(homePageDocument != null){

                String studentName = homePageDocument.select("div[class=table-responsive] > h3 > strong").first().text();
                Elements ogrHomePageTable = homePageDocument.select("table[class=table table-condensed] > tbody > tr > td");

                /*Log.d(TAG,studentName);
                for(int i=0;i<ogrHomePageTable.size();++i) {
                    Log.d(TAG, ogrHomePageTable.get(i).text());
                }*/
                ArrayList<String> homePageInformation = new ArrayList<>();

                homePageInformation.add(studentName);
                for(int i=0;i<ogrHomePageTable.size();++i)
                    homePageInformation.add(ogrHomePageTable.get(i).text());

                homePage = new HomePage(homePageInformation);
                InformationSingleton.getInstance().setHomePage(homePage);

            }else{
                InformationSingleton.getInstance().setHomePage(null);
                Log.d(TAG,"HomePage NULL!");
            }
        }
        private void getSelectedCoursePage(){
            Connection.Response selectedCoursePageForm = null;
            Document selectedCourseDocument = null;

            HTTPRequest httpRequest = new HTTPRequest(selectedCourseDocument);
            httpRequest.GET(selectedCourseUrl,cookies);

            selectedCourseDocument = httpRequest.getDocument();

            if(selectedCourseDocument != null){

                ArrayList<String> panelInfo = new ArrayList<>();
                Elements panel = selectedCourseDocument.select("table[class=table table-condensed table-striped] > tbody > tr > td");
                for(int i=0;i<panel.size();++i) {
                    panelInfo.add(panel.get(i).text());
                    //Log.d(TAG, panel.get(i).text());
                }

                Elements optionYear = selectedCourseDocument.select("select[id=selectYil] > option");
                ArrayList<String> Syears = new ArrayList<>();
                for (int i = 0; i < optionYear.size(); ++i)
                    Syears.add(optionYear.text());


                Elements optionTerm = selectedCourseDocument.select("select[id=selectDonem] > option");
                ArrayList<String> Sterms = new ArrayList<>();
                for (int i = 0; i < optionTerm.size(); ++i)
                    Sterms.add(optionTerm.text());

                ArrayList<Course> courses = new ArrayList<>();
                Elements selectedCourseContainer = selectedCourseDocument.select("div[id=dersContainer] > div");
                for(int i=0;i<selectedCourseContainer.size();++i){
                    //Log.d(TAG,selectedCourseContainer.get(i).select("h5 > a").text());
                    //Log.d(TAG,selectedCourseContainer.get(i).select("table > tbody > tr > td").text());
                    courses.add(new Course(selectedCourseContainer.get(i).select("h5 > a").text(),
                            selectedCourseContainer.get(i).select("table > tbody > tr > td").text()));
                }

                selectedCoursePage = new SelectedCoursePage(courses,panelInfo,Syears,Sterms);
                InformationSingleton.getInstance().setSelectedCoursePage(selectedCoursePage);

                /*if(InformationSingleton.getInstance().isRememberMe()){
                    Gson gson = new Gson();
                    String json = gson.toJson(selectedCoursePage);
                    editor.putString("selectedCoursePage", json);
                    editor.apply();
                }*/
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pBar.setMessage("Loading Almost Done!.");
                    }
                });

            }else{
                InformationSingleton.getInstance().setSelectedCoursePage(null);
                Log.d(TAG,"Selected Course Null");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(pBar != null)
            pBar.dismiss();
    }
}
