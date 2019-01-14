package com.example.iem.test.BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.iem.test.Don.Donation;
import com.example.iem.test.Login_Register_Auth.Fragments.MapsActivity;
import com.example.iem.test.Login_Register_Auth.HomeActivity;
import com.example.iem.test.Login_Register_Auth.InternetConnection;
import com.example.iem.test.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PlanFragment extends Fragment {
    private Button don, position;
    private View v;

    public PlanFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_plan, container, false);
        position = v.findViewById(R.id.position);

        position.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        if (InternetConnection.checkConnection(getApplicationContext())) {
        } else {
            Toast.makeText(getActivity(), R.string.string_internet_connection_not_available,
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        return v;
    }

}
