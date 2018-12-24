package com.gtu.yunus.kampus.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gtu.yunus.kampus.Advertisement.Advertsement;
import com.gtu.yunus.kampus.R;

import java.util.List;

public class AdvertListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Advertsement> mAdvertsement;


    public AdvertListAdapter(Context mContext, List<Advertsement> mAdvertsement) {
        this.mContext = mContext;
        this.mAdvertsement = mAdvertsement;
    }

    @Override
    public int getCount() {
        return mAdvertsement.size();
    }

    @Override
    public Object getItem(int position) {
        return mAdvertsement.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.advertisement_list_item, null);
        TextView dayWithNumber = v.findViewById(R.id.day_with_number);
        TextView day = v.findViewById(R.id.day);
        TextView year = v.findViewById(R.id.year);
        TextView month = v.findViewById(R.id.month);
        TextView title = v.findViewById(R.id.title);
        TextView place = v.findViewById(R.id.place);

        //Set text for TextView
        dayWithNumber.setText(mAdvertsement.get(position).getStartDate().getDayWithNumber());
        day.setText(mAdvertsement.get(position).getStartDate().getDay());
        title.setText(mAdvertsement.get(position).getTitle());
        year.setText(mAdvertsement.get(position).getStartDate().getYear());
        month.setText(mAdvertsement.get(position).getStartDate().getMonth());
        place.setText(mAdvertsement.get(position).getPlace());

        v.setTag(position);
        return v;
    }
}
