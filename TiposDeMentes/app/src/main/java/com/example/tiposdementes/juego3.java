package com.example.tiposdementes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class juego3 extends AppCompatActivity {
    private WebView wvInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego3);
        wvInformacion = findViewById(R.id.webvviewInformacion);

        wvInformacion.setVisibility(View.VISIBLE);
        WebSettings webSettings = wvInformacion.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        wvInformacion.loadUrl("https://wordwall.net/es/embed/22a999153b6e4243924990a5759268b3?themeId=1&templateId=2&fontStackId=0");
        wvInformacion.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
    }
}