package com.gtu.yunus.kampus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gtu.yunus.kampus.Adapters.ViewPagerAdapter;
import com.gtu.yunus.kampus.apolloFragments.ApolloHome;
import com.gtu.yunus.kampus.apolloFragments.SelectedCourse;
import com.gtu.yunus.kampus.apolloFragments.Transcript;

public class apolloSystem extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apollo_system);

        initialUI();
    }
    private void initialUI(){
        tabLayout = findViewById(R.id.tab_layout_id);
        appBarLayout = findViewById(R.id.appbarid);
        viewPager = findViewById(R.id.gtu_view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new ApolloHome(),"Anasayfa");
        viewPagerAdapter.AddFragment(new SelectedCourse(),"Seçilen Dersler");
        viewPagerAdapter.AddFragment(new Transcript(),"Transcript");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if(!InformationSingleton.getInstance().isRememberMe()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                if (!isFinishing()){
                    createDialog();
                }
                }
            });
        }
        else{
            Intent ıntent = new Intent(apolloSystem.this, home.class);
            ıntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ıntent);
            finish();
            super.onBackPressed();
        }
    }
    private void createDialog(){
        new AlertDialog.Builder(apolloSystem.this)
            .setTitle("")
            .setMessage("Çıkış yapmak istediğinize emin misiniz? ")
            .setCancelable(false)
            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent ıntent = new Intent(apolloSystem.this, home.class);
                    ıntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ıntent);
                    finish();
                    apolloSystem.super.onBackPressed();
                }
            })
            .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
        .show();
    }
}
