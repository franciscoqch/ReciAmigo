package com.example.tiposdementes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {
    private LinearLayout rlMiTest, rlEliminar;
    private String recibidocod = "", recibidocarrera = "";
    private TextView txtnombre, txtcorreo, txtcarrera;
    private ImageView back;
    private FirebaseFirestore db;
    public static Map<String, Object> map;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        rlMiTest = findViewById(R.id.relaMiTest);
        rlEliminar = findViewById(R.id.relaEliminar);
        txtnombre = findViewById(R.id.txtnombreprofile);
        txtcorreo = findViewById(R.id.txtcorreoprofile);
        txtcarrera = findViewById(R.id.txtcarreraprofile);
        back = findViewById(R.id.btnback);
        db = FirebaseFirestore.getInstance();
        map = new HashMap<>();
        SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);

        back.setOnClickListener(v -> finish());

        rlMiTest.setOnClickListener(v -> startActivity(new Intent(Perfil.this, MisTest.class)));
        rlEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
            builder.setTitle(getResources().getString(R.string.tituloalertborrar));
            builder.setMessage(getResources().getString(R.string.confirmacionalertborrar));

            builder.setPositiveButton(getResources().getString(R.string.btnaceptar), (dialog, which) -> {
                map.put(getResources().getString(R.string.fbusersestado), "false");
                db.collection(getResources().getString(R.string.fbusers)).document(preferencias.getString(getResources().getString(R.string.shcodigo), "null")).update(map);
                Utils.intentTrans(LoginActivity.class, Perfil.this);
                finish();
            });

            builder.setNegativeButton(getResources().getString(R.string.btncancelar), (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        SharedPreferences prefe = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);
        recibidocod = prefe.getString(getResources().getString(R.string.shcodigo), getResources().getString(R.string.shvalordefault));
        recibidocarrera = prefe.getString(getResources().getString(R.string.shcarrera), getResources().getString(R.string.shvalordefault));

        txtnombre.setText(prefe.getString(getResources().getString(R.string.shnombre), getResources().getString(R.string.shvalordefault)));
        txtcorreo.setText(prefe.getString(getResources().getString(R.string.shcorreo), getResources().getString(R.string.shvalordefault)));
        txtcarrera.setText(recibidocarrera + " - " + recibidocod);

    }
}