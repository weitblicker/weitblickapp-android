package org.weitblicker.weitblickapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ProjectFragment extends Fragment {

    private Project project = null;
    private Context context = null;
    static Gson gson = new Gson();

    final static String PROJECT_BUNDLE_KEY = "JSON_PROJECT";

    public ProjectFragment() {
        super();
    }

    public static ProjectFragment newInstance(Project project) {
        String projectJson = gson.toJson(project);
        ProjectFragment fragment = new ProjectFragment();

        Bundle bundle = new Bundle();
        bundle.putString(PROJECT_BUNDLE_KEY, projectJson);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String projectJson = bundle.getString(PROJECT_BUNDLE_KEY);
            if(projectJson != null){
                this.project = gson.fromJson(projectJson, Project.class);
                Log.i("debug", projectJson);
            }else{
                throw new RuntimeException("Bundle Arguments for "+ PROJECT_BUNDLE_KEY + " are null!");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.fragment_project_title);
        titleView.setText(project.getName());

        TextView descriptionView = (TextView) view.findViewById(R.id.fragment_project_abstract);
        descriptionView.setText(project.getDescription());

        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_project_image);

        Picasso.with(context)
            .load(project.getImageUrl())
            .resize(800, 300)
            .centerCrop()
            .into(imageView);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

}
