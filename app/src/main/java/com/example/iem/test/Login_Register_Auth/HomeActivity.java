package com.example.iem.test.Login_Register_Auth;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.iem.test.BottomNavigation.HomeFragment;
import com.example.iem.test.BottomNavigation.PlanFragment;
import com.example.iem.test.BottomNavigation.UserCardsFragment;

import com.example.iem.test.Constants;
import com.example.iem.test.Don.Donation;
import com.example.iem.test.BottomNavigation.ListPokemonFragment;
import com.example.iem.test.NavigationDrawer.DrawerListAdapter;
import com.example.iem.test.BottomNavigation.FriendsFragment;
import com.example.iem.test.NavigationDrawer.NavItem;
import com.example.iem.test.NavigationDrawer.ProfileUserFragment;
import com.example.iem.test.R;

import java.util.ArrayList;

import com.example.iem.test.Service.AlarmManagerPokemon;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
     private String username;
     private String profilePic, jsondata;
     private JSONObject response;

     public static FragmentManager fragmentManager;

     //NavDrawer
     ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_plan:
                    //PLAN CONTENT
                    showFragment(new PlanFragment());
                    return true;
                case R.id.navigation_friends:
                    //FRIENDS CONTENT
                    showFragment(new FriendsFragment());
                    return true;
                case R.id.navigation_accueil:
                    //HOME CONTENT
//                    Intent intent = getIntent();
//                    jsondata = intent.getStringExtra("userProfile");
//                    showFragment(HomeFragment.newInstance(jsondata));
                    showFragment(new HomeFragment());
                    return true;
                case R.id.navigation_cartes:
                    //USER CARDS CONTENT
                    showFragment(new UserCardsFragment());
                    return true;
                case R.id.navigation_liste:
                    //LIST POKEMON CONTENT
                    showFragment(new ListPokemonFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_home);
        final BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_accueil);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        jsondata = intent.getStringExtra("userProfile");

        try {
            response = new JSONObject(jsondata);
            username = response.getString("name");

        } catch(Exception e){
            e.printStackTrace();
        }

        Toast.makeText(this,"Bienvenu "+ username, Toast.LENGTH_SHORT).show();


        /**-------- Navigation Drawer --------*/
        mNavItems.add(new NavItem("Profile", R.mipmap.ic_profile));
        mNavItems.add(new NavItem("Facebook", R.drawable.facebook));
        mNavItems.add(new NavItem("Twitter", R.drawable.twitter));
        mNavItems.add(new NavItem("Google+", R.drawable.google));
        mNavItems.add(new NavItem("Donation", R.mipmap.ic_donate));
        mNavItems.add(new NavItem("Déconnexion", R.mipmap.ic_disconnect));

        // DrawerLayout
        mDrawerLayout = findViewById(R.id.DLmain);

        //DrawerToggle
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /* Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);

            }

            /* Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Populate the Navigation Drawer with options
        mDrawerPane = findViewById(R.id.RLMenuBurger);
        mDrawerList = findViewById(R.id.ListMenu);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /*
    * Called when a particular item from the navigation drawer
    * is selected.
    * */
    private void selectItemFromDrawer(int position) {
        switch (position) {
            case 0: //Profile
                showFragment(new ProfileUserFragment());
                break;
            case 1: //Facebook
                String facebookURL = "https://www.facebook.com/sharer/sharer.php?u=http://google.fr";
                Uri uriFacebook = Uri.parse(facebookURL);
                startActivity(new Intent(Intent.ACTION_VIEW,uriFacebook));
                break;
            case 2: //Twitter
                String tweetURL = "https://twitter.com/intent/tweet?text=Viens me rejoindre sur MyPokeDEX ! &url=";
                Uri uri = Uri.parse(tweetURL);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                break;
            case 3: //Google
                String GoogleURL = "https://plus.google.com/share?url=http://google.fr";
                Uri uriGoogle = Uri.parse(GoogleURL);
                startActivity(new Intent(Intent.ACTION_VIEW,uriGoogle));
                break;
            case 4: //Donation
                Intent intent = new Intent(this, Donation.class);
                startActivity(intent);
                break;
            case 5: //Deconnexion
                Disconnect();
                break;
        }

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public void onBackPressed() {
        Disconnect();
    }

    private void Disconnect() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Déconnexion")
                .setMessage("Etes-vous sur de vouloir vous déconnecter ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(HomeActivity.this, MainActivity.class);
                                startActivity(intent1);
                                HomeActivity.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    public FragmentManager getFragmentManagerFromActivity() {
        return this.getFragmentManager();

    }

    private void scheduleAlarm() {

        Intent intent = new Intent(HomeActivity.this, AlarmManagerPokemon.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, Constants.ALARM_REQUEST_CODE, intent,PendingIntent.FLAG_UPDATE_CURRENT );
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, Constants.INTERVAL_RANDOM_POKEMON, pIntent);
    }

}
