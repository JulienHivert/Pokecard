package com.example.iem.test.BottomNavigation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.iem.test.DetailActivity;
import com.example.iem.test.Login_Register_Auth.HomeActivity;
import com.example.iem.test.Login_Register_Auth.InternetConnection;
import com.example.iem.test.Login_Register_Auth.MainActivity;
import com.example.iem.test.Model.Pokemon;
import com.example.iem.test.PokeApi.pokeAPIService;
import com.example.iem.test.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;


public class HomeFragment extends MotherFragment {
    private TextView NameLastPokemon, TimerText;
    private ImageView ImageLastPokemon;
    private static final String FORMAT = "%02d:%02d:%02d";
    private ImageView imageProfile;
    private String jsondata, username;
    private JSONObject response, profile_pic_data, profile_pic_url;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String userProfile) {
        Bundle args = new Bundle();
        args.putString("userProfileKey",userProfile);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        NameLastPokemon = v.findViewById(R.id.TVNameLastPokemon);
        ImageLastPokemon = v.findViewById(R.id.imgLastPokemon);
        progress = v.findViewById(R.id.progress_bar);
        contentLayout = v.findViewById(R.id.RLContent);
        TimerText = v.findViewById(R.id.TimerRandom);
        imageProfile = v.findViewById(R.id.imgProfile);

//        jsondata = getArguments().getString("userProfileKey");
//
//        try {
//            response = new JSONObject(jsondata);
//            username = response.getString("name");
//            profile_pic_data = new JSONObject(response.get("picture").toString());
//            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
//            Picasso.with(getActivity()).load(profile_pic_url.getString("url"))
//                    .into(imageProfile);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        Toast.makeText(getActivity(), username,
//                Toast.LENGTH_LONG).show();

        new CountDownTimer(86400000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                TimerText.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                TimerText.setText("done!");
            }
        }.start();
        onLoadingFragment();
        //TODO: if User already got, get last pokemon from ListPokemon of the user
        //int randomNum = ThreadLocalRandom.current().nextInt(1, 151 + 1);
            int userLastPokemon = 55;
        if (InternetConnection.checkConnection(getApplicationContext())) {
            getData(userLastPokemon);
        } else {
            Toast.makeText(getActivity(), R.string.string_internet_connection_not_available,
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        return v;


    }

    //Récupère le dernier pokemon via son id
    private void getData(int id) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pokeAPIService service = retrofit.create(pokeAPIService.class);

        Call<Pokemon> call = service.getPokemonById(id);
        call.enqueue(new Callback<Pokemon>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    final Pokemon pokemon = response.body();
                    NameLastPokemon.setText(pokemon.getName());

                    Glide.with(getContext())
                            .load("https://pokeapi.co/media/sprites/pokemon/"+ pokemon.getId() +".png")
                            .centerCrop()
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ImageLastPokemon);

                    ImageLastPokemon.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view)
                        {
                            Intent intentPokemon = new Intent(getActivity(), DetailActivity.class);
                            intentPokemon.putExtra("idpokemon",pokemon.getId());
                            startActivity(intentPokemon);
                        }
                    });
                } else {
                    Log.e(TAG, "onResponse" + response.body());
                }
                setVisible();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Snackbar.make(getView(), R.string.string_internet_connection_not_available, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onLastPokemon(View v) {
        Intent intentPokemon = new Intent(getActivity(), DetailActivity.class);
        intentPokemon.putExtra("idpokemon",4);
        startActivity(intentPokemon);
    }
}