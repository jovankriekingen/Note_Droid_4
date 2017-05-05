package jo.vk.notedroid4.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import jo.vk.notedroid4.R;
import jo.vk.notedroid4.fragments.AboutFragment;
import jo.vk.notedroid4.fragments.NotesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private AboutFragment aboutFragment;
    private Fragment settingsFragment;
    private NotesFragment notesFragment;

    //no magic values
    private static final String BACKSTACK_NAME = "main_stack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.tv_username);
        username.setText("Jo");

        aboutFragment = new AboutFragment();
        settingsFragment = new Fragment();
        notesFragment = new NotesFragment();

        replaceFragment(notesFragment);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.nav_notes:
                replaceFragment(notesFragment);
                break;

            case R.id.nav_settings:
                replaceFragment(settingsFragment);
                break;

            case R.id.nav_about:
                replaceFragment(aboutFragment);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment f)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, f)
                .addToBackStack(BACKSTACK_NAME)
                .commit();
    }
}