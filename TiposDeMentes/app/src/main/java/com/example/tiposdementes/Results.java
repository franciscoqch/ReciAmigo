package com.example.tiposdementes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

public class Results extends AppCompatActivity {
    private TextView txtPuntos, txtResultMente, txtnombreuser;
    private String[] mentes, arregloresultado;
    public static String nombre = "";
    private RecyclerView recyclerView;
    private AdaptadorResult adaptadorResult;
    private Button btnContinuarHome;
    private LottieAnimationView animacionDanger, animacionNormal;
    private Double dominantePorcentaje, subdominantePorcentaje, apoyoPorcentaje;
    private Boolean validacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        txtPuntos = findViewById(R.id.txtPuntos);
        btnContinuarHome = findViewById(R.id.btnContinuarHome);
        txtResultMente = findViewById(R.id.txtMenteCirculo);
        recyclerView = findViewById(R.id.recyclerResult);
        txtnombreuser = findViewById(R.id.txtnombreuser);
        animacionDanger = findViewById(R.id.resulAlerta);
        animacionNormal = findViewById(R.id.resultNormal);

        SharedPreferences prefe = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefe.edit();
        mentes = new String[6];
        arregloresultado = new String[8];

        btnContinuarHome.setOnClickListener(v -> Utils.intentTrans(MenuPrincipal.class, Results.this));

        nombre = prefe.getString(getResources().getString(R.string.shnombre), getResources().getString(R.string.shvalordefault));
        mentes = getIntent().getStringArrayExtra("mentes");
        txtPuntos.setText(mentes[3]);
        txtnombreuser.setText(nombre);

        validacion = mentes[0].equals(getResources().getString(R.string.desproporcionado));

        txtResultMente.setVisibility(!validacion ? View.VISIBLE : View.GONE);
        txtResultMente.setText(!validacion ? mentes[0] : "");
        animacionDanger.setVisibility(!validacion ? View.GONE : View.VISIBLE);
        animacionNormal.setVisibility(!validacion ? View.VISIBLE : View.GONE);

        dominantePorcentaje = ((Double.parseDouble(mentes[3])) / 45) * 100;
        subdominantePorcentaje = ((Double.parseDouble(mentes[4])) / 45) * 100;
        apoyoPorcentaje = ((Double.parseDouble(mentes[5])) / 45) * 100;

        arregloresultado[0] = mentes[0]; //mente dominante
        arregloresultado[1] = mentes[1]; //mente subdominante
        arregloresultado[2] = mentes[2]; // mente apoyo
        arregloresultado[3] = String.format("%.2f", dominantePorcentaje) + "%"; // porcentaje Dominante
        arregloresultado[4] = String.format("%.2f", subdominantePorcentaje) + "%"; // porcentaje Subdominante
        arregloresultado[5] = String.format("%.2f", apoyoPorcentaje) + "%"; // porcentaje apoyo
        arregloresultado[6] = String.valueOf(((dominantePorcentaje * 100d) / 100d));
        arregloresultado[7] = mentes[3];

        editor.putString(getResources().getString(R.string.shdominante), mentes[0]);
        editor.putString(getResources().getString(R.string.shsubdominante), mentes[1]);
        editor.putString(getResources().getString(R.string.shapoyo), mentes[2]);
        editor.putString(getResources().getString(R.string.shporcentajed), arregloresultado[3]);
        editor.putString(getResources().getString(R.string.shporcentajes), arregloresultado[4]);
        editor.putString(getResources().getString(R.string.shporcentajea), arregloresultado[5]);
        editor.putString(getResources().getString(R.string.shscore), arregloresultado[7]);
        editor.apply();

        adaptadorResult = new AdaptadorResult(this, arregloresultado);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adaptadorResult);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}