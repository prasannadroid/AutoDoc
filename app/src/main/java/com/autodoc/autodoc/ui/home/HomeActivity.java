package com.autodoc.autodoc.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.autodoc.App;
import com.autodoc.autodoc.R;
import com.autodoc.autodoc.data.MapData;
import com.autodoc.autodoc.ui.history.HistoryActivity;
import com.autodoc.autodoc.ui.login.LoginActivity;
import com.autodoc.autodoc.ui.profile.ProfileActivity;
import com.autodoc.autodoc.ui.report.ReportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ReportFragment reportFragment;
    private final static int ACTIVITY_RESULT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.welcome_to_auto_doc));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home) {
            reportFragment = new ReportFragment();
            ft.replace(R.id.main_fragment, reportFragment);
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_logout) {
            App.getInstance().getUserSession().logoutUser();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        ft.addToBackStack("tag_back");
        ft.commit();

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_RESULT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                MapData mapData = (MapData) data.getSerializableExtra("mapData");

                if (mapData != null) {
                    //MapData mapData = (MapData) bundle.getSerializable("mapData");
                    reportFragment.updateReport(mapData);
                }
            }
        }
    }
}
