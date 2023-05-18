package com.example.tiposdementes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Juego5 extends AppCompatActivity {
    private WebView wvInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego5);
        wvInformacion = findViewById(R.id.webvviewInformacion);

        wvInformacion.setVisibility(View.VISIBLE);
        WebSettings webSettings = wvInformacion.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        wvInformacion.loadUrl("https://wordwall.net/es/embed/dbd21d3b55a549458ba106fa2cf5c163?themeId=22&templateId=45&fontStackId=0");
        wvInformacion.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
    }
}