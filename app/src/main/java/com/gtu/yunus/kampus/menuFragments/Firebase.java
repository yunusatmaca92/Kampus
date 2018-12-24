package com.gtu.yunus.kampus.menuFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gtu.yunus.kampus.Adapters.AdvertListAdapter;
import com.gtu.yunus.kampus.Advertisement.AddAdverisement;
import com.gtu.yunus.kampus.Advertisement.Advertsement;
import com.gtu.yunus.kampus.R;
import com.gtu.yunus.kampus.Advertisement.Images;

import java.util.ArrayList;
import java.util.List;

public class Firebase extends Fragment {
    private FloatingActionButton addAdvert;

    private ProgressBar progRef;
    private View view;
    private ListView listView;
    private List<Advertsement> listAdvert;
    private AdvertListAdapter adapter;
    private DatabaseReference databaseReference;
    private StorageReference mStorageRef;
    private ArrayList<Images> images;
    private Dialog popUpShow;

    public Firebase(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_firebase, container, false);


        initialUI();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Advertsement advertsement = listAdvert.get((Integer) view.getTag());
                popUpShow.setContentView(R.layout.pop_up_show_advertisement);

                ImageView poster = popUpShow.findViewById(R.id.show_poster);
                TextView dayWithNumber = popUpShow.findViewById(R.id.show_day_with_number);
                TextView month = popUpShow.findViewById(R.id.show_month);
                TextView day = popUpShow.findViewById(R.id.show_day);
                TextView year = popUpShow.findViewById(R.id.show_year);
                TextView title = popUpShow.findViewById(R.id.show_title);
                TextView place = popUpShow.findViewById(R.id.show_place);
                TextView details = popUpShow.findViewById(R.id.show_detail);
                Button create = popUpShow.findViewById(R.id.show_create);
                create.setVisibility(View.GONE);

                for(int i=0;i<images.size();++i)
                    if(advertsement.getId().equals(images.get(i).getId()))
                        poster.setImageBitmap(images.get(i).getImage());

                dayWithNumber.setText(advertsement.getStartDate().getDayWithNumber());
                month.setText(advertsement.getStartDate().getMonth());
                day.setText(advertsement.getStartDate().getDay());
                year.setText(advertsement.getStartDate().getYear());
                title.setText(advertsement.getTitle());
                place.setText(advertsement.getPlace());
                details.setText(advertsement.getDetails());
                popUpShow.show();
                //listViewItemClicked();
            }
        });

        return view;
    }

    private void initialUI() {
        addAdvert = view.findViewById(R.id.add_advertisement);
        addAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdvertClicked();
            }
        });
        progRef = view.findViewById(R.id.progressBar_refresh);
        listView = view.findViewById(R.id.list_links);
        listAdvert = new ArrayList<>();
        databaseReference =  FirebaseDatabase.getInstance().getReference("Advertisement");
        mStorageRef = FirebaseStorage.getInstance().getReference("Posters/");
        images = new ArrayList<>();
        popUpShow = new Dialog(getActivity());
    }
    private void addAdvertClicked() {
        Intent ıntent = new Intent(getActivity(), AddAdverisement.class);
        startActivity(ıntent);
    }
    /*private void listViewItemClicked(){
        Advertsement advertsement = listAdvert.get((Integer) view.getTag());
        popUpShow.setContentView(R.layout.pop_up_show_advertisement);

        ImageView poster = popUpShow.findViewById(R.id.show_poster);
        TextView dayWithNumber = popUpShow.findViewById(R.id.show_day_with_number);
        TextView month = popUpShow.findViewById(R.id.show_month);
        TextView day = popUpShow.findViewById(R.id.show_day);
        TextView year = popUpShow.findViewById(R.id.show_year);
        TextView title = popUpShow.findViewById(R.id.show_title);
        TextView place = popUpShow.findViewById(R.id.show_place);
        TextView details = popUpShow.findViewById(R.id.show_detail);
        Button create = popUpShow.findViewById(R.id.show_create);
        create.setVisibility(View.GONE);

        for(int i=0;i<images.size();++i)
            if(advertsement.getId().equals(images.get(i).getId()))
                poster.setImageBitmap(images.get(i).getImage());

        dayWithNumber.setText(advertsement.getStartDate().getDayWithNumber());
        month.setText(advertsement.getStartDate().getMonth());
        day.setText(advertsement.getStartDate().getDay());
        year.setText(advertsement.getStartDate().getYear());
        title.setText(advertsement.getTitle());
        place.setText(advertsement.getPlace());
        details.setText(advertsement.getDetails());
        popUpShow.show();
    }*/
    @Override
    public void onStart() {
        super.onStart();

        addAdvert .setClickable(false);
        progRef.setVisibility(View.VISIBLE);
        final long ONE_MEGABYTE = 2024 * 2024;

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAdvert.clear();
                images.clear();
                for(DataSnapshot advertDataSnapshot : dataSnapshot.getChildren()){
                    final Advertsement advertsement = advertDataSnapshot.getValue(Advertsement.class);

                    Log.d("Image-ID",advertsement.getId());

                    StorageReference islandRef = mStorageRef.child(advertsement.getId());

                    final Bitmap[] bitmap = new Bitmap[1];
                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            bitmap[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            if(bitmap[0] == null)
                                Log.d("NullImage","NullImage");
                            else
                                Log.d("NullDeğil","NullDeğil");

                            images.add(new Images(advertsement.getId(), bitmap[0]));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("FAILEDDDD--",e.getMessage());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                        @Override
                        public void onComplete(@NonNull Task<byte[]> task) {
                            adapter = new AdvertListAdapter(getContext(),listAdvert);
                            listView.setAdapter(adapter);

                            addAdvert.setClickable(true);
                            progRef.setVisibility(View.INVISIBLE);
                        }
                    });

                    listAdvert.add(advertsement);
                }
                Log.d("listSIZE--",String.valueOf(listAdvert.size()));
                // mStorageRef.getBytes();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
