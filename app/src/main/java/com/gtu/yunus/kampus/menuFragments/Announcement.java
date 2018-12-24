package com.gtu.yunus.kampus.menuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.gtu.yunus.kampus.Adapters.ExpandableListAdapter;
import com.gtu.yunus.kampus.Announcement.generalAnnouncement;
import com.gtu.yunus.kampus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Announcement extends Fragment {
    private View view;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private int flagDAnn = 0;

    public Announcement(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_announcement, container, false);



        initData();
        listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHashMap);
        listView.setAdapter(listAdapter);

        Button gAnnouncement = view.findViewById(R.id.g_announcement);
        gAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getActivity(),generalAnnouncement.class);
                startActivity(ıntent);
            }
        });

        final Button dAnnouncement = view.findViewById(R.id.d_announcement);

        dAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagDAnn % 2 == 0){
                    dAnnouncement.setCompoundDrawablesWithIntrinsicBounds( 0, 0,
                            getResources().getIdentifier("@mipmap/arrow_up","mipmap",
                                    getActivity().getPackageName()), 0 );
                    listView.setVisibility(View.VISIBLE);
                }
                else {
                    dAnnouncement.setCompoundDrawablesWithIntrinsicBounds( 0, 0,
                            getResources().getIdentifier("@mipmap/arrow_down","mipmap",
                                    getActivity().getPackageName()), 0 );
                    listView.setVisibility(View.GONE);
                }
                ++flagDAnn;
            }
        });

        return view;
    }
    private void initData() {
        listView = view.findViewById(R.id.expanded_list_department_announcement);

        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Mühendislik Fakültesi");
        listDataHeader.add("Temel Bilimler Fakültesi");
        listDataHeader.add("İşletme Fakültesi");
        listDataHeader.add("Mimarlık Fakültesi");

        List<String> FacultyEn = new ArrayList<>();
        FacultyEn.add("Bilgisayar Mühendisliği");
        FacultyEn.add("Biyomühendislik");
        FacultyEn.add("Çevre Mühendisliği");
        FacultyEn.add("Endüstri Mühendisliği");
        FacultyEn.add("İnşaat Mühendisliği");
        FacultyEn.add("Harita Mühendisliği");
        FacultyEn.add("Kimya Mühendisliği");
        FacultyEn.add("Makine Mühendisliği");
        FacultyEn.add("Malzeme Bilimi ve Mühendisliği");

        List<String> FacultyTe = new ArrayList<>();
        FacultyTe.add("Fizik");
        FacultyTe.add("Kimya");
        FacultyTe.add("Matematik");
        FacultyTe.add("Moleküler Biyoloji ve Genetik");

        List<String> FacultyIs = new ArrayList<>();
        FacultyIs.add("İktisat");
        FacultyIs.add("İşletme");
        FacultyIs.add("Strateji Bilimi");

        List<String> FacultyMi = new ArrayList<>();
        FacultyMi.add("Mimarlık");
        FacultyMi.add("Şehir ve Bölge Planlama");
        FacultyMi.add("Endüstri Ürünleri Tasarımı");

        listHashMap.put(listDataHeader.get(0),FacultyEn);
        listHashMap.put(listDataHeader.get(1),FacultyTe);
        listHashMap.put(listDataHeader.get(2),FacultyIs);
        listHashMap.put(listDataHeader.get(3),FacultyMi);
    }
}
