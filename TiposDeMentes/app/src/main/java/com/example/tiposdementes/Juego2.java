package com.example.tiposdementes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Juego2 extends AppCompatActivity {
    private WebView wvInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego2);
        wvInformacion = findViewById(R.id.webvviewInformacion);

        wvInformacion.setVisibility(View.VISIBLE);
        WebSettings webSettings = wvInformacion.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        wvInformacion.loadUrl("https://wordwall.net/es/embed/29e8379eed354496a6a693b163c653b0?themeId=2&templateId=25&fontStackId=0");
        wvInformacion.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
    }
}