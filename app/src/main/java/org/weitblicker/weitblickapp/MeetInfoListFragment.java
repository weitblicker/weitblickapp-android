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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MeetInfoListFragment extends ListFragment {
    ArrayList<MeetInfo> meetInfos = new ArrayList<MeetInfo>();
    Context context;
    OnMeetInfoSelectListener onMeetInfoSelectInterface;
    MeetInfoListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnMeetInfoSelectListener) {
            onMeetInfoSelectInterface = (OnMeetInfoSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMeetInfoSelectListener");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        adapter = new MeetInfoListAdapter(getActivity(), meetInfos);
        loadMeetInfos();

        setListAdapter(adapter);

        // disables the divider
        getListView().setDividerHeight(0);
    }

    public interface OnMeetInfoSelectListener {
        void onMeetInfoSelect(MeetInfo meetInfo);
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        onMeetInfoSelectInterface.onMeetInfoSelect(meetInfos.get(position));
        Log.i("debug","Selected meet info at pos: " + position);

    }

    private void loadMeetInfos(){

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.start();

        String url = "https://weitblick-server.de/rest/meeting/list/en";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        // TODO check whether an update is necessary or not
                        // update list of meet infos -> clear all existing
                        meetInfos.clear();

                        for(int i=0; i<response.length(); i++){
                            MeetInfo meetInfo = new MeetInfo();
                            try {
                                JSONObject object = response.getJSONObject(i);

                                String name = object.getString("name");
                                String desc = object.getString("desc");
                                String abst = object.getString("abst");
                                JSONObject location = object.getJSONObject("location");
                                JSONArray imageList = object.getJSONArray("images");
                                JSONObject host = object.getJSONObject("host");
                                String hostName = host.getString("name");
                                String hostEmail = host.getString("email");
                                for(int j=0; j<imageList.length(); j++){
                                    JSONObject image = imageList.getJSONObject(j);
                                    String url = image.getString("uri");
                                    ImageInfo imageInfo = new ImageInfo();
                                    imageInfo.url = url;
                                    meetInfo.addImage(imageInfo);
                                }

                                double longitude = location.getDouble("longitude");
                                double latitude = location.getDouble("latitude");

                                meetInfo.setAbstract(abst);
                                meetInfo.setDescription(desc);
                                meetInfo.setName(name);
                                meetInfo.setLocation((float) longitude, (float) latitude);
                                meetInfo.setHostName(hostName);
                                meetInfo.setHostEmail(hostEmail);

                                meetInfos.add(meetInfo);
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
