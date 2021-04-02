package com.example.carcenter.JavaClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carcenter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
private FirebaseUser firebaseUser;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();
        //sleep 3 seconds
        SystemClock.sleep(1000);
    }

    @Override
    protected void onStart() {
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(SplashScreen.this, SingInActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();

        }
        super.onStart();
    }
}