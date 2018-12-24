package com.gtu.yunus.kampus.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gtu.yunus.kampus.R;

import java.util.List;

public class AnnouncementListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mAnnouncements;

    //Constructor

    public AnnouncementListAdapter(Context mContext, List<String> mAnnouncements) {
        this.mContext = mContext;
        this.mAnnouncements = mAnnouncements;
    }

    @Override
    public int getCount() {
        return mAnnouncements.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnnouncements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.announcement_list_item, null);
        TextView title = (TextView)v.findViewById(R.id.title_announcement);
        TextView show = (TextView)v.findViewById(R.id.show_announcement);

        //Set text for TextView
        title.setText(mAnnouncements.get(position));
        if(mAnnouncements.get(position).equals("Bir sorun oluştu. Tekrar deneyiniz."))
            show.setText("");
        else
            show.setText(R.string.show);

        //Save product id to tag
        v.setTag(position);

        return v;
    }
}
