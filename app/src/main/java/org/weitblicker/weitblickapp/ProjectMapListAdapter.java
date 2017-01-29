package org.weitblicker.weitblickapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectMapListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Project> projects;
    private static FontAwesomeDrawable.FontAwesomeDrawableBuilder iconBuilder = null;

    private OnItemClicked onItemClickedListener;
    private OnDetailIconClicked onDetailIconClickedListener;
    int selectedPosition = -1;


    public ProjectMapListAdapter(Context context, ArrayList<Project> projects){
        this.context = context;
        this.projects = projects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        createChevronRightSingelton();
    }

    public interface OnItemClicked{
        public void onClick(int position);
    }

    public void setOnItemClickedListener(OnItemClicked listener){
        onItemClickedListener = listener;
    }

    public interface OnDetailIconClicked{
        public void onClick(int position);
    }

    public void setOnDetailIconClickedListener(OnDetailIconClicked listener){
        onDetailIconClickedListener = listener;
    }


    private FontAwesomeDrawable.FontAwesomeDrawableBuilder createChevronRightSingelton(){
        if(iconBuilder == null) {
            FontAwesomeDrawable.FontAwesomeDrawableBuilder builder
                    = new FontAwesomeDrawable.FontAwesomeDrawableBuilder(context);
            builder.setColor(R.color.wb_green);
            builder.setSize(22);
            iconBuilder = builder;
        }
        return iconBuilder;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // later return project id
    }

    public void select(int position){
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get view for row item
        View rowView = inflater.inflate(R.layout.list_item_project_map, parent, false);

        if(selectedPosition == position){
            rowView.setSelected(true);
            rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.wb_green));
        }

        // name
        TextView nameTextView =
                (TextView) rowView.findViewById(R.id.list_item_project_map_name); // plus possibly location -> 'Title, Location' as caption


        // info
        TextView hostNameTextView =
                (TextView) rowView.findViewById(R.id.host_name);


        // image
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.list_item_project_map_image);



        // button
        ImageButton button =
                (ImageButton) rowView.findViewById(R.id.list_item_project_map_button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDetailIconClickedListener != null){
                    onDetailIconClickedListener.onClick(position);
                }
            }
        });

        button.setImageDrawable(iconBuilder.build(R.string.fa_chevron_circle_right));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickedListener != null){
                    onItemClickedListener.onClick(position);
                }
            }
        });

        if(selectedPosition == position){
            hostNameTextView.setTextColor(ContextCompat.getColor(context, R.color.wb_darkgrey));
            nameTextView.setTextColor(ContextCompat.getColor(context, R.color.wb_darkgrey));
            button.setImageDrawable(iconBuilder.build(R.string.fa_chevron_circle_right, R.color.wb_darkgrey));
        }



        Project project = (Project) getItem(position);

        nameTextView.setText(project.getName());
        hostNameTextView.setText(project.getHostNames());


        if(project.hasImage()) {
            Picasso.with(context)
                    .load(project.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }


        return rowView;
    }

}
