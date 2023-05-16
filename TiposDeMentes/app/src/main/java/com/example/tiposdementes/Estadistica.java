package com.example.tiposdementes;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Estadistica extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ArrayList<TestEstadistica> listHome;
    private double contadorCD = 0.0, contadorTD = 0.0, contadorQD = 0.0, contadorGe = 0.0;
    private TextView txtprimercirculo, txtsegundocirculo, txttercercirculo, menteprimercirculo, mentesegundocirculo, mentetercercirculo;
    private ProgressBar progressBar1, progressBar2, progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);

        recyclerView = findViewById(R.id.recyclerestadistica);
        txtprimercirculo = findViewById(R.id.txt_progress);
        txtsegundocirculo = findViewById(R.id.txt_progress2);
        txttercercirculo = findViewById(R.id.txt_progress3);
        menteprimercirculo = findViewById(R.id.menteprimercirculo);
        mentesegundocirculo = findViewById(R.id.mentesegundocirculo);
        mentetercercirculo = findViewById(R.id.mentetercercirculo);
        progressBar1 = findViewById(R.id.progress_bar);
        progressBar2 = findViewById(R.id.progress_bar2);
        progressBar3 = findViewById(R.id.progress_bar3);

        db = FirebaseFirestore.getInstance();
        listHome = new ArrayList<>();
        consultarPorcentajes();
    }

    public void consultarPorcentajes() {
        db.collection(getResources().getString(R.string.fbtest)).orderBy(getResources().getString(R.string.fbusersfecha), Query.Direction.ASCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                listHome.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.i("Paso",queryDocumentSnapshots.toString());
                    contadorGe = contadorGe + 1;
                    String nombre = doc.getString(getResources().getString(R.string.fbtestnombre));
                    String carrera = doc.getString(getResources().getString(R.string.fbtestcarrera));
                    String mente = doc.getString(getResources().getString(R.string.fbtestmentedominante));
                    if (contadorGe <= 10) {
                        listHome.add(new TestEstadistica(nombre, carrera, mente));
                        AdaapterEstadistica adapterEstadistica = new AdaapterEstadistica(listHome, Estadistica.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapterEstadistica);
                    }

                    if (mente.equals(getResources().getString(R.string.circulo)))
                        contadorCD++;
                    else if (mente.equals(getResources().getString(R.string.cuadrado)))
                        contadorQD++;
                    else if (mente.equals(getResources().getString(R.string.triangulo)))
                        contadorTD++;
                }
            }
            // contadorcd = 1
            //contadorqd = 2
            //contadortd = 3
            if (contadorCD > contadorQD) {
                if (contadorCD > contadorTD) {
                    //mayor contadorcd
                    menteprimercirculo.setText(getResources().getString(R.string.circulo));
                    progressBar1.setProgress((int) ((contadorCD / contadorGe) * 100));
                    txtprimercirculo.setText(String.format("%.2f", (contadorCD / contadorGe) * 100) + "%");
                    if (contadorTD > contadorQD) {
                        mentesegundocirculo.setText(getResources().getString(R.string.triangulo));
                        mentetercercirculo.setText(getResources().getString(R.string.cuadrado));
                        progressBar2.setProgress((int) ((contadorTD / contadorGe) * 100));
                        progressBar3.setProgress((int) ((contadorQD / contadorGe) * 100));
                        txtsegundocirculo.setText(String.format("%.2f", (contadorTD / contadorGe) * 100) + "%");
                        txttercercirculo.setText(String.format("%.2f", (contadorQD / contadorGe) * 100) + "%");
                    } else {
                        mentesegundocirculo.setText(getResources().getString(R.string.cuadrado));
                        mentetercercirculo.setText(getResources().getString(R.string.triangulo));
                        progressBar2.setProgress((int) ((contadorQD / contadorGe) * 100));
                        progressBar3.setProgress((int) ((contadorTD / contadorGe) * 100));
                        txtsegundocirculo.setText(String.format("%.2f", (contadorQD / contadorGe) * 100) + "%");
                        txttercercirculo.setText(String.format("%.2f", (contadorTD / contadorGe) * 100) + "%");
                    }

                } else {
                    //mayor contadortd
                    menteprimercirculo.setText(getResources().getString(R.string.triangulo));
                    mentesegundocirculo.setText(getResources().getString(R.string.circulo));
                    mentetercercirculo.setText(getResources().getString(R.string.cuadrado));
                    progressBar1.setProgress((int) ((contadorTD / contadorGe) * 100));
                    progressBar2.setProgress((int) ((contadorCD / contadorGe) * 100));
                    progressBar3.setProgress((int) ((contadorQD / contadorGe) * 100));
                    txtprimercirculo.setText(String.format("%.2f", (contadorTD / contadorGe) * 100) + "%");
                    txtsegundocirculo.setText(String.format("%.2f", (contadorCD / contadorGe) * 100) + "%");
                    txttercercirculo.setText(String.format("%.2f", (contadorQD / contadorGe) * 100) + "%");
                }
            } else if (contadorQD > contadorTD) {
                //mayor contdorqd
                menteprimercirculo.setText(getResources().getString(R.string.cuadrado));
                progressBar1.setProgress((int) ((contadorQD / contadorGe) * 100));
                txtprimercirculo.setText(String.format("%.2f", (contadorQD / contadorGe) * 100) + "%");
                if (contadorTD > contadorCD) {
                    mentesegundocirculo.setText(getResources().getString(R.string.triangulo));
                    mentetercercirculo.setText(getResources().getString(R.string.circulo));
                    progressBar2.setProgress((int) ((contadorTD / contadorGe) * 100));
                    progressBar3.setProgress((int) ((contadorCD / contadorGe) * 100));
                    txtsegundocirculo.setText(String.format("%.2f", (contadorTD / contadorGe) * 100) + "%");
                    txttercercirculo.setText(String.format("%.2f", (contadorCD / contadorGe) * 100) + "%");
                } else {
                    mentesegundocirculo.setText(getResources().getString(R.string.circulo));
                    mentetercercirculo.setText(getResources().getString(R.string.triangulo));
                    progressBar2.setProgress((int) ((contadorCD / contadorGe) * 100));
                    progressBar3.setProgress((int) ((contadorTD / contadorGe) * 100));
                    txtsegundocirculo.setText(String.format("%.2f", (contadorCD / contadorGe) * 100) + "%");
                    txttercercirculo.setText(String.format("%.2f", (contadorTD / contadorGe) * 100) + "%");
                }
            } else {
                //mayor contadortd
                menteprimercirculo.setText(getResources().getString(R.string.triangulo));
                mentesegundocirculo.setText(getResources().getString(R.string.cuadrado));
                mentetercercirculo.setText(getResources().getString(R.string.circulo));
                progressBar1.setProgress((int) ((contadorTD / contadorGe) * 100));
                progressBar2.setProgress((int) ((contadorQD / contadorGe) * 100));
                progressBar3.setProgress((int) ((contadorCD / contadorGe) * 100));
                txtprimercirculo.setText(String.format("%.2f", (contadorTD / contadorGe) * 100) + "%");
                txtsegundocirculo.setText(String.format("%.2f", (contadorQD / contadorGe) * 100) + "%");
                txttercercirculo.setText(String.format("%.2f", (contadorCD / contadorGe) * 100) + "%");
            }
        });
    }
}