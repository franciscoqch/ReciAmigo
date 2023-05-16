package com.example.tiposdementes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Animation zoom;
    private ImageView img;
    private String primerves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img = findViewById(R.id.imagen);
        img.startAnimation(zoom);

        SharedPreferences prefe = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);
        primerves = prefe.getString(getResources().getString(R.string.shinstalada), getResources().getString(R.string.shtrue));

        new Handler().postDelayed(() -> {
            if (primerves.equals("") || primerves.equals(" ") || primerves.equals(getResources().getString(R.string.shvalordefault)) || primerves.equals(getResources().getString(R.string.shtrue))) {
                Utils.intentTrans(IntroSlider.class, MainActivity.this);
                finish();
            } else if (primerves.equals(getResources().getString(R.string.shfalse))) {
                Utils.intentTrans(LoginActivity.class, MainActivity.this);
                finish();
            }
        }, 3000);
    }

}