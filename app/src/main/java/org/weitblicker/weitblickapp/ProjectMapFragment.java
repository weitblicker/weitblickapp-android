package org.weitblicker.weitblickapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectMapFragment extends Fragment implements OnMapReadyCallback {

    private ArrayList<Project> projects;
    private GoogleMap map;
    private OnFragmentInteractionListener listener;
    private ProjectMapListAdapter adapter;
    private ListView projectListView;

    boolean projectsLoaded;
    boolean mapLoaded;
    boolean markersLoaded;

    private HashMap<Marker, Integer> markerIndexMap = new HashMap<Marker, Integer>();
    private HashMap<Integer, Marker> indexMarkerMap = new HashMap<Integer, Marker>();

    ProjectListFragment.OnProjectSelectListener onProjectSelectListener = null;

    public ProjectMapFragment() {
        projectsLoaded = false;
        mapLoaded = false;
        markersLoaded = false;
    }

    public static ProjectMapFragment newInstance() {
        System.out.println("new instance");
        ProjectMapFragment fragment = new ProjectMapFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public void setOnProjectSelectListener(ProjectListFragment.OnProjectSelectListener onProjectSelectListener){
        this.onProjectSelectListener = onProjectSelectListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("onCreateView");

        mapLoaded = false;
        markersLoaded = false;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_map, container, false);

        MapView mapView = (MapView) view.findViewById(R.id.fragment_project_map_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(getActivity());
        mapView.getMapAsync(this);

        if(!projectsLoaded)
            loadProjects();

        projectListView = (ListView) view.findViewById(R.id.fragment_project_map_list);
        adapter = new ProjectMapListAdapter(getActivity(), projects);
        projectListView.setAdapter(adapter);
        projectListView.setDividerHeight(0);

        adapter.setOnItemClickedListener(
            new ProjectMapListAdapter.OnItemClicked(){
            @Override
                public void onClick(int position) {
                    Marker marker = indexMarkerMap.get(position);
                    CameraUpdate cu = CameraUpdateFactory.newLatLng(marker.getPosition());
                    map.animateCamera(cu);
                    adapter.select(position);
                    projectListView.smoothScrollToPosition(position);

            }
        });

        adapter.setOnDetailIconClickedListener(new ProjectMapListAdapter.OnDetailIconClicked() {
            @Override
            public void onClick(int position) {
                if(onProjectSelectListener != null){
                    onProjectSelectListener.onProjectSelect(projects.get(position));
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        projects = new ArrayList<Project>();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapLoaded = true;
                if(!markersLoaded && projectsLoaded) {
                    loadMarkers();
                }
            }
        });
    }

    private void loadProjects(){
        projects.clear();
        LoadProjects.loadProjects(projects, NetworkHandling.getInstance(getActivity()).getRequestQueue(), new LoadProjects.OnProjectsLoaded() {
            @Override
            public void projectsLoaded() {
                projectsLoaded = true;
                adapter.notifyDataSetChanged();
                if(mapLoaded && !markersLoaded){
                    loadMarkers();
                }

            }
        });
    }

    private void loadMarkers(){
        synchronized (this){
            if(markersLoaded) return;
            markersLoaded = true;
        }
        if(projects.isEmpty()) return;

        List<Marker> markers = new LinkedList<>();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.wb_location);

        int i=0;
        for(Project project: projects){
            LatLng postion = project.getLocation().getLatLng();
            String title = project.getName();
            MarkerOptions markerOptions =
                    new MarkerOptions()
                            .position(postion)
                            .draggable(false)
                            .icon(icon)
                            .title(title);
            Marker marker = map.addMarker(markerOptions);
            markers.add(marker);
            indexMarkerMap.put(i, marker);
            markerIndexMap.put(marker, i);
            i++;
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = markerIndexMap.get(marker);
                projectListView.setSelection(index);
                adapter.select(index);
                CameraUpdate cu = CameraUpdateFactory.newLatLng(marker.getPosition());
                map.animateCamera(cu);
                int height = projectListView.getHeight();
                projectListView.smoothScrollToPositionFromTop(index, height/3);

                return true;

            }
        });

        int padding = 30; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.moveCamera(cu);
        cu = CameraUpdateFactory.zoomOut();
        map.moveCamera(cu);

    }

}
