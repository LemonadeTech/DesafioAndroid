package com.desafio.neto.desafioandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.desafio.neto.desafioandroid.notifications.AlarmService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;
    AlarmService alarmService;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById
    RelativeLayout fragment;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        OverviewFragment frag= (OverviewFragment) getSupportFragmentManager().findFragmentByTag("home");
        if (frag == null) {
            frag = OverviewFragment_.builder().build();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, frag, "home");
            ft.commit();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        alarmService = new AlarmService(this);
        alarmService.startAlarm();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alarmService.cancelAlarm();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            OverviewFragment frag = (OverviewFragment) getSupportFragmentManager().findFragmentByTag("home");
            if (frag == null) {
                frag = OverviewFragment_.builder().build();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, frag, "home");
                ft.commit();
            }
        } else if (id == R.id.nav_previsao) {
            navigationView.setCheckedItem(R.id.nav_home);
            toolbar.setTitle(R.string.previsao_dia);
            DetailFragment frag = (DetailFragment) getSupportFragmentManager().findFragmentByTag("detail");
            if (frag == null) {
                frag = DetailFragment_.builder().build();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, frag, "detail");
                ft.commit();
            }
        } else if (id == R.id.nav_conf) {
            navigationView.setCheckedItem(R.id.nav_home);
            toolbar.setTitle(R.string.config);
            ConfigFragment frag= (ConfigFragment) getSupportFragmentManager().findFragmentByTag("config");
            if (frag == null) {
                frag = ConfigFragment_.builder().build();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, frag, "config");
                ft.commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
