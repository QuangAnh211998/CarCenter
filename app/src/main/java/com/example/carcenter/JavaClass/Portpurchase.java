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

public class Portpurchase extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvmuctien;
    private EditText edttieude, edtnoidung;
    private Button btndangtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portpurchase);

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

        tvmuctien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btndangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Portpurchase.this, "Chào mừng bạn đến với CarCenter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void Anhxa(){
        toolbar = findViewById(R.id.toolbarbdtmua);
        tvmuctien = findViewById(R.id.tvmuctien);
        edtnoidung = findViewById(R.id.edtnoidungmua);
        edttieude = findViewById(R.id.edttieudemua);
        btndangtin = findViewById(R.id.btndtmua);
    }
}
