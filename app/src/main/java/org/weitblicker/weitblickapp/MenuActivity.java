package org.weitblicker.weitblickapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   //Project contents layout
    TextView p_name ;
    TextView p_description ;
    TextView p_title ;
    TextView p_location ;


    // Location Layout
    TextView l_town ;
    TextView l_city ;
    TextView l_country ;
    TextView l_postal_code ;
    TextView l_longitude ;
    TextView l_latitude ;
    TextView l_adition ;
    TextView l_comments ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // to call the project's contents from Project class
        p_name = (TextView) findViewById(R.id.name);
        p_description = (TextView) findViewById(R.id.diescription);
        p_title = (TextView) findViewById(R.id.title);
        p_location = (TextView) findViewById(R.id.location);


        Project p1=new Project(0,"","","","");
        p_name.setText("Nmae:"+ " "+p1.getName());
        p_description.setText("Description:"+ " "+p1.getDescription());
        p_title.setText("Abstract:"+ " "+p1.getAbstract());


        //to call the Location's data from Location Project

         l_town= (TextView) findViewById(R.id.town);
         l_city = (TextView) findViewById(R.id.city); ;
         l_country= (TextView) findViewById(R.id.country) ;
        l_postal_code=(TextView) findViewById(R.id.postal_code) ;
         l_longitude=(TextView) findViewById(R.id.longitude) ;
         l_latitude=(TextView) findViewById(R.id.latitude) ;
         l_adition =(TextView) findViewById(R.id.adition);
         l_comments=(TextView) findViewById(R.id.comments) ;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new Fragment();
        String title = getString(R.string.app_name);


        if (id == R.id.nav_projects) {
            title  = "Projekte";
            fragment = new ProjectList();

        } else if (id == R.id.nav_bicycle) {
            title  = "Radeln";

        } else if (id == R.id.nav_campaign) {
            title  = "Aktionen";

        } else if (id == R.id.nav_share) {
            title  = "Share";

        } else if (id == R.id.nav_send) {
            title  = "Send";

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_menu, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}