package com.example.tiposdementes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Arrays;
import java.util.List;

public class Preguntas extends AppCompatActivity {

    private Button btn;
    private ImageButton iniciar;
    private MediaPlayer mp;
    private String[] preguntas, respuestas;
    private String dominante = "";
    private String subdominante = "";
    private String apoyo = "";
    private int contadorPreguntas = 0, cuadrado, triangulo, circulo, valor, mayor = 0, submayor = 0, medio = 0, menor = 0, edad = 0;
    private TextView preguntastxt, preguntastitulo;
    private RadioButton r1, r2, r3, r4, r5;
    private Boolean selecciono = false;
    private LottieAnimationView lottieAnimationLogo;
    private CardView cardCentral;
    private RelativeLayout relaContador;
    private Double porcentajeD, porcentajeS, porcentajeA;
    public Utils utils;
    private ImageView cancelar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        btn = findViewById(R.id.boton_siguiente);
        iniciar = findViewById(R.id.play_song);
        preguntastxt = findViewById(R.id.preguntastxt);
        lottieAnimationLogo = findViewById(R.id.lotieContador);
        cardCentral = findViewById(R.id.cardHeader);
        relaContador = findViewById(R.id.contador);
        preguntastitulo = findViewById(R.id.textViews);
        cancelar = findViewById(R.id.cancelar);

        utils = new Utils(Preguntas.this);
        mp = MediaPlayer.create(this, R.raw.lonelines);
        mp.setLooping(true);
        mp.start();

