package com.desafio.neto.desafioandroid.activities;

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

import com.desafio.neto.desafioandroid.R;
import com.desafio.neto.desafioandroid.fragments.Overview;
import com.desafio.neto.desafioandroid.fragments.Overview_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @ViewById
    Toolbar toolbar;

    @ViewById
    FloatingActionButton fab;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById
    RelativeLayout fragment;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        Overview frag= (Overview) getSupportFragmentManager().findFragmentByTag("home");
        if (frag == null) {
            frag = Overview_.builder().build();
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
    }

    @Click(R.id.fab)
    void fabClicked(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Overview frag= (Overview) getSupportFragmentManager().findFragmentByTag("home");
            if (frag == null) {
                frag = Overview_.builder().build();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, frag, "home");
                ft.commit();
            }
        } else if (id == R.id.nav_previsao) {
            Overview frag= (Overview) getSupportFragmentManager().findFragmentByTag("home");
            if (frag == null) {
                frag = Overview_.builder().build();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, frag, "home");
                ft.commit();
            }
        } else if (id == R.id.nav_conf) {
            Overview frag= (Overview) getSupportFragmentManager().findFragmentByTag("home");
            if (frag == null) {
                frag = Overview_.builder().build();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, frag, "home");
                ft.commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
