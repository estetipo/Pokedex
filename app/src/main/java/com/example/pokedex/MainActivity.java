package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedex.API.PkmnAdapter;
import com.example.pokedex.modelo.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
implements Callback<Pokemon> {
    ImageView imgPkmn,imgPkmnDetras,imgPkmnShiny;
    TextView tvNom,tvNum,tvPeso,tvAltura;
    ProgressBar barra;
    Button siguiente;
    int idPkmn = 0;
    int minPkdx = 1, maxPkdx = 898;
    CountDownTimer contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        carga();
        llamaPkmn();
        bar();
    }
    public void bar(){
        final int oneMin = 30000;
        contador = new CountDownTimer(oneMin, 1000) {
            public void onTick(long millisUntilFinished) {
                long finishedSeconds = oneMin - millisUntilFinished;
                int total = (int) (((float)finishedSeconds / (float)oneMin) * 100.0);
                barra.setProgress(total);
            }
            public void onFinish() {
                sig();
            }
        }.start();
    }
    public void carga(){
        imgPkmn = findViewById(R.id.ivPkmnFrente);
        imgPkmnDetras = findViewById(R.id.ivPkmnDetras);
        imgPkmnShiny = findViewById(R.id.ivPkmnShiny);
        tvNom = findViewById(R.id.tvNombre);
        tvNum = findViewById(R.id.tvNumero);
        tvPeso = findViewById(R.id.tvPeso);
        tvAltura = findViewById(R.id.tvAltura);
        barra = findViewById(R.id.progressBar);
        siguiente = findViewById(R.id.btnSiguiente);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sig();
            }
        });
    }
    public void llamaPkmn(){
        Random rand = new Random();
        idPkmn = rand.nextInt((maxPkdx - minPkdx) + 1) + minPkdx;
        Call<Pokemon> call = PkmnAdapter.getApiService(idPkmn).getPokemon();
        call.enqueue(this);
        call.request();
    }

    public void sig(){
        contador.cancel();
        llamaPkmn();
        bar();
    }

    @Override
    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
        if (response.isSuccessful()){
            Pokemon pk = response.body();
            tvNom.setText(String.valueOf((pk.getName()).charAt(0)).toUpperCase() + pk.getName().substring(1,pk.getName().length()));
            tvNum.setText(String.valueOf(pk.getId()));
            tvPeso.setText(convierte(pk.getWeight()) + " kg");
            tvAltura.setText(convierte(pk.getHeight()) + " m");
            Picasso.get().load(pk.getSprites().getFront_default()).into(imgPkmn);
            Picasso.get().load(pk.getSprites().getBack_default()).into(imgPkmnDetras);
            Picasso.get().load(pk.getSprites().getFront_shiny()).into(imgPkmnShiny);
        }else{
            Toast.makeText(this,"Ocurri?? un error en la consulta",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Pokemon> call, Throwable t) {
        Toast.makeText(this,"Ocurri?? un error en la consulta",Toast.LENGTH_LONG).show();
    }

    public Float convierte(String num){
        Float pe = Float.parseFloat(num);
        pe = pe/10;
        return pe;
    }
}