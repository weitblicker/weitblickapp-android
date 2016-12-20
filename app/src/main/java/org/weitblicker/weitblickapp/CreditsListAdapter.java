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

import java.util.ArrayList;
import java.util.HashMap;

public class CreditsListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Credits> mDataSource;

    public CreditsListAdapter(Context context, ArrayList<Credits> items){
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
        return position; // later return project id
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_credits, parent, false);

        // info
        TextView infoTextView =
                (TextView) rowView.findViewById(R.id.list_item_credits_info);

        // name
        TextView nameTextView =
                (TextView) rowView.findViewById(R.id.list_item_credits_name);

        // image
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.list_item_credits_image);

        Credits credits = (Credits) getItem(position);

        String name = credits.name != null? credits.name : credits.login;

        nameTextView.setText(name);

        String info = "";

        if(credits.linesOfCodeWeitblickAppAndroid != 0){
            info += "\n" + credits.linesOfCodeWeitblickAppAndroid + " lines of code" +
                    " - Android App";
        }

        if(credits.linesOfCodeWeitblickAppServer != 0){
            info += "\n" + credits.linesOfCodeWeitblickAppServer + " lines of code" +
                    " - Server";
        }


        if(credits.commitsWeitblickAppAndroid != 0){
            info += "\n" + credits.commitsWeitblickAppAndroid + " Commit" +
                    (credits.commitsWeitblickAppAndroid > 1 ? "s" : "") +
                    " - Android App";
        }

        if(credits.commitsWeitblickAppServer != 0){
            info += "\n" + credits.commitsWeitblickAppServer + " Commit" +
                    (credits.commitsWeitblickAppServer > 1 ? "s" : "") +
                    " - Weitblick Server";
        }

        if(credits.location != null){
            info += "\n" + credits.location;
        }

        if(credits.bio != null){
            info += "\n" + credits.bio;
        }

        infoTextView.setText(info);

        Picasso.with(mContext)
            .load(credits.imageUrl)
            .fit()
            .centerCrop()
            .into(imageView);

        return rowView;
    }

}
