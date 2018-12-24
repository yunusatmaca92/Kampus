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

import com.gtu.yunus.kampus.Dining.GTUDiningHall;
import com.gtu.yunus.kampus.Dining.KelebekCafe;
import com.gtu.yunus.kampus.R;

public class DiningHall extends Fragment {

    private View view;

    public DiningHall(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_dining_hall, container, false);


        final Button dRoom = view.findViewById(R.id.d_room);
        dRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getActivity(), GTUDiningHall.class);
                startActivity(ıntent);
            }
        });

        final Button dRes = view.findViewById(R.id.d_res);
        dRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getActivity(), KelebekCafe.class);
                startActivity(ıntent);
            }
        });

        /*final Button card = view.findViewById(R.id.card_money);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getActivity(), CardMoney.class);
                startActivity(ıntent);
            }
        });*/

        return view;
    }
}
