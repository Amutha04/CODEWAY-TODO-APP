package com.example.todoapp;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;





@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        final Intent i = new Intent(SplashActivity.this, MainActivity.class);
        new Handler().postDelayed(()-> {
                startActivity(i);

                finish();

        },2000);

    }

}
