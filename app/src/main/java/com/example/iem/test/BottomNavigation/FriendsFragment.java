package com.example.iem.test.BottomNavigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iem.test.Login_Register_Auth.InternetConnection;
import com.example.iem.test.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FriendsFragment extends Fragment {
    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (InternetConnection.checkConnection(getApplicationContext())) {
        } else {
            Toast.makeText(getActivity(), R.string.string_internet_connection_not_available,
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }
}
