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
                (TextView) rowView.findViewById(R.id.project_list_title); // plus possibly location -> 'Title, Location' as caption

        // abstract
        TextView abstractTextView =
                (TextView) rowView.findViewById(R.id.project_list_abstract);

        // image
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.project_list_image);

        Project project = (Project) getItem(position);

        captionTextView.setText(project.getName());
        abstractTextView.setText(project.getAbstract());

        try {
            Picasso.with(mContext)
                    .load(project.getImageUrl().toString())
                    .resize(800, 300)
                    .centerCrop()
                    .into(imageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("error", "Can not load image via picasso!", e);
        }

        return rowView;
    }

}
