package com.example.pokedex.API;

import com.example.pokedex.modelo.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PkmnService {
    @GET(" ")
    Call <Pokemon> getPokemon();
}
