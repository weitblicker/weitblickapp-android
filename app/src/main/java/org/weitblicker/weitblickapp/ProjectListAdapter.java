package org.weitblicker.weitblickapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Project} and makes a call to the
 * specified {@link org.weitblicker.weitblickapp.ProjectListFragment.OnProjectSelectionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private final List<Project> projects;
    private final ProjectListFragment.OnProjectSelectionListener mListener;

    public ProjectListAdapter(List<Project> projects, ProjectListFragment.OnProjectSelectionListener listener) {
        this.projects = projects;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Project project = projects.get(position);
        holder.project = project;
        holder.captionTextView.setText(project.getName());
        holder.abstractTextView.setText(project.getAbstract());

        //ToDo
        //holder.thumbnailImageView.setImageBitmap();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onProjectSelected(project);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView captionTextView;
        public final TextView abstractTextView;
        public final ImageView thumbnailImageView;
        public Project project;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            captionTextView = (TextView) view.findViewById(org.weitblicker.weitblickapp.R.id.project_list_title);
            abstractTextView = (TextView) view.findViewById(R.id.project_list_abstract);
            thumbnailImageView = (ImageView) view.findViewById(R.id.project_list_thumbnail);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + captionTextView.getText() + "'";
        }
    }
}
