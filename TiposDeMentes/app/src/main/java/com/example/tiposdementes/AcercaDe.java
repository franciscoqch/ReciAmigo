package com.example.tiposdementes;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AcercaDe extends AppCompatActivity {

    private Button volver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        volver1 = findViewById(R.id.btnVolver1);
        volver1.setOnClickListener(v -> finish());
    }

}