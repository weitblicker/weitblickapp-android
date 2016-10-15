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

    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        Project sample_project_1 = new Project(111);
        Project sample_project_2 = new Project(222);
        Project sample_project_3 = new Project(333);

        ArrayList<Project> projectList = new ArrayList<Project>();//Project.getProjectFromFile("recipes.json", this);
        projectList.add(sample_project_1);
        projectList.add(sample_project_2);
        projectList.add(sample_project_3);

        ProjectAdapter adapter = new ProjectAdapter(getActivity(), projectList);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id){

        Log.i("listclick","Hallo " + position);
    }
}
