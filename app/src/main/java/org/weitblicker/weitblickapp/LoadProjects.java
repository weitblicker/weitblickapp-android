package org.weitblicker.weitblickapp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LoadProjects {

    public interface OnProjectsLoaded{
        public void projectsLoaded();
    }

    public static void loadProjects(final List<Project> projects, RequestQueue queue, final OnProjectsLoaded listener){

        String url = "https://weitblick-server.de/rest/project/list/en";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        // TODO check whether an update is necessary or not
                        // update list of projects -> clear all existing

                        for(int i=0; i<response.length(); i++){
                            Project project = new Project   ();
                            try {
                                JSONObject object = response.getJSONObject(i);

                                String name = object.getString("name").trim();
                                String desc = object.getString("desc").trim();
                                String abst = object.getString("abst").trim();
                                JSONObject location = object.getJSONObject("location");
                                JSONArray imageList = object.getJSONArray("images");

                                for(int j=0; j<imageList.length(); j++){
                                    JSONObject image = imageList.getJSONObject(j);
                                    String url = image.getString("uri");
                                    ImageInfo imageInfo = new ImageInfo();
                                    imageInfo.url = url;
                                    project.addImage(imageInfo);
                                }

                                Location locationObject = new Location();
                                locationObject.lat = location.getDouble("latitude");
                                locationObject.lng = location.getDouble("longitude");
                                locationObject.mapZoom = location.getInt("mapZoom");

                                JSONArray hosts = object.getJSONArray("hosts");
                                for(int j=0; j<hosts.length(); j++){
                                    JSONObject host = hosts.getJSONObject(j);
                                    String hostName = host.getString("name").trim();
                                    String hostEmail = host.getString("email").trim();
                                    project.addHost(hostName);
                                }

                                project.setAbstract(abst);
                                project.setDescription(desc);
                                project.setName(name);
                                project.setLocation(locationObject);

                                projects.add(project);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listener.projectsLoaded();
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
