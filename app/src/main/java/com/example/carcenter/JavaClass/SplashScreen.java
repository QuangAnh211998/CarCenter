package com.example.carcenter.JavaClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carcenter.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //sleep 3 seconds
        SystemClock.sleep(1000);
        startActivity(new Intent(getApplication(), MainActivity.class));
    }

}