package org.weitblicker.weitblickapp;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CreditsListFragment extends ListFragment {
    ArrayList<Credits> credits = new ArrayList<>();
    HashMap<String, Credits> creditsMap = new HashMap<>();
    Context context;
    CreditsListAdapter adapter;
    RequestQueue queue;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    @Override
    public void onStart(){
        super.onStart();
        queue.start();
    }

    @Override
    public void onStop(){
        super.onStop();
        queue.stop();
    }

    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        adapter = new CreditsListAdapter(getActivity(), credits);
        loadCredits();

        setListAdapter(adapter);

        // disables the divider
        getListView().setDividerHeight(0);
    }

    private void loadCredits(){

        // TODO replace with the server address
        String gitHubApiWeitblickAppAndroidUrl = "https://api.github.com/repos/weitblicker/weitblickapp-android/stats/contributors";
        String gitHubApiWeitblickAppServerUrl = "https://api.github.com/repos/weitblicker/weitblickapp-server/stats/contributors";

        credits.clear();

        JsonArrayRequest jsObjRequestWeitblickAppAndroid = new JsonArrayRequest
                (Request.Method.GET, gitHubApiWeitblickAppAndroidUrl, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println("response...");

                        // TODO check whether an update is necessary or not
                        // update list of credits -> clear all existing

                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);

                                int commits = object.getInt("total");
                                JSONObject author = object.getJSONObject("author");
                                String login = author.getString("login");
                                String imageUrl = author.getString("avatar_url");

                                System.out.println("login: " + login);

                                JSONArray weeks = object.getJSONArray("weeks");

                                int additions = 0;
                                int deletions = 0;

                                for(int j=0; j<weeks.length(); j++){
                                    JSONObject week = weeks.getJSONObject(j);
                                    additions += week.getInt("a");
                                    deletions += week.getInt("d");
                                }

                                int linesOfCode = additions - deletions;

                                Credits creditsItem = null;
                                if((creditsItem = creditsMap.get(login)) == null ){
                                    creditsItem = new Credits();
                                    creditsItem.login = login;
                                    creditsMap.put(login, creditsItem);
                                    credits.add(creditsItem);
                                }

                                creditsItem.commitsWeitblickAppAndroid = commits;
                                creditsItem.imageUrl = imageUrl;
                                creditsItem.linesOfCodeWeitblickAppAndroid = linesOfCode;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error response...");

                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });

        queue.add(jsObjRequestWeitblickAppAndroid);


        JsonArrayRequest jsObjRequestWeitblickAppServer = new JsonArrayRequest
                (Request.Method.GET, gitHubApiWeitblickAppServerUrl, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println("response...");

                        // TODO check whether an update is necessary or not
                        // update list of credits -> clear all existing

                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int commits = object.getInt("total");
                                JSONObject author = object.getJSONObject("author");
                                String login = author.getString("login");
                                String imageUrl = author.getString("avatar_url");

                                JSONArray weeks = object.getJSONArray("weeks");

                                int additions = 0;
                                int deletions = 0;

                                for(int j=0; j<weeks.length(); j++){
                                    JSONObject week = weeks.getJSONObject(j);
                                    additions += week.getInt("a");
                                    deletions += week.getInt("d");
                                }

                                int linesOfCode = additions - deletions;


                                System.out.println("login: " + login);

                                Credits creditsItem = null;
                                if((creditsItem = creditsMap.get(login)) == null ){
                                    creditsItem = new Credits();
                                    creditsItem.login = login;
                                    creditsMap.put(login, creditsItem);
                                    credits.add(creditsItem);
                                }
                                creditsItem.commitsWeitblickAppServer = commits;
                                creditsItem.imageUrl = imageUrl;
                                creditsItem.linesOfCodeWeitblickAppServer = linesOfCode;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for(Credits creditsItem: credits){
                            loadUserInfo(creditsItem);
                        }

                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });


        queue.add(jsObjRequestWeitblickAppServer);

    }

    void loadUserInfo(final Credits creditsItem){

        // TODO replace with the server address
        String gitHubApiUsersUrl = "https://api.github.com/users/" + creditsItem.login;

        JsonObjectRequest jsObjRequestGitHubUser = new JsonObjectRequest
                (Request.Method.GET, gitHubApiUsersUrl, null, new Response.Listener<JSONObject>() {

                    Credits user = creditsItem;

                    @Override
                    public void onResponse(JSONObject userInfo) {

                        try {
                            String name = userInfo.getString("name");
                            String location = userInfo.getString("location");
                            String bio = userInfo.getString("bio");

                            if(!name.equals("null") && !name.equals("")){
                                user.name = name;
                            }

                            if(!location.equals("null") && !location.equals("")){
                                user.location = location;
                            }

                            if(!bio.equals("null") && !bio.equals("")){
                                user.bio = bio;
                            }


                            System.out.println("Name: " + name + " Bio: " + bio + " Location: " + location);
                            Collections.sort(credits);

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error response...");

                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });

        queue.add(jsObjRequestGitHubUser);
    }
}
