package com.gtu.yunus.kampus.apolloFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtu.yunus.kampus.InformationSingleton;
import com.gtu.yunus.kampus.Models.HomePage;
import com.gtu.yunus.kampus.R;

import java.util.ArrayList;

public class ApolloHome extends Fragment {
    private View view;
    private int TextSize = 20;

    public ApolloHome(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ap_home, container, false);

        initialUI();

        return view;
    }
    private void initialUI(){
        LinearLayout linearLayout = view.findViewById(R.id.home_page_linearLayout);

        LinearLayout.LayoutParams paramsC = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        if (InformationSingleton.getInstance().getHomePage() != null) {
            HomePage homePage = InformationSingleton.getInstance().getHomePage();
            ArrayList<String> homePageInformation = homePage.getHomePageInformation();
            for(int i=0;i<homePageInformation.size();++i){
                linearLayout.addView(makeTextView(TextSize,homePageInformation.get(i),paramsC));
            }

        }else{
            TextView somethingIsWrong = makeTextView(30,"Something is wrong..!",paramsC);
            linearLayout.addView(somethingIsWrong);
        }
    }
    private TextView makeTextView(int textSize,String text,LinearLayout.LayoutParams params){

        TextView textView = new TextView(getContext());

        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setLayoutParams(params);
        textView.setBackground(getResources().getDrawable(R.drawable.text_view_custom_white));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.mainColor));

        return textView;
    }
}
