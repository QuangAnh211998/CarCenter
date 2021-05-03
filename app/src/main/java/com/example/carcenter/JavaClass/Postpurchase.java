package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.R;

public class Postpurchase extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView postpurchase_price_range;
    private EditText postpurchase_content;
    private EditText postpurchase_title;
    private Button btndangtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpurchase);

        Anhxa();
        Eventclick();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    void Eventclick(){

        postpurchase_price_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btndangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Postpurchase.this, "Chào mừng bạn đến với CarCenter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void Anhxa(){
        toolbar = findViewById(R.id.toolbarPostPurchase);
        postpurchase_price_range = findViewById(R.id.post_price_range_tv);
        postpurchase_content = findViewById(R.id.post_title_edt);
        postpurchase_title = findViewById(R.id.post_content_edt);
        btndangtin = findViewById(R.id.postpurchase_btn);
    }
}
