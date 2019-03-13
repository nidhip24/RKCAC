package com.rk.rkabc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rk.rkabc.admin.AdminHome;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG_EVENT = "EVENT";
    private static final String TAG_ATTENDANCE = "ATTENDANCE";
    private static final String TAG_FEEDBACK = "FEEDBACK";
    private static final String TAG_GAL = "GALLERY";
    public static String CURRENT_TAG = TAG_EVENT;

    int navItem = 0;

    int navidd=0;

    DrawerLayout drawer;

    String activityTitles[] = {"Event","Attendance","Feedback","Gallery"};

    NavigationView navigationView;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            navItem = 0;
            CURRENT_TAG = TAG_EVENT;
            loadFragment();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            //Toast.makeText(getApplicationContext(),"well you can't",Toast.LENGTH_SHORT).show();
                            Home.this.finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure? You want to exit?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void selectNavMenu() {
        try{
            navigationView.getMenu().getItem(navItem).setChecked(true);
        }catch (Exception e){
            e.printStackTrace();
        }

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

        if (id == R.id.nav_event) {
            CURRENT_TAG = TAG_EVENT;
            navItem = 0;
        } else if (id == R.id.nav_attendance) {
            CURRENT_TAG = TAG_ATTENDANCE;
            navItem = 1;
        } else if (id == R.id.nav_feedback) {
            CURRENT_TAG = TAG_FEEDBACK;
            navItem = 2;
        }else if (id == R.id.nav_gallery) {
            CURRENT_TAG = TAG_GAL;
            navItem = 3;
        }
        else if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
            UserData u = new UserData();
            u.deleteUser(getApplication());
            Intent i = new Intent(getApplicationContext(),Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(id != R.id.nav_logout)
            loadFragment();

        return true;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItem]);
    }

    private void loadFragment(){
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        //Toast.makeText(getApplicationContext(),"ys"+navItem,Toast.LENGTH_SHORT).show();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        //Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT).show();
    }

    private Fragment getHomeFragment(){
        //Toast.makeText(getApplicationContext(),"future is here" + navItem,Toast.LENGTH_SHORT).show();
        switch (navItem) {

            case 0:
                // event
                return new EventFragment();
            case 1:
                // attendance
                return new AttendanceFragment();
            case 2:
                // feedback
                return new FeedbackFragment();
            case 3:
                // gallery
                return new Gallery();
            default:
                return new EventFragment();
        }
    }
}
