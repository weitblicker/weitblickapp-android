package org.weitblicker.weitblickapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MeetInfoListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<MeetInfo> mDataSource;

    public MeetInfoListAdapter(Context context, ArrayList<MeetInfo> items){
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // later return meetinfo id
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_meetinfo, parent, false);

        // caption
        TextView captionTextView =
                (TextView) rowView.findViewById(R.id.list_item_meetinfo_title); // plus possibly location -> 'Title, Location' as caption

        // abstract
        TextView abstractTextView =
                (TextView) rowView.findViewById(R.id.list_item_meetinfo_abstract);

        // host
        TextView hostTextView =
                (TextView) rowView.findViewById(R.id.list_item_meetinfo_host);

        // host
        TextView dateTextView =
                (TextView) rowView.findViewById(R.id.list_item_meetinfo_datetime);

        // image
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.list_item_meetinfo_image);

        MeetInfo meetInfo = (MeetInfo) getItem(position);

        captionTextView.setText(meetInfo.getName());
        abstractTextView.setText(meetInfo.getAbstract());
        hostTextView.setText(meetInfo.getHostName());
        // TODO check year

        DateFormat dateFormat;
        Date now = new Date();
        if(now.getYear() != meetInfo.getDateTime().getYear()) {
            dateFormat = new SimpleDateFormat("E dd.MM.yy - H:mm", Locale.GERMAN);
        }else{
            dateFormat = new SimpleDateFormat("E dd. MMMM - H:mm", Locale.GERMAN);
        }
        String datetime = dateFormat.format(meetInfo.getDateTime());
        System.out.println(meetInfo.getDateTime().getDay());
        dateTextView.setText(datetime);

        Log.i("image url", meetInfo.getImageUrl());
        if(meetInfo.hasImage()) {
            Picasso.with(mContext)
                    .load(meetInfo.getImageUrl())
                    .resize(800, 300)
                    .centerCrop()
                    .into(imageView);
        }
        return rowView;
    }

}
