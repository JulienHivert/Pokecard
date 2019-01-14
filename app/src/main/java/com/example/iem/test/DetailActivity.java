package com.example.iem.test;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.iem.test.Login_Register_Auth.HomeActivity;
import com.example.iem.test.Login_Register_Auth.InternetConnection;
import com.example.iem.test.Login_Register_Auth.MainActivity;
import com.example.iem.test.Model.Pokemon;
import com.example.iem.test.PokeApi.pokeAPIService;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "POKEDEX";

    public Retrofit retrofit;
    private TextView NamePokemon, typePoke, HPPoke, weightPoke, heightPoke, speedPoke, attackPoke, defensePoke, speDefensePoke, speAttackPoke;
    private ImageView ImagePokemon;
    private int idPokemon;
    private ProgressBar progress;
    private View lineBreak1, lineBreak2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        NamePokemon = findViewById(R.id.TVNamePokemon);
        ImagePokemon = findViewById(R.id.imgPokemon);
        progress = findViewById(R.id.progress_bar);
        lineBreak1 = findViewById(R.id.LineBreak);
        lineBreak2 = findViewById(R.id.LineBreak2);
        typePoke = findViewById(R.id.TVType);
        HPPoke = findViewById(R.id.TVHP);
        weightPoke = findViewById(R.id.TVWeight);
        heightPoke = findViewById(R.id.TVHeight);
        speedPoke = findViewById(R.id.TVSpeed);
        attackPoke = findViewById(R.id.TVAttack);
        defensePoke = findViewById(R.id.TVDefense);
        speDefensePoke = findViewById(R.id.TVSpeDefense);
        speAttackPoke = findViewById(R.id.TVSpeAttack);

        Intent myIntent = getIntent();
        idPokemon = myIntent.getIntExtra("idpokemon",0);
        if(idPokemon == 0) {
            Toast.makeText(this, "Erreur : Pokemon introuvable",
                    Toast.LENGTH_LONG).show();
            super.finish();
        } else {
            if (InternetConnection.checkConnection(getApplicationContext())) {
                getData(idPokemon);
            } else {
                Toast.makeText(this, R.string.string_internet_connection_not_available,
                        Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

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
                    Pokemon pokemon = response.body();
                    pokemon.setFromListsOfStats(pokemon.getStats(), pokemon.getTypes());

                    NamePokemon.setText(pokemon.getName());
                    String TextOnType = "";
                    for (int i = 0; i < pokemon.getType().length; i++) {
                        if (i ==1) {
                            TextOnType = TextOnType + " / " + pokemon.getType()[i];
                        } else {
                            TextOnType = TextOnType + pokemon.getType()[i];
                        }
                    }
                    typePoke.setText(TextOnType);
                    HPPoke.setText(String.valueOf(pokemon.getHp()));
                    weightPoke.setText(String.valueOf(pokemon.getWeight()));
                    heightPoke.setText(String.valueOf(pokemon.getHeight()));
                    speedPoke.setText(String.valueOf(pokemon.getSpeed()));
                    attackPoke.setText(String.valueOf(pokemon.getAttack()));
                    defensePoke.setText(String.valueOf(pokemon.getDefense()));
                    speDefensePoke.setText(String.valueOf(pokemon.getSpecial_defense()));
                    speAttackPoke.setText(String.valueOf(pokemon.getSpecial_attack()));

                    //Set colors depend on type of the pokemon
                    String mainType = "";
                    if (pokemon.getType().length == 1) {
                        mainType = (pokemon.getType()[0]);
                    } else {
                        mainType = (pokemon.getType()[1]);
                    }
                    setColorsFromTypePokemon(mainType);

                    //ImagePokemon.setBackground();

                    Glide.with(getBaseContext())
                            .load("https://pokeapi.co/media/sprites/pokemon/"+ pokemon.getId() +".png")
                            .centerCrop()
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ImagePokemon);
                    progress.setVisibility(View.GONE);

                } else {
                    Log.e(TAG, "onResponse" + response.body());

                }
                //setVisible();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG, "onFailure"+ t.getMessage());
                DetailActivity.super.finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setColorsFromTypePokemon(String mainType) {
        switch (mainType) {
            case "normal" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_normal));
                lineBreak1.setBackgroundResource(R.color.typeNormal);
                lineBreak2.setBackgroundResource(R.color.typeNormal);
                break;
            case "fighting" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_fighting));
                lineBreak1.setBackgroundResource(R.color.typeFighting);
                lineBreak2.setBackgroundResource(R.color.typeFighting);
                break;
            case "flying" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_flying));
                lineBreak1.setBackgroundResource(R.color.typeFlying);
                lineBreak2.setBackgroundResource(R.color.typeFlying);
                break;
            case "poison" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_poison));
                lineBreak1.setBackgroundResource(R.color.typePoison);
                lineBreak2.setBackgroundResource(R.color.typePoison);
                break;
            case "ground" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_ground));
                lineBreak1.setBackgroundResource(R.color.typeGround);
                lineBreak2.setBackgroundResource(R.color.typeGround);
                break;
            case "rock" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_rock));
                lineBreak1.setBackgroundResource(R.color.typeRock);
                lineBreak2.setBackgroundResource(R.color.typeRock);
                break;
            case "bug" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_bug));
                lineBreak1.setBackgroundResource(R.color.typeBug);
                lineBreak2.setBackgroundResource(R.color.typeBug);
                break;
            case "ghost" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_ghost));
                lineBreak1.setBackgroundResource(R.color.typeGhost);
                lineBreak2.setBackgroundResource(R.color.typeGhost);
                break;
            case "steel" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_steel));
                lineBreak1.setBackgroundResource(R.color.typeSteel);
                lineBreak2.setBackgroundResource(R.color.typeSteel);
                break;
            case "fire" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_fire));
                lineBreak1.setBackgroundResource(R.color.typeFire);
                lineBreak2.setBackgroundResource(R.color.typeFire);
                break;
            case "water" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_water));
                lineBreak1.setBackgroundResource(R.color.typeWater);
                lineBreak2.setBackgroundResource(R.color.typeWater);
                break;
            case "grass" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_grass));
                lineBreak1.setBackgroundResource(R.color.typeGrass);
                lineBreak2.setBackgroundResource(R.color.typeGrass);
                break;
            case "electric" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_electric));
                lineBreak1.setBackgroundResource(R.color.typeElectric);
                lineBreak2.setBackgroundResource(R.color.typeElectric);
                break;
            case "psychic" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_psychic));
                lineBreak1.setBackgroundResource(R.color.typePsychic);
                lineBreak2.setBackgroundResource(R.color.typePsychic);
                break;
            case "ice" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_ice));
                lineBreak1.setBackgroundResource(R.color.typeIce);
                lineBreak2.setBackgroundResource(R.color.typeIce);
                break;
            case "dragon" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_dragon));
                lineBreak1.setBackgroundResource(R.color.typeDragon);
                lineBreak2.setBackgroundResource(R.color.typeDragon);
                break;
            case "dark" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_dark));
                lineBreak1.setBackgroundResource(R.color.typeDark);
                lineBreak2.setBackgroundResource(R.color.typeDark);
                break;
            case "fairy" :
                ImagePokemon.setBackground(getDrawable(R.drawable.border_fairy));
                lineBreak1.setBackgroundResource(R.color.typeFairy);
                lineBreak2.setBackgroundResource(R.color.typeFairy);
                break;
        }
    }
}
