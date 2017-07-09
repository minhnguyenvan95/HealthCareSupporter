package mobi.devteam.demofalldetector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import mobi.devteam.demofalldetector.R;
import mobi.devteam.demofalldetector.fragment.HomeFragment;
import mobi.devteam.demofalldetector.fragment.ProfileFragment;
import mobi.devteam.demofalldetector.fragment.RelativeListFragment;
import mobi.devteam.demofalldetector.myServices.GetLocationService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        addControls();
        initData();

        onNavigationItemSelected(navigationView.getMenu().getItem(0));// select home for default

        //TODO: HANDLE THING HERE
        startService(new Intent(this, GetLocationService.class));
    }

    private void initData() {

    }

    private void addControls() {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        navItemSelected(item.getItemId());
        return true;
    }

    public void navItemSelected(int id){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_home:
                setTitle(R.string.tittle_home);
                fragmentTransaction.replace(R.id.frame_container, HomeFragment.newInstance());
                fragmentTransaction.commit();
                break;
            case R.id.nav_relatives:
                setTitle(R.string.tittle_relatives);
                fragmentTransaction.replace(R.id.frame_container, RelativeListFragment.
                        newInstance());
                fragmentTransaction.commit();
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.nav_profile:
                setTitle(R.string.tittle_profile);
                fragmentTransaction.replace(R.id.frame_container, ProfileFragment.newInstance());
                fragmentTransaction.commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
