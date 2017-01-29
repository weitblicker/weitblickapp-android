package org.weitblicker.weitblickapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.handle;

public class ProjectFragment extends Fragment implements OnMapReadyCallback {

    private Project project;
    private ViewPager imageViewPager = null;
    static Gson gson = new Gson();
    GoogleMap map = null;

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
            }else{
                // TODO log this error
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

        TextView abstView = (TextView) view.findViewById(R.id.fragment_project_abstract);
        abstView.setText(project.getAbstract());

        TextView descriptionView = (TextView) view.findViewById(R.id.fragment_project_description);
        descriptionView.setText(project.getDescription());

        // Instantiate a ViewPager and a PagerAdapter.
        imageViewPager = (ViewPager) view.findViewById(R.id.project_image_slide_pager);
        final ImageSlidePagerAdapter imageViewPagerAdapter = new ImageSlidePagerAdapter(getActivity(), getFragmentManager(), project.getImages());
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

        MapView mapView = (MapView) view.findViewById(R.id.fragment_project_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        MapsInitializer.initialize(getActivity());

        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(false);

        LatLng latLng = project.getLocation().getLatLng();
        int mapZoom = project.getLocation().mapZoom;

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mapZoom);
        map.animateCamera(cameraUpdate);

        MarkerOptions options = new MarkerOptions();

        options.position(latLng);
        options.title(project.getName());
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.wb_location));
        map.addMarker( options );
    }
}
