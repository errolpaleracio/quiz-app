package com.thesis.quiz.quizapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private final int SPLASH_TIME_OUT = 4000;
    private Animation fadeInAnim, fromTopAnim;
    private ImageView splashScreenImg;
    private LayoutInflater inflater;
    private View layoutView;
    private static boolean hasRunned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasRunned) {
            setContentView(R.layout.activity_splash);
            hasRunned = true;
        }
        inflater = getLayoutInflater();
        fromTopAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in2);

        layoutView = inflater.inflate(R.layout.activity_main, null, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutView.startAnimation(fromTopAnim);
                setContentView(layoutView);
                hasRunned = true;
            }
        }, SPLASH_TIME_OUT);



        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        splashScreenImg = (ImageView)findViewById(R.id.ivSplashScreenImg);
        splashScreenImg.startAnimation(fadeInAnim);
    }

    public void goPlay(View view){
        final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.tap);
        mp.start();
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
