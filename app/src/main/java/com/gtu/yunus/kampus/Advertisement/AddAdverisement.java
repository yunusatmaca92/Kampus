package com.gtu.yunus.kampus.Advertisement;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gtu.yunus.kampus.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class AddAdverisement extends AppCompatActivity {

    private Dialog popUpCalendar;
    private Dialog popUpShow;
    private CalendarView calendarView;
    private TextView startDate;
    private TextView endDate;
    private EditText place;
    private EditText title;
    private EditText details;
    private Button cancel;
    private Button show;
    private Button choose;
    private static final int PICK_IMAGE = 100;
    private TextView posterText;
    private String startDay;
    private String endDay;
    private Uri imageUri;
    private Button createAdvert;
    private Advertsement advertsement;
    private Bitmap bitmapImage = null;
    private ProgressBar progCreate;

    private DatabaseReference databaseAdvertisement;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adverisement);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialUI();
    }

    private void initialUI(){

        popUpCalendar = new Dialog(this);
        popUpShow = new Dialog(this);

        popUpCalendar.setContentView(R.layout.pop_up_calendar);
        popUpShow.setContentView(R.layout.pop_up_show_advertisement);

        title = findViewById(R.id.title_advert);
        startDate = findViewById(R.id.start_date_advert);
        endDate = findViewById(R.id.end_date_advert);
        place = findViewById(R.id.place_advert);
        details = findViewById(R.id.details_advert);
        choose= findViewById(R.id.choose_image);
        cancel= findViewById(R.id.cancel_advert);
        show = findViewById(R.id.create_advert);
        posterText = findViewById(R.id.poster);

        calendarView = popUpCalendar.findViewById(R.id.calendar_advert);
        createAdvert = popUpShow.findViewById(R.id.show_create);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateClicked();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateClicked();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseClicked();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClicked();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClicked();
            }
        });
        createAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAdvertClicked();
            }
        });
    }

    private void createAdvertClicked() {
        progCreate = popUpShow.findViewById(R.id.progressBar_advert);
        createAdvert.setClickable(false);

        progCreate.setVisibility(View.VISIBLE);

        databaseAdvertisement = FirebaseDatabase.getInstance().getReference("Advertisement");

        final String id = databaseAdvertisement.push().getKey();
        advertsement.setId(id);

        mStorageRef = FirebaseStorage.getInstance().getReference("Posters").child(id);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        mStorageRef.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                databaseAdvertisement.child(id).setValue(advertsement);
                progCreate.setVisibility(View.INVISIBLE);
                Toast.makeText(AddAdverisement.this,"Etkinlik Oluşturuldu.", Toast.LENGTH_LONG).show();
                createAdvert.setClickable(true);
                popUpShow.dismiss();
                finish();
            }
        });


    }
    private void startDateClicked() {
        if(!popUpCalendar.isShowing())
            popUpCalendar.show();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                startDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                popUpCalendar.dismiss();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                String [] days = new DateFormatSymbols().getWeekdays();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                startDay = days[dayOfWeek];

            }
        });
    }
    private void endDateClicked() {
        if(!popUpCalendar.isShowing())
            popUpCalendar.show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                endDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                String [] days = new DateFormatSymbols().getWeekdays();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                endDay = days[dayOfWeek];
                popUpCalendar.dismiss();
            }
        });
    }
    private void chooseClicked() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }
    private void showClicked() {

        if(title.getText().toString().equals("") || startDate.getText().toString().equals("")  ||
                endDate.getText().toString().equals("") || place.getText().toString().equals("") ||
                details.getText().toString().equals("") || posterText.getText().toString().equals("")) {

            Toast.makeText(this,"Lütfen bütün alanları doldurun",Toast.LENGTH_LONG).show();
        }
        else {

            ImageView poster = popUpShow.findViewById(R.id.show_poster);
            TextView dayWithNumber = popUpShow.findViewById(R.id.show_day_with_number);
            TextView day = popUpShow.findViewById(R.id.show_day);
            TextView month = popUpShow.findViewById(R.id.show_month);
            TextView year = popUpShow.findViewById(R.id.show_year);
            TextView title = popUpShow.findViewById(R.id.show_title);
            TextView place = popUpShow.findViewById(R.id.show_place);
            TextView details = popUpShow.findViewById(R.id.show_detail);

            details.setText(this.details.getText().toString());
            place.setText(this.place.getText().toString());
            title.setText(this.title.getText().toString());
            String [] partOfStartDate = startDate.getText().toString().split("/");
            year.setText(partOfStartDate[2]);
            month.setText(returnMonth(Integer.parseInt(partOfStartDate[1])));
            if(partOfStartDate[0].length() == 1)
                dayWithNumber.setText("0" + partOfStartDate[0]);
            else
                dayWithNumber.setText(partOfStartDate[0]);

            poster.setImageURI(imageUri);
            day.setText(returnDay(this.startDay));

            String [] partOfEndDate = endDate.getText().toString().split("/");
            advertsement = new Advertsement(title.getText().toString()
                    ,new CalendarAd(day.getText().toString(),dayWithNumber.getText().toString(),
                    month.getText().toString(),year.getText().toString()),new CalendarAd(returnDay(endDay),
                    partOfEndDate[0],partOfEndDate[1],partOfEndDate[2]),place.getText().toString(),
                    details.getText().toString(),"");
            popUpShow.show();
        }
    }

    private void cancelClicked() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();

            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] projection = { MediaStore.Images.Media.DATA };
            @SuppressWarnings("deprecation")
            Cursor cursor = this.managedQuery(imageUri, projection, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String realPath = cursor.getString(column_index);

            String [] partOfRealPath = realPath.split("/");
            posterText.setText(partOfRealPath[partOfRealPath.length-1]);

            if(bitmapImage == null)
                Log.d("IF--GAL", "null");
            else
                Log.d("IF--GAL", "nullDEĞİLLLLL");
        }
        else {
            //Log.d("ELSE--GAL","null");
        }
    }
    private String returnDay(String day) {
        day = day.toUpperCase();
        String returnVal;
        switch (day) {
            case "MONDAY":
            case "PAZARTESİ":
                returnVal = CalendarAd.MONDAY;
                break;
            case "TUESDAY":
            case "SALI":
                returnVal = CalendarAd.TUESDAY;
                break;
            case "WEDNESDAY":
            case "ÇARŞAMBA":
                returnVal = CalendarAd.WEDNESDAY;
                break;
            case "THURSDAY":
            case "PERŞEMBE":
                returnVal = CalendarAd.THURSDAY;
                break;
            case "FRIDAY":
            case "CUMA":
                returnVal = CalendarAd.FRIDAY;
                break;
            case "SATURDAY":
            case "CUMARTESİ":
                returnVal = CalendarAd.SATURDAY;
                break;
            case "SUNDAY":
            case "PAZAR":
                returnVal = CalendarAd.SUNDAY;
                break;
            default:
                returnVal = "";

        }
        return returnVal;
    }
    private String returnMonth(int month){
        String returnVal;
        switch (month){
            case 1:
                returnVal = CalendarAd.JANUARY;
                break;
            case 2:
                returnVal = CalendarAd.FEBRUARY;
                break;
            case 3:
                returnVal = CalendarAd.MARCH;
                break;
            case 4:
                returnVal = CalendarAd.APRIL;
                break;
            case 5:
                returnVal = CalendarAd.MAY;
                break;
            case 6:
                returnVal = CalendarAd.JUNE;
                break;
            case 7:
                returnVal = CalendarAd.JULY;
                break;
            case 8:
                returnVal = CalendarAd.AUGUST;
                break;
            case 9:
                returnVal = CalendarAd.SEPTEMBER;
                break;
            case 10:
                returnVal = CalendarAd.OCTOBER;
                break;
            case 11:
                returnVal = CalendarAd.NOVEMBER;
                break;
            case 12:
                returnVal = CalendarAd.DECEMBER;
                break;
            default:
                returnVal = "";
        }

        return returnVal;
    }
}
