package com.example.pokedex.API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PkmnAdapter {
    private static PkmnService API_SERVICE;
    public static PkmnService getApiService(int id) {
        String baseUrl = "https://pokeapi.co/api/v2/pokemon/"+id+"/";
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        API_SERVICE = retrofit.create(PkmnService.class);
        return API_SERVICE;
    }
}