package com.example.iem.test.ListPokemonAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.iem.test.DetailActivity;
import com.example.iem.test.Model.Pokemon;
import com.example.iem.test.R;

import java.util.ArrayList;

/**
 * Created by iem on 11/12/2017.
 */

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;
    private TextView idPokemon;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentPokemon = new Intent(context, DetailActivity.class);
            idPokemon = view.findViewById(R.id.IDPokemon);
            int id = Integer.parseInt(idPokemon.getText().toString());
            intentPokemon.putExtra("idpokemon",id);
            context.startActivity(intentPokemon);
        }
    };

    public ListPokemonAdapter(Context context){
        dataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempokemon, parent,false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListPokemonAdapter.ViewHolder holder, int position) {

        Pokemon p = dataset.get(position);
        holder.textView.setText(p.getName());
        String test = p.getUrl().replace("https://pokeapi.co/api/v2/pokemon/", "");
        String test1 = test.replace("/", "");
        holder.idPokemon.setText(test1);

        Glide.with(context)
                .load("https://pokeapi.co/media/sprites/pokemon/"+ p.getNumber()+".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public Pokemon getItem(int position) {
        return dataset.get(position);
    }

    public void addPokemon(ArrayList<Pokemon> listPokemon) {

        dataset.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView, idPokemon;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.IDPokemonPic);
            textView = itemView.findViewById(R.id.PokemonName);
            idPokemon = itemView.findViewById(R.id.IDPokemon);
        }
    }
}
