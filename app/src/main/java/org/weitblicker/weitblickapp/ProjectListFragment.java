package org.weitblicker.weitblickapp;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ProjectListFragment extends ListFragment {
    ArrayList<Project> projects = new ArrayList<Project>();

    OnProjectSelectListener onProjectSelectInterface;

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
        loadProjects();
        ProjectListAdapter adapter = new ProjectListAdapter(getActivity(), projects);
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

        // just for testing
        for(int i=0; i<10; i++) {
            projects.add(new Project());
        }

    }
}
