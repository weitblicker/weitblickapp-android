package org.weitblicker.weitblickapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        mListView = (ListView) findViewById(R.id.project_list);

        Project sample_project_1 = new Project(111);
        Project sample_project_2 = new Project(222);
        Project sample_project_3 = new Project(333);

        final ArrayList<Project> projectList = new ArrayList<Project>();//Project.getProjectFromFile("recipes.json", this);
        projectList.add(sample_project_1);
        projectList.add(sample_project_2);
        projectList.add(sample_project_3);

       ProjectAdapter adapter = new ProjectAdapter(this, projectList);
       mListView.setAdapter(adapter);
    }
}
