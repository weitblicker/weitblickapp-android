package org.weitblicker.weitblickapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ProjectList extends ListFragment {
    private ArrayList<Project> projects = new ArrayList<Project>();

    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        loadProjects();

        ProjectAdapter adapter = new ProjectAdapter(getActivity(), projects);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id){

        Log.i("listclick","Hallo " + position);
    }

    private void loadProjects(){
        Project sample_project_1 = new Project(111);
        Project sample_project_2 = new Project(222);
        Project sample_project_3 = new Project(333);

        projects.add(sample_project_1);
        projects.add(sample_project_2);
        projects.add(sample_project_3);
    }
}
