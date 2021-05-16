package com.example.carcenter.common;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carcenter.Register.SignInFragment;
import com.example.carcenter.Register.SignUpFragment;
import com.example.carcenter.R;

public class DialogSignIn extends AppCompatDialogFragment {

    private Button SignInDialog;
    private Button SignUpDialog;
    private FrameLayout perentFrameLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_signin, null);

        SignInDialog = view.findViewById(R.id.signin_dialog_btn);
        SignUpDialog = view.findViewById(R.id.signup_dialog_btn);
        perentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);

        SignInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        SignUpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        return onCreateDialog(savedInstanceState);
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_right);
        fragmentTransaction.replace(perentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
