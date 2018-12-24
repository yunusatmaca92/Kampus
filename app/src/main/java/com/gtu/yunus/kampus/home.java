package com.gtu.yunus.kampus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.gtu.yunus.kampus.menuFragments.Announcement;
import com.gtu.yunus.kampus.menuFragments.Calendar;
import com.gtu.yunus.kampus.menuFragments.DiningHall;
import com.gtu.yunus.kampus.menuFragments.Firebase;
import com.gtu.yunus.kampus.menuFragments.GTURadio;
import com.gtu.yunus.kampus.menuFragments.Home;

public class home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialUI();
    }

    private void initialUI() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Default Fragment
        FragmentTransaction homeFrag = getSupportFragmentManager().beginTransaction();
        homeFrag.add(R.id.login_frag, new Home(),"home");
        homeFrag.commit();
        setTitle("Anasayfa");

        //Action Bar
        toolbar = findViewById(R.id.home_action_bar);
        setSupportActionBar(toolbar);

        //draw layout toggle
        drawerLayout = findViewById(R.id.login_drawer_layout);
        dToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_next);

        navigationView.getMenu().getItem(0).setChecked(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpDrawerContent(navigationView);
    }
    private void setUpDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }
    public void selectItemDrawer(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass;

        FragmentManager fragmentManager = getSupportFragmentManager();


        switch (menuItem.getItemId()){
            case R.id.home:
                //if exist use it else add
                if(fragmentManager.findFragmentByTag("home") != null)
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("home")).commit();
                else
                    fragmentManager.beginTransaction().add(R.id.login_frag, new Home(),"home").commit();

                //Hide the others
                if(fragmentManager.findFragmentByTag("announcements") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("announcements")).commit();
                if(fragmentManager.findFragmentByTag("calendar") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();
                if(fragmentManager.findFragmentByTag("diningHall") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("diningHall")).commit();
                if(fragmentManager.findFragmentByTag("firebase") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("firebase")).commit();

                //fragmentClass = menuHome.class;
                break;
            case R.id.announcements:
                //if it exists ,use it else add
                if(fragmentManager.findFragmentByTag("announcements") != null)
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("announcements")).commit();
                else
                    fragmentManager.beginTransaction().add(R.id.login_frag, new Announcement(),"announcements").commit();

                //Hide the others
                if(fragmentManager.findFragmentByTag("home") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("home")).commit();
                if(fragmentManager.findFragmentByTag("calendar") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();
                if(fragmentManager.findFragmentByTag("diningHall") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("diningHall")).commit();
                if(fragmentManager.findFragmentByTag("firebase") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("firebase")).commit();
                //fragmentClass = menuAnnouncements.class;
                break;
            case R.id.radio:
                Intent ıntent = new Intent(home.this,GTURadio.class);
                startActivity(ıntent);
                break;
            case R.id.calendar:
                //if it exists ,use it else add
                if(fragmentManager.findFragmentByTag("calendar") != null)
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("calendar")).commit();
                else
                    fragmentManager.beginTransaction().add(R.id.login_frag, new Calendar(),"calendar").commit();

                //Hide the others
                if(fragmentManager.findFragmentByTag("announcements") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("announcements")).commit();
                if(fragmentManager.findFragmentByTag("home") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("home")).commit();
                if(fragmentManager.findFragmentByTag("diningHall") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("diningHall")).commit();
                if(fragmentManager.findFragmentByTag("firebase") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("firebase")).commit();
                //fragmentClass = menuCalendar.class;
                break;
            case R.id.diningHall:
                //if it exists ,use it else add
                if(fragmentManager.findFragmentByTag("diningHall") != null)
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("diningHall")).commit();
                else
                    fragmentManager.beginTransaction().add(R.id.login_frag, new DiningHall(),"diningHall").commit();

                //Hide the others
                if(fragmentManager.findFragmentByTag("announcements") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("announcements")).commit();
                if(fragmentManager.findFragmentByTag("home") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("home")).commit();
                if(fragmentManager.findFragmentByTag("calendar") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();
                if(fragmentManager.findFragmentByTag("firebase") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("firebase")).commit();
                //fragmentClass = menuDiningHall.class;
                break;
            case R.id.firebase:
                //if it exists ,use it else add
                if(fragmentManager.findFragmentByTag("firebase") != null)
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("firebase")).commit();
                else
                    fragmentManager.beginTransaction().add(R.id.login_frag, new Firebase(),"firebase").commit();

                //Hide the others
                if(fragmentManager.findFragmentByTag("announcements") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("announcements")).commit();
                if(fragmentManager.findFragmentByTag("home") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("home")).commit();
                if(fragmentManager.findFragmentByTag("calendar") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();
                if(fragmentManager.findFragmentByTag("diningHall") != null)
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("diningHall")).commit();
                //fragmentClass = menuLinks.class;
                break;
            default:
                fragmentClass = Home.class;
        }
        /*try{
            fragment =(Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }*/
       /* FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_frag,fragment);
        fragmentTransaction.commit();*/

        menuItem.setChecked(true);

        if(menuItem.getTitle().equals("Radyo")){
            String tag = fragmentManager.getFragments().get(fragmentManager.getFragments().size()-1).getTag();
            String title = "Anasayfa";

            if(tag.equals("firebase"))
                title = "Etkinlikler";
            else if(tag.equals("home"))
                title = "Anasayfa";
            else if(tag.equals("announcements"))
                title = "Duyurular";
            else if(tag.equals("diningHall"))
                title = "Yemekhane";
            else
                title = "Takvim";

            setTitle(title);
        }
        else
            setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }
    @Override
    public void onBackPressed(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.login_drawer_layout);

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()){
                        new AlertDialog.Builder(home.this)
                                .setTitle("")
                                .setMessage("Çıkış yapmak istediğinize emin misiniz?")
                                .setCancelable(false)
                                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        home.super.onBackPressed();
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
            });
            //super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
