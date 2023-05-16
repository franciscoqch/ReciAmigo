package com.example.tiposdementes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MisTest extends AppCompatActivity {

    private String[] preguntas, respuestas;
    private TextView preguntastxt, preguntastitulo, respuestastxt;
    private Button btn;
    private int contadorPreguntas = 0, edad = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_test);

        preguntastitulo = findViewById(R.id.textViews);
        preguntastxt = findViewById(R.id.preguntastxt);
        respuestastxt = findViewById(R.id.respuestastxt);
        btn = findViewById(R.id.boton_siguiente);

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
        respuestas = consultarUltimasRespuestas().toArray(new String[0]);
        respuestastxt.setText(respuestas[0]);

        btn.setOnClickListener(v -> {
            if (contadorPreguntas == 0 || contadorPreguntas < 28) {
                contadorPreguntas += 1;
            }
            if (contadorPreguntas < preguntas.length) {
                preguntastitulo.setText("Pregunta " + (contadorPreguntas + 1) + "/27");
                preguntastxt.setText(preguntas[contadorPreguntas]);
                respuestastxt.setText(respuestas[contadorPreguntas]);
            } else {
                finish();
                Toast.makeText(MisTest.this, R.string.resultcompletos, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //este metodo prohibido moverlo
    public ArrayList<String> consultarUltimasRespuestas() {
        ArrayList list = new ArrayList();
        SharedPreferences preferences = getSharedPreferences("lista", Context.MODE_PRIVATE);
        list.clear();
        for (int i = 0; i < 27; i++) {
            list.add(preferences.getString("Status_" + i, null));
        }
        return list;
    }
}