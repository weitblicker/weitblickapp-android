package org.weitblicker.weitblickapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MeetInfoFragment extends Fragment implements OnMapReadyCallback {

    private MeetInfo meetInfo = null;
    private Context context = null;
    private ViewPager imageViewPager = null;
    static Gson gson = new Gson();
    GoogleMap map = null;

    final static String MEETINFO_BUNDLE_KEY = "JSON_MEETINFO";

    public MeetInfoFragment() {
        super();
    }

    public static MeetInfoFragment newInstance(MeetInfo meetInfo) {
        String meetinfoJson = gson.toJson(meetInfo);
        MeetInfoFragment fragment = new MeetInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(MEETINFO_BUNDLE_KEY, meetinfoJson);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String meetInfoJson = bundle.getString(MEETINFO_BUNDLE_KEY);
            if(meetInfoJson != null){
                this.meetInfo = gson.fromJson(meetInfoJson, MeetInfo.class);
            }else{
                // TODO log this error
                throw new RuntimeException("Bundle Arguments for "+ MEETINFO_BUNDLE_KEY + " are null!");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meetinfo, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.fragment_meetinfo_title);
        titleView.setText(meetInfo.getName());

        TextView abstView = (TextView) view.findViewById(R.id.fragment_meetinfo_abstract);
        abstView.setText(meetInfo.getAbstract());

        TextView descriptionView = (TextView) view.findViewById(R.id.fragment_meetinfo_description);
        descriptionView.setText(meetInfo.getDescription());

        // Instantiate a ViewPager and a PagerAdapter.
        imageViewPager = (ViewPager) view.findViewById(R.id.meetinfo_image_slide_pager);
        final ImageSlidePagerAdapter imageViewPagerAdapter = new ImageSlidePagerAdapter(context, getFragmentManager(), meetInfo.getImages());
        imageViewPager.setAdapter(imageViewPagerAdapter);


        // TODO integrate user interaction in auto slide
        final Handler handler = new Handler();
        Runnable task = new Runnable(){
            public void run(){

                // TODO disable when it is touched
                synchronized (imageViewPager) {
                    int currentItem = imageViewPager.getCurrentItem();
                    int count = imageViewPagerAdapter.getCount();
                    if (currentItem == count - 1) {
                        imageViewPager.setCurrentItem(0, true);
                    } else {
                        imageViewPager.setCurrentItem(currentItem + 1, true);
                    }
                }
                // TODO put mili seconds to options
                handler.postDelayed(this, 4000);
            }
        };

        task.run();

        MapView mapView = (MapView) view.findViewById(R.id.fragment_meetinfo_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        MapsInitializer.initialize(getActivity());

        mapView.getMapAsync(this);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(false);

        LatLng latLng = meetInfo.getLocation().getLatLng();
        int mapZoom = meetInfo.getLocation().mapZoom;

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mapZoom);
        map.animateCamera(cameraUpdate);

        MarkerOptions options = new MarkerOptions();

        options.position(latLng);
        options.title(meetInfo.getName());
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.wb_location));
        map.addMarker( options );
    }
}
