package com.example.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PkmnService {
    @GET(" ")
    Call <Pokemon> getPokemon();
}
