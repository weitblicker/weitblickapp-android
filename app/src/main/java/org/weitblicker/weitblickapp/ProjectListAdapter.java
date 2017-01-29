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

import java.net.MalformedURLException;
import java.util.ArrayList;

public class ProjectListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Project> mDataSource;

    public ProjectListAdapter(Context context, ArrayList<Project> items){
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
        View rowView = mInflater.inflate(R.layout.list_item_project, parent, false);

        // caption
        TextView captionTextView =
                (TextView) rowView.findViewById(R.id.list_item_project_title); // plus possibly location -> 'Title, Location' as caption

        // abstract
        TextView abstractTextView =
                (TextView) rowView.findViewById(R.id.list_item_project_abstract);

        // host
        TextView hostTextView =
                (TextView) rowView.findViewById(R.id.list_item_project_host);

        // image
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.list_item_project_image);

        Project project = (Project) getItem(position);

        captionTextView.setText(project.getName());
        abstractTextView.setText(project.getAbstract());
        hostTextView.setText(project.getHostNames());

        Log.i("test", project.getImageUrl());
        if(project.hasImage()) {
            Picasso.with(mContext)
                    .load(project.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }

        return rowView;
    }

}
