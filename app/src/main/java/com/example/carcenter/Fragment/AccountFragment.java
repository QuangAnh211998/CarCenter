package com.example.carcenter.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.carcenter.JavaClass.ResetInformationActivity;
import com.example.carcenter.JavaClass.ResetPasswordActivity;
import com.example.carcenter.JavaClass.RegisterActivity;
import com.example.carcenter.R;

public class AccountFragment extends Fragment {
    private Button btndangnhap;
    private LinearLayout lodoimk, lodoittcn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        btndangnhap = view.findViewById(R.id.btndangnhapac);
        lodoimk = view.findViewById(R.id.lodoimk);
        lodoittcn = view.findViewById(R.id.lodoittcn);

        EventButton();
        EventLinearLayout();
        return view;
    }

    void EventButton(){
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
            }
        });
    }

    void EventLinearLayout(){
        lodoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetPasswordActivity.class));
            }
        });

        lodoittcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetInformationActivity.class));
            }
        });
    }
}