        relaContador.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);
        cardCentral.setVisibility(View.GONE);
        lottieAnimationLogo.setVisibility(View.VISIBLE);

        startCheckAnimationLogo();

        r1 = findViewById(R.id.r_a);
        r2 = findViewById(R.id.r_b);
        r3 = findViewById(R.id.r_c);
        r4 = findViewById(R.id.r_d);
        r5 = findViewById(R.id.r_e);

        background(0);

        //preguntas para las edades
        SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);
        edad = Integer.parseInt(preferencias.getString(getResources().getString(R.string.fbusersedad), "0"));

        if (edad > 6 && edad < 10)
            preguntas = getResources().getStringArray(R.array.array_menores10);
        else if (edad >= 10 && edad < 18)
            preguntas = getResources().getStringArray(R.array.array_adolescentes);
        else if (edad >= 18)
            preguntas = getResources().getStringArray(R.array.array_adultos);
        else if (edad >= 0 && edad < 6)
            preguntas = getResources().getStringArray(R.array.array_primergrado);

        preguntastxt.setText(preguntas[0]);
        respuestas = new String[preguntas.length];

        iniciar.setOnClickListener(v -> {
            if (mp.isPlaying())
                mp.pause();
            else {
                mp.start();
                mp.setLooping(true);
            }
            iniciar.setBackgroundResource(mp.isPlaying() ? R.drawable.ic_volume_up : R.drawable.ic_volume_off);
        });

        cancelar.setOnClickListener(v -> toastSalir());

        r1.setOnClickListener(v -> {
            selecciono = true;
            background(1);
        });
        r2.setOnClickListener(v -> {
            selecciono = true;
            background(2);
        });
        r3.setOnClickListener(v -> {
            selecciono = true;
            background(3);
        });
        r4.setOnClickListener(v -> {
            selecciono = true;
            background(4);
        });
        r5.setOnClickListener(v -> {
            selecciono = true;
            background(5);
        });

        btn.setOnClickListener(v -> {
            background(0);
            if (selecciono) {
                if (contadorPreguntas == 0) {
                    cuadrado += valor;
                    respuestas[0] = String.valueOf(valor);
                }
                contadorPreguntas += 1;
                if (contadorPreguntas < preguntas.length) {
                    preguntastitulo.setText(getResources().getString(R.string.cardpregunta) + " " + (contadorPreguntas + 1) + "/27");
                    if (contadorPreguntas == 4 || contadorPreguntas == 7 || contadorPreguntas == 10 || contadorPreguntas == 11 || contadorPreguntas == 14 || contadorPreguntas == 15 || contadorPreguntas == 21 || contadorPreguntas == 26) {
                        cuadrado += valor;
                    } else if (contadorPreguntas == 1 || contadorPreguntas == 12 || contadorPreguntas == 16 || contadorPreguntas == 17 || contadorPreguntas == 18 || contadorPreguntas == 20 || contadorPreguntas == 22 || contadorPreguntas == 24 || contadorPreguntas == 25) {
                        triangulo += valor;
                    } else if (contadorPreguntas == 2 || contadorPreguntas == 3 || contadorPreguntas == 5 || contadorPreguntas == 6 || contadorPreguntas == 8 || contadorPreguntas == 9 || contadorPreguntas == 13 || contadorPreguntas == 19 || contadorPreguntas == 23) {
                        circulo += valor;
                    }
                    preguntastxt.setText(preguntas[contadorPreguntas]);
                    respuestas[contadorPreguntas] = String.valueOf(valor);
                } else {
                    List<String> myList = Arrays.asList(respuestas);
                    utils.guardarUltimasRespuestas(myList, this);
                    Intent results = new Intent(Preguntas.this, Results.class);
                    results.putExtra("mentes", proporsionalidad(circulo, cuadrado, triangulo));
                    startActivity(results);
                    finish();
                    mp.reset();
                }
            } else
                Toast.makeText(Preguntas.this, R.string.usenoopcion, Toast.LENGTH_SHORT).show();
            selecciono = false;
        });
    }

    //metodo para saber cuando termina el 3,2,1 go
    private void startCheckAnimationLogo() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1850);
        animator.addUpdateListener(animation -> lottieAnimationLogo.setProgress((Float) animation.getAnimatedValue()));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                relaContador.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                cardCentral.setVisibility(View.VISIBLE);
            }
        });
        if (lottieAnimationLogo.getProgress() == 0f) {
            animator.start();
        } else {
            lottieAnimationLogo.setProgress(0f);
        }
    }

    public String[] proporsionalidad(int circulo, int cuadrado, int triangulo) {
        String[] resultado = new String[6];
        if (circulo >= cuadrado && circulo >= triangulo) {
            mayor = circulo;
            dominante = getResources().getString(R.string.circulo);
        } else if (cuadrado >= circulo && cuadrado >= triangulo) {
            mayor = cuadrado;
            dominante = getResources().getString(R.string.cuadrado);
        } else {
            mayor = triangulo;
            dominante = getResources().getString(R.string.triangulo);
        }

        if (circulo <= cuadrado && circulo <= triangulo) {
            menor = circulo;
            apoyo = getResources().getString(R.string.circulo);
        } else if (cuadrado <= circulo && cuadrado <= triangulo) {
            menor = cuadrado;
            apoyo = getResources().getString(R.string.cuadrado);
        } else {
            menor = triangulo;
            apoyo = getResources().getString(R.string.triangulo);
        }

        if ((dominante.equals(getResources().getString(R.string.cuadrado)) && apoyo.equals(getResources().getString(R.string.circulo))) || (dominante.equals(getResources().getString(R.string.circulo)) && apoyo.equals(getResources().getString(R.string.cuadrado))))
            subdominante = getResources().getString(R.string.triangulo);
        else if ((dominante.equals(getResources().getString(R.string.cuadrado)) && apoyo.equals(getResources().getString(R.string.triangulo))) || (dominante.equals(getResources().getString(R.string.triangulo)) && apoyo.equals(getResources().getString(R.string.cuadrado))))
            subdominante = getResources().getString(R.string.circulo);
        else
            subdominante = getResources().getString(R.string.cuadrado);

        medio = (circulo + cuadrado + triangulo) - (menor + mayor);
        String aux = dominante;
        submayor = mayor;

        if (!(((mayor - menor) > 2) && ((mayor - menor) <= 4))) {
            if ((mayor - menor) >= 7) {
                dominante = getResources().getString(R.string.desproporcionado);
            } else {
                if (((mayor - medio) <= 2)) {
                    dominante = apoyo;
                    mayor = menor;
                    apoyo = subdominante;
                    menor = medio;
                    subdominante = aux;
                    medio = submayor;
                }
            }
        } else {
            if (((mayor - medio) <= 2)) {
                dominante = apoyo;
                mayor = menor;
                apoyo = subdominante;
                menor = medio;
                subdominante = aux;
                medio = submayor;
            }
        }
        resultado[0] = dominante;
        resultado[1] = subdominante;
        resultado[2] = apoyo;
        resultado[3] = String.valueOf(mayor);
        resultado[4] = String.valueOf(medio);
        resultado[5] = String.valueOf(menor);
        porcentajeD = ((Double.parseDouble(resultado[3])) / 45) * 100;
        porcentajeS = ((Double.parseDouble(resultado[4])) / 45) * 100;
        porcentajeA = ((Double.parseDouble(resultado[5])) / 45) * 100;
        utils.registrarTestFirebase(resultado, porcentajeD, porcentajeS, porcentajeA);

        return resultado;
    }

    public void background(int numero) {
        if (numero != 0) {
            valor = numero;
            r1.setBackgroundResource(numero == 1 ? R.drawable.respuesta_seleccionada : R.drawable.respuesta_sin_seleccionar);
            r1.setChecked(numero == 1);
            r2.setBackgroundResource(numero == 2 ? R.drawable.respuesta_seleccionada : R.drawable.respuesta_sin_seleccionar);
            r2.setChecked(numero == 2);
            r3.setBackgroundResource(numero == 3 ? R.drawable.respuesta_seleccionada : R.drawable.respuesta_sin_seleccionar);
            r3.setChecked(numero == 3);
            r4.setBackgroundResource(numero == 4 ? R.drawable.respuesta_seleccionada : R.drawable.respuesta_sin_seleccionar);
            r4.setChecked(numero == 4);
            r5.setBackgroundResource(numero == 5 ? R.drawable.respuesta_seleccionada : R.drawable.respuesta_sin_seleccionar);
            r5.setChecked(numero == 5);
        } else {
            r1.setBackgroundResource(R.drawable.respuesta_sin_seleccionar);
            r1.setChecked(false);
            r2.setBackgroundResource(R.drawable.respuesta_sin_seleccionar);
            r2.setChecked(false);
            r3.setBackgroundResource(R.drawable.respuesta_sin_seleccionar);
            r3.setChecked(false);
            r4.setBackgroundResource(R.drawable.respuesta_sin_seleccionar);
            r4.setChecked(false);
            r5.setBackgroundResource(R.drawable.respuesta_sin_seleccionar);
            r5.setChecked(false);
        }
    }

    public void toastSalir() {
        final RelativeLayout relativeLayout = findViewById(R.id.msgAccount);

        relativeLayout.setVisibility(View.VISIBLE);
        Button btnEntentido = findViewById(R.id.entendidoMsg);
        Button btnSalir = findViewById(R.id.noEntendidoMsg);

        relativeLayout.setOnClickListener(v -> {
            //nothing
        });
        btnEntentido.setOnClickListener(v -> relativeLayout.setVisibility(View.GONE));
        btnSalir.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }
}