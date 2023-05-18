package com.example.tiposdementes;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class IntroSlider extends AppCompatActivity {

    private static final short REQUEST_CODE = 6545;
    private TextView tvNext;
    private ViewPager viewPager;
    private LinearLayout layoutDots;
    private int[] layouts;
    private TextView[] dots;
    private MyViewPagerAdapter viewPagerAdapter;
    private WebView wvInformacion;
    private Utils utils;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        tvNext = findViewById(R.id.tvNext);
        viewPager = findViewById(R.id.viewPager);
        layoutDots = findViewById(R.id.layoutDots);
        wvInformacion = findViewById(R.id.webvviewInformacion);
        utils = new Utils(IntroSlider.this);
        utils.registrarValidador();

        if (false) {
            progressDialog = new ProgressDialog(IntroSlider.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            tvNext.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            layoutDots.setVisibility(View.GONE);
            wvInformacion.setVisibility(View.VISIBLE);
            WebSettings webSettings = wvInformacion.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            wvInformacion.loadUrl(getResources().getString(R.string.urlinformacion));
            wvInformacion.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressDialog.dismiss();
                    wvInformacion.setVisibility(View.VISIBLE);
                }
            });
        } else
            wvInformacion.setVisibility(View.GONE);

        layouts = new int[]{
                R.layout.intro_one,
                R.layout.intro_two,
                R.layout.intro_three
        };

        tvNext.setOnClickListener(v -> {
            int current = getItem();
            if (current < layouts.length) {
                viewPager.setCurrentItem(current);
            } else {
                launchLoginScreen();
            }
        });

        viewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        addBottomDots(0);
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            tvNext.setText(R.string.itsiguiente);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] activeColors = getResources().getIntArray(R.array.active);
        int[] inActiveColors = getResources().getIntArray(R.array.inactive);
        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(50);
            dots[i].setTextColor(inActiveColors[currentPage]);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(activeColors[currentPage]);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private int getItem() {
        return viewPager.getCurrentItem() + 1;
    }

    private void launchLoginScreen() {
        Utils.intentTrans(MenuPrincipal.class, IntroSlider.this);
        finish();
    }


    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    private void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_CODE);
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted! Do the work

            } else {
                // permission denied!
                Toast.makeText(this, getResources().getString(R.string.permisos), Toast.LENGTH_LONG).show();
            }
        }
    }
}