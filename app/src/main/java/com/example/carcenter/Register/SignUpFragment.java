package com.example.carcenter.Register;

import android.annotation.SuppressLint;
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

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Custom.BottomSheetProvince;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpFragment extends Fragment {

    private List<ProvinceModel> provinceModelList;

    private ImageButton exitSignUpImageButton;
    private FrameLayout perentFrameLayout;
    private TextView livingArea_tv;
    private EditText name_edt;
    private EditText phone_edt;
    private EditText email_edt;
    private EditText password_edt;
    private EditText confirm_password_edt;
    private EditText address_edt;
    private Button SignUp_btn;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String phonePattern = "[0]+[0-9&&[^01246]]+[0-9]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        if(Build.VERSION.SDK_INT>=22){
//            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));
        }

        exitSignUpImageButton = view.findViewById(R.id.btnexit_SignUp);
        perentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);
        livingArea_tv = view.findViewById(R.id.signup_livingArea_tv);
        name_edt = view.findViewById(R.id.edt_name);
        phone_edt = view.findViewById(R.id.edt_phone);
        email_edt = view.findViewById(R.id.edt_email);
        password_edt = view.findViewById(R.id.edt_pass1);
        confirm_password_edt = view.findViewById(R.id.edt_pass2);
        address_edt = view.findViewById(R.id.edt_address);
        SignUp_btn = view.findViewById(R.id.btn_SignUp);

        provinceModelList = new ArrayList<>();
        getDataProvince();
        ShowBottomSheet();
        return view;
    }



    ////// show bottom sheet
    private void ShowBottomSheet(){
        livingArea_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetProvince bottomSheetProvince = new BottomSheetProvince(provinceModelList, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        livingArea_tv.setText(name);
                    }
                });
                bottomSheetProvince.show(getFragmentManager(), bottomSheetProvince.getTag());
            }
        });
    }



    ////// get Province
    @SuppressLint("CheckResult")
    private void getDataProvince(){
        APIRequest.getProvince(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("province",jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProvinceModel> provinceModels = gson.fromJson(jsonElement.getAsJsonArray(),
                            new TypeToken<ArrayList<ProvinceModel>>(){}.getType());
                    provinceModelList.addAll(provinceModels);
                }, throwable -> {

                });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exitSignUpImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });


        livingArea_tv.addTextChangedListener(new TextWatcher() {
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
        email_edt.addTextChangedListener(new TextWatcher() {
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
        name_edt.addTextChangedListener(new TextWatcher() {
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
        phone_edt.addTextChangedListener(new TextWatcher() {
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
        password_edt.addTextChangedListener(new TextWatcher() {
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
        confirm_password_edt.addTextChangedListener(new TextWatcher() {
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

        address_edt.addTextChangedListener(new TextWatcher() {
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

        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String living = livingArea_tv.getText().toString();
                String name = name_edt.getText().toString();
                String email = email_edt.getText().toString();
                String phone = phone_edt.getText().toString();
                String address = address_edt.getText().toString();
                String pass = password_edt.getText().toString();

                SignUp(living, name, email, phone, address, pass);
            }
        });

    }


    ////// th???c hi???n ????ng k?? t??i kho???n
    @SuppressLint("CheckResult")
    private void SignUp(String livingarea, String name, String email, String phone, String address, String pass){
        if (email_edt.getText().toString().matches(emailPattern)) {
            if(phone_edt.getText().toString().matches(phonePattern)) {
                if (password_edt.getText().toString().equals(confirm_password_edt.getText().toString())) {
                    SignUp_btn.setEnabled(false);
                    SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));

                    APIRequest.SignUp(getActivity(), livingarea, name, email, phone, address, pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(jsonElement -> {
                                Log.e("SignUp", jsonElement.toString());
                                JSONObject jsonObject = new JSONObject(jsonElement.toString());
                                String status = jsonObject.getString("status");
                                if (status.equals("emailalrealy")) {
                                    Toast.makeText(getContext(), "Email ???? t???n t???i! \nVui l??ng nh???p email kh??c", Toast.LENGTH_SHORT).show();
                                } else if (status.equals("success")) {
                                    getActivity().finish();
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                Toast.makeText(getContext(), "????ng k?? th???t b???i", Toast.LENGTH_LONG).show();
                            });

                } else {
                    confirm_password_edt.setError("M???t kh???u kh??ng kh???p!");
                }
            }else {
                phone_edt.setError("S??T kh??ng h???p l???!");
            }
        }else {
            email_edt.setError("Email kh??ng h???p l???!");
        }

    }


    ////// set chuy???n m??n h??nh fragment
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_left);
        fragmentTransaction.replace(perentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    ////// check ??i???u ki???n hi???n th??? button
    private void checkInput() {
        if(!TextUtils.isEmpty(livingArea_tv.getText())){
            if (!TextUtils.isEmpty(email_edt.getText().toString())) {
                if (!TextUtils.isEmpty(name_edt.getText())) {
                    if(phone_edt.length()==10){
                        if ( password_edt.length() >= 8) {
                            if (!TextUtils.isEmpty(confirm_password_edt.getText().toString())) {
                               if (!TextUtils.isEmpty(address_edt.getText())){
                                   SignUp_btn.setEnabled(true);
                                   SignUp_btn.setTextColor(Color.rgb(255, 255, 255));
                               }else {
                                   SignUp_btn.setEnabled(false);
                                   SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
                               }
                            } else {
                                SignUp_btn.setEnabled(false);
                                SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
                            }
                        } else {
                            SignUp_btn.setEnabled(false);
                            SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
                        }
                    }else {
                        SignUp_btn.setEnabled(false);
                        SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    SignUp_btn.setEnabled(false);
                    SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                SignUp_btn.setEnabled(false);
                SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        }else {
            SignUp_btn.setEnabled(false);
            SignUp_btn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }
}