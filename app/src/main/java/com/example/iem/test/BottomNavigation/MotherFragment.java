package com.example.iem.test.BottomNavigation;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iem.test.Login_Register_Auth.HomeActivity;
import com.example.iem.test.R;

import retrofit2.Retrofit;

public abstract class MotherFragment extends Fragment {
    public Retrofit retrofit;
    public static final String TAG = "POKEDEX";
    public ProgressBar progress;
    public RelativeLayout contentLayout;
    public BottomNavigationView nav;
    public FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationNoItemSelected
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_plan:
                    //PLAN CONTENT
                    return false;
                case R.id.navigation_friends:
                    //MESSAGES CONTENT
                    return false;
                case R.id.navigation_accueil:
                    //HOME CONTENT
                    return false;
                case R.id.navigation_cartes:
                    //USER CARDS CONTENT
                    return false;
                case R.id.navigation_liste:
                    //LIST POKEMON CONTENT
                    return false;
            }
            return false;
        }
    };

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
                    //MESSAGES CONTENT
                    showFragment(new FriendsFragment());
                    return true;
                case R.id.navigation_accueil:
                    //HOME CONTENT
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

    public MotherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        View rootView = ((Activity)context).getWindow().getDecorView();
        nav = rootView.findViewById(R.id.bottom_navigation);
        fragmentManager = ((HomeActivity)getActivity()).getFragmentManagerFromActivity();
    }

    private void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    public void onLoadingFragment() {
        progress.setVisibility(View.VISIBLE);
        contentLayout.setEnabled(false);
        nav.setOnNavigationItemSelectedListener(mOnNavigationNoItemSelected);
        contentLayout.setAlpha((float).45);
    }

    public void setVisible() {
        progress.setVisibility(View.GONE);
        contentLayout.setAlpha((float)1);
        contentLayout.setEnabled(true);
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
