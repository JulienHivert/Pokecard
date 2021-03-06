package com.example.iem.test.BottomNavigation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iem.test.ListPokemonAdapter.ListPokemonAdapter;
import com.example.iem.test.Login_Register_Auth.InternetConnection;
import com.example.iem.test.Model.Pokemon;
import com.example.iem.test.Model.PokemonResponse;
import com.example.iem.test.PokeApi.pokeAPIService;
import com.example.iem.test.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by iem on 31/01/2018.
 */

public class ListPokemonFragment extends MotherFragment {

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;
    private int offset;
    private View v;

    public ListPokemonFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.recycler_pokemon, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(getContext());

        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);


        //UTILISATION DE RETROFIT
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        offset = 0; //
        progress = v.findViewById(R.id.progress_bar);
        contentLayout = v.findViewById(R.id.RLContent);

        onLoadingFragment();
        if (InternetConnection.checkConnection(getApplicationContext())) {
            getData(offset);
        } else {
            Toast.makeText(getActivity(), R.string.string_internet_connection_not_available,
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        return v;
    }

    private void getData(int offset) {
        pokeAPIService service = retrofit.create(pokeAPIService.class);
        Call<PokemonResponse> pokemonResponseCall = service.obtenirListPokemon(151 , offset); // LIMITE DE LA REQUETE
        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()){
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> listPokemon =  pokemonResponse.getResults();
                    listPokemonAdapter.addPokemon(listPokemon);
                }else{
                    Log.e(TAG,"onResponse" + response.body());
                }
                setVisible();
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure",
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
