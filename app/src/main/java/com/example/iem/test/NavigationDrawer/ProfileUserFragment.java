package com.example.iem.test.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iem.test.BottomNavigation.HomeFragment;
import com.example.iem.test.R;

/**
 * Created by iem on 31/01/2018.
 */

public class ProfileUserFragment extends Fragment {

    private View v;
    private EditText oldPass, newPass;
    private AppCompatButton buttonValidate;
    private String oldPassText, newPassText;

    public ProfileUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        oldPass = v.findViewById(R.id.ETOldPass);
        newPass = v.findViewById(R.id.ETNewPass);
        buttonValidate = v.findViewById(R.id.BtnValidate);

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                oldPassText = oldPass.getText().toString();
                newPassText = newPass.getText().toString();
                if ((oldPassText.isEmpty()) || !(oldPassText.equals(newPassText))){
                    //non identic ou vide
                    Toast.makeText(getActivity(), "Les champs sont vides ou non conforme !",
                            Toast.LENGTH_LONG).show();
                } else {
                    //identic edittexts
                    Toast.makeText(getActivity(), "Mot de passe modifié !",
                            Toast.LENGTH_LONG).show();
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment())
                            .commit();
                }
            }
        });

        return v;
    }
}
