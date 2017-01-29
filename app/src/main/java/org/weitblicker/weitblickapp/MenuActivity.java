package org.weitblicker.weitblickapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProjectListFragment.OnProjectSelectListener,
        NewsListFragment.OnNewsArticleSelectListener,
        MeetInfoListFragment.OnMeetInfoSelectListener,
        ProjectMapFragment.OnFragmentInteractionListener{

    private Fragment currentFragment;
    private Menu optionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup picasso image loading for caching
        setupPicasso();

        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        FontAwesomeDrawable.FontAwesomeDrawableBuilder builder
                = new FontAwesomeDrawable.FontAwesomeDrawableBuilder(getBaseContext());
        builder.setColor(R.color.wb_darkgrey);
        builder.setSize(22);

        // news icon
        MenuItem newsItem = menu.findItem(R.id.nav_news);
        newsItem.setIcon(builder.build(R.string.fa_newspaper_o));

        // projects icon
        MenuItem projectsItem = menu.findItem(R.id.nav_projects);
        projectsItem.setIcon(builder.build(R.string.fa_globe));

        // bicycle icon
        //MenuItem bicycleItem = project_options.findItem(R.id.nav_bicycle);
        //bicycleItem.setIcon(builder.build(R.string.fa_bicycle));

        // campaign icon
        //MenuItem campaignItem = project_options.findItem(R.id.nav_campaign);
        //campaignItem.setIcon(builder.build(R.string.fa_rocket));

        // credits icon
        MenuItem creditsItem = menu.findItem(R.id.nav_credits);
        creditsItem.setIcon(builder.build(R.string.fa_code));

        // meet icon
        MenuItem meetItem = menu.findItem(R.id.nav_meet);
        meetItem.setIcon(builder.build(R.string.fa_users));

        // join icon
        //MenuItem joinItem = project_options.findItem(R.id.nav_join);
        //joinItem.setIcon(builder.build(R.string.fa_star));

        String title = "Weitblick News";
        Fragment fragment = new NewsListFragment();
        loadFragment(title, fragment);
        newsItem.setEnabled(true);
        newsItem.setChecked(true);

    }

    private void setupPicasso(){
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().cache(cache);
        Picasso.Builder picassoBuilder = new Picasso.Builder(getApplicationContext());
        picassoBuilder.downloader(new OkHttp3Downloader(clientBuilder.build()));
        Picasso picasso = picassoBuilder.build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException ignored) {
            Log.e("Picasso", "Picasso instance already used");
        }
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
        optionMenu = menu;
        getMenuInflater().inflate(R.menu.project_options, menu);
        return true;
    }

    private void loadOptionsMenuForSelection(MenuItem item){

        hideAllOptionItems();

        switch(item.getItemId()){
            case R.id.nav_projects:
                setOptionItemVisible(R.id.op_nav_list_view);
                setOptionItemVisible(R.id.op_nav_map_view);
                return;
            default:
                return;
        }
    }

    private void setOptionItemVisible(int itemId){
        optionMenu.findItem(itemId).setVisible(true);
    }

    private void hideAllOptionItems(){
        for(int i=0; i< optionMenu.size(); i++){
            MenuItem item = optionMenu.getItem(i);
            item.setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch(item.getItemId()){
            case R.id.op_nav_map_view:
                ProjectMapFragment projectMapFragment = ProjectMapFragment.newInstance();
                projectMapFragment.setOnProjectSelectListener(this);
                loadFragment(null, projectMapFragment);
                break;
            case R.id.op_nav_list_view:
                ProjectListFragment projectListFragment = new ProjectListFragment();
                loadFragment(null, projectListFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getCheckedItemId(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isChecked()) {
                return item.getItemId();
            }
        }
        return -1;
    }

    String currentTitle = null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new Fragment();
        String title = getString(R.string.app_name);

        switch(id){
            case R.id.nav_news:
                title = "Weitblick News";
                fragment = new NewsListFragment();
                break;
            case R.id.nav_projects:
                title = "Weitblick Projekte";
                fragment = new ProjectListFragment();
                break;

            /* case R.id.nav_campaign:
                title = "Aktivität";
                // TODO
                break;
            case R.id.nav_bicycle:
                title = "Radeln";
                // TODO
                break;
            case R.id.nav_join:
                // TODO
                title = "Join Weitblick";
                fragment = new JoinFragment();
                break;

            */
            case R.id.nav_meet:
                title = "Meet Weitblick";
                fragment = new MeetInfoListFragment();
                break;
            case R.id.nav_credits:
                title  = "App Credits";
                fragment = new CreditsListFragment();
                break;
        }

        loadFragment(title, fragment);

        loadOptionsMenuForSelection(item);

        return true;
    }

    int cnt = 0;

    public void loadFragment(String title, Fragment fragment){
        if (fragment != null && !fragment.isAdded()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            if(currentFragment != null && currentFragment.isAdded()){
                ft.remove(currentFragment);
            }

            Log.i("debug", "loadFragment: " + title + " cnt: " + cnt + " fragment:" + fragment.toString());
            ft.replace(R.id.content_menu, fragment);
            ft.addToBackStack(cnt++ + "cnt");
            ft.commit();

            if(title != null && !title.equals("") && !title.equals(currentTitle)){
                currentTitle = title;

                // set the toolbar title
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            currentFragment = fragment;
        }


    }


    @Override
    public void onProjectSelect(Project project) {
        Fragment fragment = ProjectFragment.newInstance(project);
        loadFragment(null, fragment);
    }

    @Override
    public void onNewArticleSelect(NewsArticle newsArticle) {
        Fragment fragment = NewsArticleFragment.newInstance(newsArticle);
        loadFragment(null, fragment);
    }

    @Override
    public void onMeetInfoSelect(MeetInfo meetInfo) {
        Fragment fragment = MeetInfoFragment.newInstance(meetInfo);
        loadFragment(null, fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
