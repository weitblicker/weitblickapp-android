package org.weitblicker.weitblickapp;

import android.content.Context;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectListFragment extends ListFragment {
    ArrayList<Project> projects = new ArrayList<Project>();
    OnProjectSelectListener onProjectSelectInterface;
    ProjectListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProjectSelectListener) {
            onProjectSelectInterface = (OnProjectSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProjectSelectListener");
        }
    }

    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        adapter = new ProjectListAdapter(getActivity(), projects);
        setListAdapter(adapter);

        LoadProjects.loadProjects(projects,
                NetworkHandling.getInstance(getActivity()).getRequestQueue(),
                new LoadProjects.OnProjectsLoaded() {
            @Override
            public void projectsLoaded() {
                adapter.notifyDataSetChanged();
            }
        });

        // disables the divider
        getListView().setDividerHeight(0);
    }

    public interface OnProjectSelectListener {
        void onProjectSelect(Project project);
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        onProjectSelectInterface.onProjectSelect(projects.get(position));
        Log.i("debug","Selected project at pos: " + position);

    }

}
