package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carcenter.R;

public class ResetInformationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView reset_livingArea;
    private EditText reset_name;
    private EditText reset_phone;
    private EditText reset_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetinformation);

        Anhxa();
        EventToolbar();

    }

    void  EventToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void Anhxa(){
        toolbar = findViewById(R.id.toolbarResetUser);
        reset_livingArea = findViewById(R.id.reset_livingArea_tv);
        reset_name = findViewById(R.id.reset_name_edt);
        reset_phone = findViewById(R.id.reset_phone_edt);
        reset_address = findViewById(R.id.reset_address_edt);
    }
}
