package com.thesis.quiz.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.thesis.quiz.quizapp.Common.Common;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private final int SPLASH_TIME_OUT = 4000;
    private Animation fadeInAnim, fromTopAnim;
    private ImageView splashScreenImg;
    private View layoutView;
    private static boolean hasRunned = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
    }

    public void goPlay(View view){
        final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.tap);
        mp.start();
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    public void goAboutUs(View view){
        final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.tap);
        mp.start();
        Intent intent = new Intent(this, AboutUsActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_close){
            new AlertDialog.Builder(this)
                    .setTitle("Exit Application")
                    .setMessage("Are you sure you want to close the game?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}
