package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carcenter.R;

public class ChangeInformationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvnoisong;
    private EditText edthoten, edtemail, edtsdt, edtdiachi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chageinformation);

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
        toolbar = findViewById(R.id.toolbardoittcn);
        tvnoisong = findViewById(R.id.tvnoisong);
        edthoten = findViewById(R.id.edthoten);
        edtemail = findViewById(R.id.edtemail);
        edtsdt = findViewById(R.id.edtsdt);
        edtdiachi = findViewById(R.id.edtdiachi);
    }
}
