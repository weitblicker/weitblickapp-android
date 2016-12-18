package org.weitblicker.weitblickapp;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectListFragment extends ListFragment {
    ArrayList<Project> projects = new ArrayList<Project>();
    Context context;
    OnProjectSelectListener onProjectSelectInterface;
    ProjectListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        loadProjects();

        setListAdapter(adapter);

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

    private void loadProjects(){

        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.start();
        String url = "http://10.0.2.2:8180/rest/project/list/en";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            Project project = new Project   ();
                            try {
                                JSONObject object = response.getJSONObject(i);

                                String name = object.getString("name");
                                String desc = object.getString("desc");
                                String abst = object.getString("abst");
                                JSONObject location = object.getJSONObject("location");
                                JSONArray imageList = object.getJSONArray("images");

                                // take the first image for now. TODO build a slider...
                                if(imageList.length() > 0) {
                                    JSONObject image = imageList.getJSONObject(0);
                                    String url = image.getString("uri").replace("localhost", "10.0.2.2");
                                    project.setImageUrl(url);
                                }
                                double longitude = location.getDouble("longitude");
                                double latitude = location.getDouble("latitude");

                                project.setAbstract(abst);
                                project.setDescription(desc);
                                project.setName(name);
                                project.setLocation((float) longitude, (float) latitude);
                                Log.i("debug","Added Project: " + name);
                                System.out.println("Added Project: " + name);
                                projects.add(project);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });

        queue.add(jsObjRequest);

    }
}
