package com.gtu.yunus.kampus.apolloFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gtu.yunus.kampus.InformationSingleton;
import com.gtu.yunus.kampus.Models.Course;
import com.gtu.yunus.kampus.Models.SelectedCoursePage;
import com.gtu.yunus.kampus.R;

import java.util.ArrayList;

public class SelectedCourse extends Fragment {
    private View view;
    private int textSize = 15;
    public SelectedCourse(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ap_selected_course, container, false);

            initialUI();
            return view;
    }

    private void initialUI(){
        LinearLayout sLinearLayout = view.findViewById(R.id.s_linear_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.gravity = Gravity.CENTER;

        if(InformationSingleton.getInstance().getSelectedCoursePage() != null) {
            SelectedCoursePage selectedCoursePage = InformationSingleton.getInstance().getSelectedCoursePage();

            //Top Panel
            TextView registration = view.findViewById(R.id.date_of_registration_2);
            TextView approvalDate = view.findViewById(R.id.approval_date_2);
            TextView approvalStatus = view.findViewById(R.id.approval_status_2);
            TextView explanation = view.findViewById(R.id.explanation_2);

            registration.setText(selectedCoursePage.getTopPanelInformations().get(0));
            approvalDate.setText(selectedCoursePage.getTopPanelInformations().get(1));
            approvalStatus.setText(selectedCoursePage.getTopPanelInformations().get(2));
            explanation.setText("");

            if (selectedCoursePage.getTopPanelInformations().get(2).equals("Onaylandı"))
                approvalStatus.setTextColor(getResources().getColor(R.color.green));
            else
                approvalStatus.setTextColor(getResources().getColor(R.color.red));
            //////////////////////////////////
            //Years and Terms
            Spinner spinnerYears = view.findViewById(R.id.s_years);
            Spinner spinnerTerms = view.findViewById(R.id.s_terms);

            String[] sYears = InformationSingleton.getInstance().getSelectedCoursePage().getYears().get(0).split(" ");
            String[] sTerms = InformationSingleton.getInstance().getSelectedCoursePage().getTerms().get(0).split(" ");

            ArrayAdapter<String> spinnerArrayAdapterY = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_items, sYears);
            spinnerArrayAdapterY.setDropDownViewResource(R.layout.spinner_items);
            spinnerYears.setAdapter(spinnerArrayAdapterY);

            ArrayAdapter<String> spinnerArrayAdapterT = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_items, sTerms);
            spinnerArrayAdapterT.setDropDownViewResource(R.layout.spinner_items);
            spinnerTerms.setAdapter(spinnerArrayAdapterT);
            ////////////////////
            //Courses
            LinearLayout.LayoutParams paramsC = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            paramsC.setMargins(0, 15, 0, 15);

            ArrayList<Course> courses = selectedCoursePage.getCourses();
            for (int k = 0; k < selectedCoursePage.getCourses().size(); ++k) {

                TextView courseName = makeTextView(textSize,courses.get(k).getName(),
                        paramsC,"blue");

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(paramsC);

                String [] parts = courses.get(k).getInformations().split(" ");
                TextView gruopName = makeTextView(textSize,parts[0] + " " + parts[1],
                        params,"white");

                TextView AKTS = makeTextView(textSize,parts[2] + " " + parts[3],
                        params,"white");

                TextView education = makeTextView(textSize,parts[4] + " " + parts[5],
                        params,"white");

                linearLayout.addView(gruopName);
                linearLayout.addView(AKTS);
                linearLayout.addView(education);

                String str = "";
                for (int i = 3; i < parts.length; ++i) {
                    if (parts[i].equals("Oran") || parts[i].equals("Çalışma") || parts[i].equals("Not") ||
                            parts[i].equals("Toplam")) {

                    } else {
                        str += parts[i] + " ";
                    }
                }
                TextView informations = makeTextView(textSize,str,paramsC,"white");

                sLinearLayout.addView(courseName);
                sLinearLayout.addView(linearLayout);
                sLinearLayout.addView(informations);

                sLinearLayout.addView(makeLine());
            }

        }
        else {
            TextView somethingIsWrong = makeTextView(30, "Something is wrong..!", params, "blue");
            sLinearLayout.addView(somethingIsWrong);
        }
    }
    private View makeLine(){
        View v = new View(getActivity());
        v.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                55
        ));

        v.setBackgroundColor(getResources().getColor(R.color.mainColor));
        return v;
    }
    private TextView makeTextView(int textSize, String text, LinearLayout.LayoutParams params, String DrawableResource){
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setLayoutParams(params);
        if(DrawableResource.equals("blue"))
            textView.setBackground(getResources().getDrawable(R.drawable.text_view_custom_blue));
        else
            textView.setBackground(getResources().getDrawable(R.drawable.text_view_custom_white));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.mainColor));
        return textView;
    }
}
