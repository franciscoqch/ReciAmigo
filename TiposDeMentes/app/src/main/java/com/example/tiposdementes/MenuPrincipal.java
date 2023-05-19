package com.example.tiposdementes;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tiposdementes.databinding.ActivityMenuPrincipalBinding;

public class MenuPrincipal extends AppCompatActivity {

    private ActivityMenuPrincipalBinding binding;
    private LinearLayout scroll1;
    private ScrollView scroll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        scroll1 = findViewById(R.id.includeHomefirst);
        scroll2 = findViewById(R.id.includeHomeSecond);

        binding.includeHomefirst.card1.setOnClickListener(v -> {
            Utils.intentTrans(aprender.class, MenuPrincipal.this);
        });

        binding.includeHomefirst.card2.setOnClickListener(v -> {
            scroll2.setVisibility(View.VISIBLE);
            scroll1.setVisibility(View.GONE);
            binding.arrowBack.setVisibility(View.VISIBLE);
        });

        binding.includeHomefirst.card3.setOnClickListener(v -> {
            Utils.intentTrans(LoginActivity.class, MenuPrincipal.this);
        });

        binding.arrowBack.setOnClickListener(v -> {
            scroll1.setVisibility(View.VISIBLE);
            scroll2.setVisibility(View.GONE);
            binding.arrowBack.setVisibility(View.GONE);
        });

        binding.includeHomeSecond.juego1.setOnClickListener(v -> {
            Utils.intentTrans(Juegos.class, MenuPrincipal.this);
        });

        binding.includeHomeSecond.juego2.setOnClickListener(v -> {
            Utils.intentTrans(Juego2.class, MenuPrincipal.this);
        });

        binding.includeHomeSecond.juego3.setOnClickListener(v -> {
            Utils.intentTrans(juego3.class, MenuPrincipal.this);
        });

        binding.includeHomeSecond.juego4.setOnClickListener(v -> {
            Utils.intentTrans(Juego4.class, MenuPrincipal.this);
        });

        binding.includeHomeSecond.juego5.setOnClickListener(v -> {
            Utils.intentTrans(Juego5.class, MenuPrincipal.this);
        });

        binding.includeHomeSecond.juego6.setOnClickListener(v -> {
            Utils.intentTrans(Juego6.class, MenuPrincipal.this);
        });
    }
}