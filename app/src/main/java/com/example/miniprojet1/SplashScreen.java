package com.example.miniprojet1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView appname;
    Animation splash_top,splash_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        logo=findViewById(R.id.logo);
        appname=findViewById(R.id.appname);

        //For animation
        splash_top=AnimationUtils.loadAnimation(this, R.anim.splash_top);
        splash_bottom= AnimationUtils.loadAnimation(this, R.anim. splash_bottom);

        logo.setAnimation(splash_top);
        appname.setAnimation(splash_bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        },1500);
    }
}