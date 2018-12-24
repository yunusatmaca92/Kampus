package com.gtu.yunus.kampus.apolloFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtu.yunus.kampus.InformationSingleton;
import com.gtu.yunus.kampus.Models.Course;
import com.gtu.yunus.kampus.Models.TranscriptCourse;
import com.gtu.yunus.kampus.Models.TranscriptPage;
import com.gtu.yunus.kampus.Models.TranscriptTerm;
import com.gtu.yunus.kampus.R;

import java.util.ArrayList;

public class Transcript extends Fragment {
    private final String TAG = "Transcript_FRAGMENT";
    private View view;
    private LinearLayout mLinearLayout;
    private TranscriptPage transcriptPage;

    LinearLayout.LayoutParams paramsC = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
    );
    public Transcript(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ap_transcript, container, false);

        initialUI();

        return view;
    }
    private void initialUI(){
        mLinearLayout = view.findViewById(R.id.transcript_linearLayout);
        if(InformationSingleton.getInstance().getTranscriptPage() != null) {

            transcriptPage = InformationSingleton.getInstance().getTranscriptPage();
            ArrayList<TranscriptTerm> transcriptTerms = transcriptPage.getTerms();

            for(int i=0;i<transcriptTerms.size();++i){

                mLinearLayout.addView(makeTitle(transcriptTerms.get(i).getHead()));
                mLinearLayout.addView(makeHead());

                ArrayList<TranscriptCourse> courses = transcriptTerms.get(i).getCourses();
                //Log.d(TAG,String.valueOf(courses.size()));
                for(int j=0;j<courses.size();++j){
                    mLinearLayout.addView(makeLinearLayout(courses.get(j).getCode(),
                            courses.get(j).getName(),
                            courses.get(j).getType(),
                            courses.get(j).getNote(),
                            courses.get(j).getGtuKredi(),
                            courses.get(j).getAktsKredi()));
                    //Log.d(TAG,j +"   " +courses.get(j).getName());
                }

                mLinearLayout.addView(makeFoot(transcriptTerms.get(i).getTotalGTUKredi(),
                        transcriptTerms.get(i).getTotalAKTS(),
                        transcriptTerms.get(i).getAverage1(),
                        transcriptTerms.get(i).getAverage2(),
                        transcriptTerms.get(i).getAverage3(),
                        transcriptTerms.get(i).getAverage4(),
                        transcriptTerms.get(i).getAverage5()));

                mLinearLayout.addView(makeLine());
            }

        }
        else {
            paramsC.gravity = Gravity.CENTER;
            TextView somethingIsWrong = makeTextView(30,paramsC,"Something is wrong..!","blue");
            mLinearLayout.addView(somethingIsWrong);
        }
    }
    private View makeLine(){
        View line = new View(getActivity());
        line.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                55
        ));
        line.setBackgroundColor(getResources().getColor(R.color.mainColor));
        return line;
    }
    private LinearLayout makeFoot(String tGTUKredi,String tAKTSKredi,String Average1,
                                  String Average2,String Average3,String Average4,String Average5){
        int textSize = 12;
        LinearLayout.LayoutParams params1F = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,1f);
        LinearLayout.LayoutParams params6F = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,6f);

        LinearLayout foot = new LinearLayout(getContext());
        foot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        foot.setOrientation(LinearLayout.VERTICAL);

        LinearLayout fKrediLayout = new LinearLayout(getContext());
        fKrediLayout.setOrientation(LinearLayout.HORIZONTAL);
        fKrediLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        TextView sumOfKredi = makeTextView(textSize,params6F,"Toplam Alınan Kredi/AKTS",
                "l-blue");

        TextView fKredi = makeTextView(textSize,params1F,tGTUKredi,
                "l-blue");

        TextView fAKTS = makeTextView(textSize,params1F,tAKTSKredi,
                "l-blue");

        fKrediLayout.addView(sumOfKredi);
        fKrediLayout.addView(fKredi);
        fKrediLayout.addView(fAKTS);

        LinearLayout bottomHead = new LinearLayout(getContext());
        bottomHead.setOrientation(LinearLayout.HORIZONTAL);
        bottomHead.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        TextView TermAKTS = makeTextView(textSize,params1F,"Dönemlik AKTS",
                "l-blue");
        TextView GeneralAKTS = makeTextView(textSize,params1F,"Genel AKTS",
                "l-blue");
        TextView Conditional = makeTextView(textSize,params1F,"Dönemlik Koşullu",
                "l-blue");
        TextView Term = makeTextView(textSize,params1F,"Dönemlik",
                "l-blue");
        TextView General = makeTextView(textSize,params1F,"Genel",
                "l-blue");

        bottomHead.addView(TermAKTS);
        bottomHead.addView(GeneralAKTS);
        bottomHead.addView(Conditional);
        bottomHead.addView(Term);
        bottomHead.addView(General);

        LinearLayout Averages = new LinearLayout(getContext());
        bottomHead.setOrientation(LinearLayout.HORIZONTAL);
        bottomHead.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        TextView TermAKTSAverages = makeTextView(textSize,params1F,Average1,
                "white");
        TextView GeneralAKTSAverages = makeTextView(textSize,params1F,Average2,
                "white");
        TextView ConditionalAverages = makeTextView(textSize,params1F,Average3,
                "white");
        TextView TermAverages = makeTextView(textSize,params1F,Average4,
                "white");
        TextView GeneralAverages = makeTextView(textSize,params1F,Average5,
                "white");

        Averages.addView(TermAKTSAverages);
        Averages.addView(GeneralAKTSAverages);
        Averages.addView(ConditionalAverages);
        Averages.addView(TermAverages);
        Averages.addView(GeneralAverages);

        foot.addView(fKrediLayout);
        foot.addView(bottomHead);
        foot.addView(Averages);
        return foot;
    }
    private TextView makeTitle(String title){

        TextView termName = new TextView(getContext());
        int TextSize = 15;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams paramsC = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        paramsC.setMargins(0, 10, 0, 10);
        params.setMargins(0,5,0,5);

        termName.setText(title);
        termName.setTextSize(TextSize);
        termName.setLayoutParams(paramsC);
        termName.setBackground(getResources().getDrawable(R.drawable.text_view_custom_blue));
        termName.setGravity(Gravity.CENTER);
        termName.setTextColor(getResources().getColor(R.color.mainColor));

        return termName;
    }
    private LinearLayout makeHead(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,1f);
        int size = 12;
        LinearLayout headLinearLayout = new LinearLayout(getContext());

        headLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        headLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView CourseCode = makeTextView(size,params,"Ders Kodu","l-blue");

        TextView CourseName = makeTextView(size,new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,3f),"Ders Adı","l-blue");

        TextView CourseType = makeTextView(size,params,"Ders Tipi","l-blue");

        TextView puan = makeTextView(size,params,"Not","l-blue");

        TextView gCredi = makeTextView(size,params,"Kredi","l-blue");

        TextView AKTSCredi = makeTextView(size,params,"AKTS","l-blue");

        headLinearLayout.addView(CourseCode);
        headLinearLayout.addView(CourseName);
        headLinearLayout.addView(CourseType);
        headLinearLayout.addView(puan);
        headLinearLayout.addView(gCredi);
        headLinearLayout.addView(AKTSCredi);

        return headLinearLayout;
    }
    private LinearLayout makeLinearLayout(String code,String name,String type,String note,
                                          String kredi,String AKTS){

        LinearLayout.LayoutParams params1F = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,1f);
        LinearLayout.LayoutParams params3F = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,3f);

        LinearLayout linearLayout = new LinearLayout(getContext());

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView cCode = makeTextView(12,params1F,code,"white");
        TextView cName = makeTextView(12,params3F,name,"white");
        String str;
        if(type.equals("Zorunlu"))
            str = "Z";
        else if (type.equals("Seçmeli"))
            str = "S";
        else
            str = "UK";
        TextView cType = makeTextView(12,params1F,str,"white");
        TextView cNote = makeTextView(12,params1F,note,"white");
        TextView cKredi = makeTextView(12,params1F,kredi,"white");
        TextView cAKTS = makeTextView(12,params1F,AKTS,"white");

        linearLayout.addView(cCode);
        linearLayout.addView(cName);
        linearLayout.addView(cType);
        linearLayout.addView(cNote);
        linearLayout.addView(cKredi);
        linearLayout.addView(cAKTS);

        return linearLayout;
    }
    private TextView makeTextView(int textSize,LinearLayout.LayoutParams params,String text,
                                  String customView){
        TextView textView = new TextView(getContext());

        textView.setTextSize(textSize);
        textView.setText(text);
        textView.setLayoutParams(params);
        textView.setTextColor(getResources().getColor(R.color.mainColor));
        textView.setGravity(Gravity.CENTER);
        if(customView.equals("white"))
            textView.setBackground(getResources().getDrawable(R.drawable.text_view_custom_white));
        else if(customView.equals("blue"))
            textView.setBackground(getResources().getDrawable(R.drawable.text_view_custom_blue));
        else
            textView.setBackground(getResources().getDrawable(R.drawable.text_view_custom_light_blue));

        return textView;
    }
}
