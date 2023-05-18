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

        new Handler().postDelayed(() -> {
                Utils.intentTrans(IntroSlider.class, MainActivity.this);
                finish();
        }, 3000);
    }

}