package com.example.iem.test.Model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by iem on 11/12/2017.
 */

public class Pokemon {

    private int id;
    private int number;
    private String name;
    private String url;

    @SerializedName("weight")
    private int weight;
    @SerializedName("height")
    private int height;

    @SerializedName("stats")
    private List<Object> stats;
    //speed > special-defense > special-attack > defense > attack > hp

    @SerializedName("types")
    private List<Object> listTypes;
    private String[] type;

    private Double speed;
    private Double special_defense;
    private Double special_attack;
    private Double defense;
    private Double attack;
    private Double hp;

    public Pokemon(int id, int number, String name, String url, int weight,List<Object> listTypes, List<Object> stats) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.url = url;
        this.weight = weight;
        this.stats = stats;
        this.listTypes = listTypes;

    }

    public void setFromListsOfStats(List<Object> stats, List<Object> types) {
        LinkedTreeMap<String, Object> speedMap = (LinkedTreeMap<String, Object>)stats.get(0);
        Double speedFromMap = (Double) speedMap.get("base_stat");
        this.speed = speedFromMap;

        LinkedTreeMap<String, Object> special_defenseMap = (LinkedTreeMap<String, Object>)stats.get(1);
        Double special_defenseFromMap = (Double) special_defenseMap.get("base_stat");
        this.special_defense = special_defenseFromMap;

        LinkedTreeMap<String, Object> special_attackMap = (LinkedTreeMap<String, Object>)stats.get(2);
        Double special_attackFromMap = (Double) special_attackMap.get("base_stat");
        this.special_attack = special_attackFromMap;

        LinkedTreeMap<String, Object> defenseMap = (LinkedTreeMap<String, Object>)stats.get(3);
        Double defenseFromMap = (Double) defenseMap.get("base_stat");
        this.defense = defenseFromMap;

        LinkedTreeMap<String, Object> attackMap = (LinkedTreeMap<String, Object>)stats.get(4);
        Double attackFromMap = (Double) attackMap.get("base_stat");
        this.attack = attackFromMap;

        LinkedTreeMap<String, Object> hpMap = (LinkedTreeMap<String, Object>)stats.get(5);
        Double hpFromMap = (Double) hpMap.get("base_stat");
        this.hp = hpFromMap;

        String[] resultsTypes = new String[types.size()];
        for (int i =0 ; i < types.size(); i++) {
            LinkedTreeMap<String, Object> typeMap = (LinkedTreeMap<String, Object>)types.get(i);
            LinkedTreeMap<String, Object> secondTypeMap = (LinkedTreeMap<String, Object>) typeMap.get("type");
            Object typeFromMap = secondTypeMap.get("name");
            String convertedType = typeFromMap.toString();
            resultsTypes[i] = convertedType;
        }
        this.type = resultsTypes;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public List<Object> getListTypes() {
        return listTypes;
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getSpecial_defense() {
        return special_defense;
    }

    public Double getSpecial_attack() {
        return special_attack;
    }

    public Double getDefense() {
        return defense;
    }

    public Double getAttack() {
        return attack;
    }

    public Double getHp() {
        return hp;
    }

    public List<Object> getStats() {
        return stats;
    }

    public List<Object> getTypes() {
        return listTypes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String[] getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        public int getNumber() {

        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

}
