package com.example.iem.test.Model;

import java.util.ArrayList;

/**
 * Created by iem on 11/12/2017.
 */

public class PokemonResponse {

    private ArrayList<Pokemon> results;
    //private ArrayList<String> resu
    private Pokemon result;

    public  ArrayList<Pokemon> getResults() {
        return results;
    }
    public Pokemon getResult() {return result;}

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
    public void setResult(Pokemon result) { this.result = result; }
}
