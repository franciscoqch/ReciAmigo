package com.example.tiposdementes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Juego4 extends AppCompatActivity {
    private WebView wvInformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego4);
        wvInformacion = findViewById(R.id.webvviewInformacion);

        wvInformacion.setVisibility(View.VISIBLE);
        WebSettings webSettings = wvInformacion.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        wvInformacion.loadUrl("https://wordwall.net/es/embed/fb31ce7b0f5240b88ae43708501d89dd?themeId=1&templateId=5&fontStackId=0");
        wvInformacion.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
    }
}