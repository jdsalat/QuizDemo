package com.machinetest.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.machinetest.quiz.R;
import com.machinetest.quiz.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_DISPLAY_LENGTH);
    }
}

