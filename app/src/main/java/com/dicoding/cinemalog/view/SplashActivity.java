package com.dicoding.cinemalog.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.cinemalog.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //hide action bar
        getSupportActionBar().hide();

        ImageView imgLogo = findViewById(R.id.img_logo);
        Animation appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        imgLogo.setAnimation(appSplash);

        //Setting timer 3 detik untuk berpindah halaman
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent goToWatchList = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(goToWatchList);
                finish();
            }
        }, 3000);
    }
}
