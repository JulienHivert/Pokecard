package com.example.iem.test.PokeApi;

import com.example.iem.test.Model.Pokemon;
import com.example.iem.test.Model.PokemonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by iem on 11/12/2017.
 */

public interface pokeAPIService {

    @GET("pokemon")
    Call<PokemonResponse> obtenirListPokemon(@Query("limit") int limit, @Query("offset")int offset);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonById(@Path("id") int id);

    @GET("pokemon")
    Call<PokemonResponse> getPokemonByName(@Query("name") String name);

}
