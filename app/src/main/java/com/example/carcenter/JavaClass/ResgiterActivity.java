package com.example.carcenter.JavaClass;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carcenter.R;

public class ResgiterActivity extends AppCompatActivity {

    private ImageButton btnthoatdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        btnthoatdk = findViewById(R.id.btnthoatdk);

        btnthoatdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                startActivity(new Intent(getApplication(), DangNhap.class));
            }
        });

    }
}
