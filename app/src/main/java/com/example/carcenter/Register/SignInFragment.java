package com.example.carcenter.Register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Fragment.ForgotPassWordFragment;
import com.example.carcenter.Model.Users;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignInFragment extends Fragment {

    private ImageButton exitSignInImage;
    private FrameLayout perentFrameLayout;
    private TextView signUpTextView;
    private TextView forgotPassWord;
    private EditText edt_name;
    private EditText edt_pass;
    private Button SignIn_Btn;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public SharedPreferences seveSignIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);

        if(Build.VERSION.SDK_INT>=22){
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        }

        exitSignInImage = view.findViewById(R.id.btnexit_SignIn);
        perentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);
        signUpTextView = view.findViewById(R.id.tvSignUp);
        forgotPassWord = view.findViewById(R.id.tvforgotPassword);
        edt_name = view.findViewById(R.id.edt_name);
        edt_pass = view.findViewById(R.id.edtpass);
        SignIn_Btn = view.findViewById(R.id.btnSignIn);

        seveSignIn = getContext().getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        exitSignInImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentSignUp(new SignUpFragment());
            }
        });

        forgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgotPassWordFragment());
            }
        });

        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SignIn_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String pass = edt_pass.getText().toString();
                SignIn(name, pass );
            }
        });
    }


    @SuppressLint("CheckResult")
    private void SignIn( String name, String pass){
        if (edt_name.getText().toString().matches(emailPattern)) {
                SignIn_Btn.setEnabled(false);
                SignIn_Btn.setTextColor(Color.argb(50, 255, 255, 255));
                APIRequest.SignIn(getActivity(), name, pass)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElement -> {
                            Gson gson = new Gson();
                            ArrayList<Users> users = gson.fromJson(jsonElement.getAsJsonArray(),new TypeToken<ArrayList<Users>>(){}.getType());
                            Log.e("signin", users.get(0).getUserEmail());

                            SharedPreferences.Editor editor = seveSignIn.edit();
                            editor.putInt("user_Id", users.get(0).getUserId());
                            editor.putString("user_Name", users.get(0).getUserName());
                            editor.putString("user_Email", users.get(0).getUserEmail());
                            editor.putString("user_Phone", users.get(0).getUserPhone());
                            editor.putString("user_Address", users.get(0).getUserAddress());
                            editor.putString("user_LivingArea", users.get(0).getUserLivingArea());
                            editor.putString("user_PassWord", users.get(0).getUserPassWord());
                            editor.putString("user_Type", users.get(0).getUserType());
                            editor.commit();

                            EventBus.getDefault().post(true,"loginSuccess");

                            getActivity().finish();
                        }, throwable -> {
                            throwable.printStackTrace();
                            Toast.makeText(getContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                        });
        }else {
            edt_name.setError("Email không đúng");
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_right);
        fragmentTransaction.replace(perentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setFragmentSignUp(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_right);
        fragmentTransaction.replace(perentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(edt_name.getText().toString())) {
            if (edt_pass.getText().length() >= 8) {
                SignIn_Btn.setEnabled(true);
                SignIn_Btn.setTextColor(Color.rgb(255, 255, 255));
            } else {
                SignIn_Btn.setEnabled(false);
                SignIn_Btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            SignIn_Btn.setEnabled(false);
            SignIn_Btn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

}