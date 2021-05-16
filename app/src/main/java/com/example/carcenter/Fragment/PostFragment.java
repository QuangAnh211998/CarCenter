package com.example.carcenter.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.carcenter.Register.RegisterActivity;
import com.example.carcenter.R;

import org.simple.eventbus.EventBus;

import static com.example.carcenter.Register.RegisterActivity.setSignUpFragment;

public class PostFragment extends Fragment {

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    private EditText edthangxe, edtphienban;
    private Button postsale_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        EventBus.getDefault().register(this);
        saveSignIn = getContext().getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        postsale_btn = view.findViewById(R.id.postsale_btn);

        EventButton();

        return view;
    }

    private void EventButton(){
        String email = saveSignIn.getString("user_Email", "");
        postsale_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){

                }else {
                    DialogSignIn();
                }
            }
        });
    }

    private void DialogSignIn(){
        Dialog signInDialog = new Dialog(getContext());
        signInDialog.setContentView(R.layout.dialog_signin);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button signIn_btn = signInDialog.findViewById(R.id.signin_dialog_btn);
        Button signUp_btn = signInDialog.findViewById(R.id.signup_dialog_btn);
        Intent registerIntent = new Intent(getContext(), RegisterActivity.class);
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
        signInDialog.show();
    }
}
