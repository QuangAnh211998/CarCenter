package com.example.carcenter.JavaClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carcenter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView tvDangky, tvquenmk;
    private ImageButton btnthoatdn;
    private EditText edtname, edtpass;
    private Button btndangnhap;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Anhxa();
        EventClick();


    }

    public void EventClick(){
        tvDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), RegisterActivity.class));
            }
        });

        btnthoatdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
login();
            }
        });
        tvquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Chức năng này hiện đang bảo trì, vui lòng quay lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        firebaseAuth.signInWithEmailAndPassword(edtname.getText().toString(),edtpass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent);
                       finish();
                       Toast.makeText(LoginActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();

                   }else {
                       String error=task.getException().getMessage();
                       Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                   }
                    }
                });
    }

    public void Anhxa(){
        tvDangky = findViewById(R.id.tvdangky);
        btnthoatdn = findViewById(R.id.btnthoatdn);
        edtname = findViewById(R.id.edtname);
        edtpass = findViewById(R.id.edtpass);
        tvquenmk = findViewById(R.id.tvquenmk);
        btndangnhap = findViewById(R.id.btndangnhap);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
