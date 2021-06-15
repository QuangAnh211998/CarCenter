package com.example.carcenter.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.BottomSheetCategoryAdapter;
import com.example.carcenter.Adapter.BottomSheetCompanyAdapter;
import com.example.carcenter.Adapter.ImageAdapter;
import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Custom.BottomSheetCategory;
import com.example.carcenter.Custom.BottomSheetCompany;
import com.example.carcenter.Custom.BottomSheetSelected;
import com.example.carcenter.Custom.Custom_Price;
import com.example.carcenter.Custom.FileUtils;
import com.example.carcenter.JavaClass.SearchActivity;
import com.example.carcenter.Model.CategoryModel;
import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.Network.BaseAPIRequest;
import com.example.carcenter.Register.RegisterActivity;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.carcenter.Register.RegisterActivity.setSignUpFragment;

public class PostFragment extends Fragment {

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    private ImageButton imageButton_search;
    private Button postsale_btn;
    private Button post_image_btn;
    private Button post_contact_btn;
    private TextView post_company_tv;
    private TextView post_name_tv;
    private TextView post_year_tv;
    private TextView post_madein_tv;
    private TextView post_status_tv;
    private TextView post_type_tv;
    private TextView post_outside_tv;
    private TextView post_inside_tv;
    private TextView post_gear_tv;
    private TextView post_drivetrain_tv;
    private TextView post_fuel_tv;
    private TextView user_province_tv;
    private EditText post_airbag_edt;
    private EditText post_abs_edt;
    private EditText post_eba_edt;
    private EditText post_esp_edt;
    private EditText post_antislip_edt;
    private EditText post_reverse_warning_edt;
    private EditText post_antitheft_edt;
    private EditText post_version_edt;
    private EditText post_kmwent_edt;
    private EditText post_price_edt;
    private EditText post_door_edt;
    private EditText post_seat_edt;
    private EditText post_consume_edt;
    private EditText post_content_edt;
    private EditText user_name_edt;
    private EditText user_phone_edt;
    private EditText user_address_edt;
    private RecyclerView recyclerView_image;

    private ImageAdapter imageAdapter;
    private List<CompanyModel> companyModelList;
    private List<CategoryModel> categoryModelList;

    String company, name, version, year, madein, status, kmwent, type, price, outside, inside, reverse;
    String door, seat, gear, drivetrain, fuel, consume, content, airbag, abs, eba, esp, antislip, antitheft;

    List<String> listRealpath;
    File file;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        EventBus.getDefault().register(this);
        saveSignIn = getContext().getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        imageButton_search = view.findViewById(R.id.search_post_image);
        postsale_btn = view.findViewById(R.id.postsale_btn);
        post_image_btn = view.findViewById(R.id.post_image_btn);
        post_contact_btn = view.findViewById(R.id.post_contact_btn);
        post_company_tv = view.findViewById(R.id.post_company_tv);
        post_name_tv = view.findViewById(R.id.post_name_tv);
        post_version_edt = view.findViewById(R.id.post_version_edt);
        post_year_tv = view.findViewById(R.id.post_year_tv);
        post_madein_tv = view.findViewById(R.id.post_madein_tv);
        post_status_tv = view.findViewById(R.id.post_status_tv);
        post_kmwent_edt = view.findViewById(R.id.post_kmwent_edt);
        post_type_tv = view.findViewById(R.id.post_type_tv);
        post_price_edt = view.findViewById(R.id.post_price_edt);
        post_outside_tv = view.findViewById(R.id.post_outside_tv);
        post_inside_tv = view.findViewById(R.id.post_inside_tv);
        post_door_edt = view.findViewById(R.id.post_door_edt);
        post_seat_edt = view.findViewById(R.id.post_seat_edt);
        post_gear_tv = view.findViewById(R.id.post_gear_tv);
        post_drivetrain_tv = view.findViewById(R.id.post_drivetrain_tv);
        post_fuel_tv = view.findViewById(R.id.post_fuel_tv);
        post_consume_edt = view.findViewById(R.id.post_consume_edt);
        post_content_edt = view.findViewById(R.id.post_content_edt);
        post_airbag_edt = view.findViewById(R.id.post_airbag_edt);
        post_abs_edt = view.findViewById(R.id.post_abs_edt);
        post_eba_edt = view.findViewById(R.id.post_eba_edt);
        post_esp_edt = view.findViewById(R.id.post_esp_edt);
        post_antislip_edt = view.findViewById(R.id.post_antislip_edt);
        post_antitheft_edt = view.findViewById(R.id.post_antitheft_edt);
        post_reverse_warning_edt = view.findViewById(R.id.post_reverse_warning_edt);
        user_name_edt = view.findViewById(R.id.user_name_edt);
        user_phone_edt = view.findViewById(R.id.user_phone_edt);
        user_address_edt = view.findViewById(R.id.user_address_edt);
        user_province_tv = view.findViewById(R.id.user_province_tv);
        recyclerView_image = view.findViewById(R.id.post_image_recyclerView);

        listRealpath = new ArrayList<>();
        companyModelList = new ArrayList<>();
        categoryModelList = new ArrayList<>();
        imageAdapter = new ImageAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_image.setLayoutManager(layoutManager);
        recyclerView_image.setAdapter(imageAdapter);

        setTextUser();
        ShowBottomSheet();
        getDataCompany();
        checkButton();
        EventButton();


        return view;
    }


    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b) {
        EventButton();
    }


    private void ShowBottomSheet() {
        List<ProvinceModel> listColor = new ArrayList<>();
        listColor.add(new ProvinceModel(1, "Bạc"));
        listColor.add(new ProvinceModel(2, "Cát"));
        listColor.add(new ProvinceModel(3, "Ghi"));
        listColor.add(new ProvinceModel(4, "Hồng"));
        listColor.add(new ProvinceModel(5, "Đỏ"));
        listColor.add(new ProvinceModel(6, "Trắng"));
        listColor.add(new ProvinceModel(7, "Đen"));
        listColor.add(new ProvinceModel(8, "Vàng"));
        listColor.add(new ProvinceModel(9, "Cam"));
        listColor.add(new ProvinceModel(10, "Kem"));
        listColor.add(new ProvinceModel(11, "Nâu"));
        listColor.add(new ProvinceModel(12, "Tím"));
        listColor.add(new ProvinceModel(13, "Xanh"));
        listColor.add(new ProvinceModel(14, "Xám"));

        post_company_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetCompany bottomSheetCompany = new BottomSheetCompany(companyModelList, new BottomSheetCompanyAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name, int id) {
                        post_company_tv.setText(name);
                        Log.e("xxx", id + "");
                        getDataCategory(id);
                    }
                });
                bottomSheetCompany.show(getFragmentManager(), bottomSheetCompany.getTag());
            }
        });

        post_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!post_company_tv.getText().toString().equals("Chọn hãng xe")) {
                    BottomSheetCategory bottomSheetCategory = new BottomSheetCategory(categoryModelList, new BottomSheetCategoryAdapter.OnItemOnCLick() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(String name) {
                            post_name_tv.setText(name);
                        }
                    });
                    bottomSheetCategory.show(getFragmentManager(), bottomSheetCategory.getTag());
                } else {
                    post_company_tv.setError("Bạn chưa chọn hãng xe!");
                }
            }
        });

        post_year_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> list = new ArrayList<>();
                list.add(new ProvinceModel(1, "2021"));
                list.add(new ProvinceModel(2, "2020"));
                list.add(new ProvinceModel(3, "2019"));
                list.add(new ProvinceModel(4, "2018"));
                list.add(new ProvinceModel(5, "2017"));
                list.add(new ProvinceModel(6, "2016"));
                list.add(new ProvinceModel(7, "2015"));
                list.add(new ProvinceModel(8, "2014"));
                list.add(new ProvinceModel(9, "2013"));
                list.add(new ProvinceModel(10, "2012"));
                list.add(new ProvinceModel(11, "2011"));
                list.add(new ProvinceModel(12, "2010"));
                list.add(new ProvinceModel(13, "2009"));
                list.add(new ProvinceModel(14, "2008"));
                list.add(new ProvinceModel(15, "2007"));
                list.add(new ProvinceModel(16, "2006"));
                list.add(new ProvinceModel(17, "2005"));
                list.add(new ProvinceModel(18, "2004"));
                list.add(new ProvinceModel(18, "2004"));
                list.add(new ProvinceModel(19, "2003"));
                list.add(new ProvinceModel(20, "2002"));
                list.add(new ProvinceModel(21, "2001"));
                list.add(new ProvinceModel(22, "2000"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(list, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_year_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_madein_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listMadein = new ArrayList<>();
                listMadein.add(new ProvinceModel(1, "Trong nước"));
                listMadein.add(new ProvinceModel(2, "Nhập khẩu"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listMadein, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_madein_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_status_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listStatus = new ArrayList<>();
                listStatus.add(new ProvinceModel(1, "Xe mới"));
                listStatus.add(new ProvinceModel(2, "Xe cũ"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listStatus, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_status_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_type_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listType = new ArrayList<>();
                listType.add(new ProvinceModel(1, "Sedan"));
                listType.add(new ProvinceModel(2, "SUV"));
                listType.add(new ProvinceModel(3, "Coupe"));
                listType.add(new ProvinceModel(4, "Crossover"));
                listType.add(new ProvinceModel(5, "Hatchback"));
                listType.add(new ProvinceModel(6, "Cabriolet"));
                listType.add(new ProvinceModel(7, "Truck"));
                listType.add(new ProvinceModel(8, "Van/Minivan"));
                listType.add(new ProvinceModel(9, "Wagon"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listType, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_type_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_outside_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listColor, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_outside_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_inside_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listColor, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_inside_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_gear_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listGear = new ArrayList<>();
                listGear.add(new ProvinceModel(1, "Số tay"));
                listGear.add(new ProvinceModel(2, "Số tự động"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listGear, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_gear_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_drivetrain_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listdrive = new ArrayList<>();
                listdrive.add(new ProvinceModel(1, "FWD"));
                listdrive.add(new ProvinceModel(2, "RFD"));
                listdrive.add(new ProvinceModel(3, "4WD"));
                listdrive.add(new ProvinceModel(4, "AWD"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listdrive, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_drivetrain_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        post_fuel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listfuel = new ArrayList<>();
                listfuel.add(new ProvinceModel(1, "Xăng"));
                listfuel.add(new ProvinceModel(2, "Diesel"));
                listfuel.add(new ProvinceModel(3, "Hybrid"));
                listfuel.add(new ProvinceModel(4, "Điện"));
                listfuel.add(new ProvinceModel(5, "Loại khác"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listfuel, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        post_fuel_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getFragmentManager(), bottomSheetSelected.getTag());
            }
        });

    }


    private void EventButton() {
        String email = saveSignIn.getString("user_Email", "");
        postsale_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(email)) {
                    String approval = "Chờ duyệt";
                    String user_type = saveSignIn.getString("user_Type", "");
                    if (user_type.equals("Vip1") || user_type.equals("Vip2")) {
                        approval = "Đã duyệt";
                        PostProductandImage(approval);
                    }
                    PostProductandImage(approval);
                } else {
                    DialogSignIn();
                }
            }
        });

        post_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPermission();
            }
        });


        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
    }


    private void PostProductandImage(String approval) {
        if (listRealpath != null) {
            Log.d("real", listRealpath.toString());
            String file_path = "";
                file = new File(listRealpath.get(0));
                file_path = file.getAbsolutePath();
//                String[] file_name = file_path.split("\\.");
//                file_path = file_name[0] + System.currentTimeMillis() + "." + file_name[1];


            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file_path, requestBody);
            Call<String> call = APIRequest.uploadImage(getContext(), body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        String message = response.body();
                        String image1 = BaseAPIRequest.BaseURL + "image/" + message;
                        String image2 = "";
                        String image3 = "";
                        String image4 = "";
                        String image5 = "";
                        CheckPostProduct(image1, image2, image3, image4, image5, approval);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        }
    }


    ////// Đăng tin
    private void CheckPostProduct(String image1, String image2, String image3, String image4, String image5, String approval) {
        company = post_company_tv.getText().toString();
        name = post_name_tv.getText().toString();
        version = post_version_edt.getText().toString();
        year = post_year_tv.getText().toString();
        madein = post_madein_tv.getText().toString();
        status = post_status_tv.getText().toString();
        kmwent = post_kmwent_edt.getText().toString();
        type = post_type_tv.getText().toString();
        price = post_price_edt.getText().toString();
        outside = post_outside_tv.getText().toString();
        inside = post_inside_tv.getText().toString();
        door = post_door_edt.getText().toString();
        seat = post_seat_edt.getText().toString();
        gear = post_gear_tv.getText().toString();
        drivetrain = post_drivetrain_tv.getText().toString();
        fuel = post_fuel_tv.getText().toString();
        consume = post_consume_edt.getText().toString();
        content = post_content_edt.getText().toString();
        airbag = post_airbag_edt.getText().toString();
        abs = post_abs_edt.getText().toString();
        eba = post_eba_edt.getText().toString();
        esp = post_esp_edt.getText().toString();
        antislip = post_antislip_edt.getText().toString();
        reverse = post_reverse_warning_edt.getText().toString();
        antitheft = post_antitheft_edt.getText().toString();

        if (!company.equals("Chọn hãng xe")) {
            if (!name.equals("Chọn đời xe")) {
                if (!post_year_tv.getText().equals("Chọn năm sản xuất")) {
                    if (!madein.equals("Chọn xuất xứ")) {
                        if (!status.equals("Chọn tình trạng")) {
                            if (!type.equals("Chọn dòng xe")) {
                                if (!outside.equals("Màu ngoại thất")) {
                                    if (!inside.equals("Màu nội thất")) {
                                        if (!gear.equals("Chọn hộp số")) {
                                            if (!drivetrain.equals("Chọn dẫn động")) {
                                                if (!fuel.equals("Chọn nhiên liệu")) {
                                                    if (abs.equals("có") || abs.equals("không")) {
                                                        if (eba.equals("có") || eba.equals("không")) {
                                                            if (esp.equals("có") || esp.equals("không")) {
                                                                if (antislip.equals("có") || antislip.equals("không")) {
                                                                    if (reverse.equals("có") || reverse.equals("không")) {
                                                                        if (antitheft.equals("có") || antitheft.equals("không")) {
                                                                            if (listRealpath != null) {
                                                                                postsale_btn.setEnabled(false);
                                                                                postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                                                                PostProduct(image1, image2, image3, image4, image5, approval);
                                                                            } else {
                                                                                Toast.makeText(getContext(), "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            post_antitheft_edt.setError("Bạn chỉ được nhập Có hoặc Không");
                                                                        }
                                                                    } else {
                                                                        post_reverse_warning_edt.setError("Bạn chỉ được nhập Có hoặc Không");
                                                                    }
                                                                } else {
                                                                    post_antislip_edt.setError("Bạn chỉ được nhập Có hoặc Không");
                                                                }
                                                            } else {
                                                                post_esp_edt.setError("Bạn chỉ được nhập Có hoặc Không");
                                                            }
                                                        } else {
                                                            post_eba_edt.setError("Bạn chỉ được nhập Có hoặc Không");
                                                        }
                                                    } else {
                                                        post_abs_edt.setError("Bạn chỉ được nhập Có hoặc Không");
                                                    }
                                                } else {
                                                    post_fuel_tv.setError("Bạn chưa chọn nhiên liệu!");
                                                }
                                            } else {
                                                post_drivetrain_tv.setError("Bạn chưa chọn dẫn động!");
                                            }
                                        } else {
                                            post_gear_tv.setError("Bạn chưa chọn hộp số");
                                        }
                                    } else {
                                        post_inside_tv.setError("Bạn chưa chọn màu nội thất");
                                    }
                                } else {
                                    post_outside_tv.setError("Bạn chưa chọn màu ngoại thất!");
                                }
                            } else {
                                post_type_tv.setError("Bạn chưa chọn dòng xe!");
                            }
                        } else {
                            post_status_tv.setError("Bạn chưa chọn tình trạng xe!");
                        }
                    } else {
                        post_madein_tv.setError("Bạn chưa chọn xuất xứ!");
                    }
                } else {
                    post_year_tv.setError("Bạn chưa chọn năm sản xuất!");
                }
            } else {
                post_name_tv.setError("Bạn chưa chọn đời xe!");
            }
        } else {
            post_company_tv.setError("Bạn chưa chọn hãng xe!");
        }
    }


    @SuppressLint("CheckResult")
    private void PostProduct(String image1, String image2, String image3, String image4, String image5, String approval) {
        String uname = saveSignIn.getString("user_Name", "");
        String uphone = saveSignIn.getString("user_Phone", "");
        String uaddress = saveSignIn.getString("user_Address", "");
        String uliving = saveSignIn.getString("user_LivingArea", "");
        int userid = saveSignIn.getInt("user_Id", -1);
        Log.d("post", antitheft);
        APIRequest.PostProduct(getContext(), company, name, version, year, madein, status, kmwent, type, price, outside, inside, door, seat,
                gear, drivetrain, fuel, consume, image1, image2, image3, image4, image5, content, uname, uphone, uaddress, uliving, airbag,
                abs, eba, esp, antislip, reverse, antitheft, userid, approval)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String id = jsonObject.getString("status");
                    Log.d("post", id);
                    if (id != null) {
                        PostImage(id);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Đăng bài thất bại", Toast.LENGTH_LONG).show();
                });
    }


    private void PostImage(String product_id) {
        String file_path;
        Log.d("realpath", listRealpath.toString());
        for (int i = 0; i < listRealpath.size(); i++) {
            file = new File(listRealpath.get(i));
            file_path = file.getAbsolutePath();
//            String[] file_name = file_path.split("\\.");
//            file_path = file_name[0] + System.currentTimeMillis() + "." + file_name[1] + "." + file_name[2];

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file_path, requestBody);
            Call<String> call = APIRequest.uploadImage(getContext(), body);
            call.enqueue(new Callback<String>() {
                @SuppressLint("CheckResult")
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        String message = response.body();
                        String url = BaseAPIRequest.BaseURL + "image/" + message;
                        APIRequest.postImage(getContext(), url, product_id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(jsonElement -> {
                                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                                    String status = jsonObject.getString("status");
                                    if (status.equals("success")) {
                                        Toast.makeText(getContext(), "Đăng bài thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    Toast.makeText(getContext(), "Đăng bài thất bại", Toast.LENGTH_LONG).show();
                                });
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        }
    }


    ////// kiểm tra cấp quyền
    private void RequestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                SelectImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }


    ////// chọn ảnh
    private void SelectImage() {
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1000)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .setSelectMaxCount(5)
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if (uriList != null && !uriList.isEmpty()) {
                            imageAdapter.setDataImage(uriList);
                            String realpath1 = "";
                            for (int i = 0; i < uriList.size(); i++) {
                                realpath1 = String.valueOf(uriList.get(i));
                                String[] name1 = realpath1.split("\\:");
                                realpath1 = name1[1];
                                listRealpath.add(realpath1);
                            }
                        }
                    }
                });
    }


    ////// check cho click button
    private void checkButton() {
        post_version_edt.addTextChangedListener(new TextWatcher() {
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
        post_kmwent_edt.addTextChangedListener(new TextWatcher() {
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
        post_price_edt.addTextChangedListener(new TextWatcher() {
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
        post_door_edt.addTextChangedListener(new TextWatcher() {
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
        post_seat_edt.addTextChangedListener(new TextWatcher() {
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
        post_content_edt.addTextChangedListener(new TextWatcher() {
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
        post_airbag_edt.addTextChangedListener(new TextWatcher() {
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
        post_abs_edt.addTextChangedListener(new TextWatcher() {
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
        post_eba_edt.addTextChangedListener(new TextWatcher() {
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
        post_esp_edt.addTextChangedListener(new TextWatcher() {
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
        post_antislip_edt.addTextChangedListener(new TextWatcher() {
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
        post_reverse_warning_edt.addTextChangedListener(new TextWatcher() {
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
        post_antitheft_edt.addTextChangedListener(new TextWatcher() {
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
    }


    ////// check điều kiện
    private void checkInput() {
        if (!TextUtils.isEmpty(post_version_edt.getText())) {
            if (!TextUtils.isEmpty(post_kmwent_edt.getText())) {
                if (!TextUtils.isEmpty(post_price_edt.getText())) {
                    if (!TextUtils.isEmpty(post_door_edt.getText())) {
                        if (!TextUtils.isEmpty(post_seat_edt.getText())) {
                            if (!TextUtils.isEmpty(post_content_edt.getText())) {
                                if (!TextUtils.isEmpty(post_airbag_edt.getText())) {
                                    if (!TextUtils.isEmpty(post_abs_edt.getText())) {
                                        if (!TextUtils.isEmpty(post_eba_edt.getText())) {
                                            if (!TextUtils.isEmpty(post_esp_edt.getText())) {
                                                if (!TextUtils.isEmpty(post_antislip_edt.getText())) {
                                                    if (!TextUtils.isEmpty(post_reverse_warning_edt.getText())) {
                                                        if (!TextUtils.isEmpty(post_antitheft_edt.getText())) {
                                                            postsale_btn.setEnabled(true);
                                                            postsale_btn.setTextColor(Color.rgb(255, 255, 255));
                                                        } else {
                                                            postsale_btn.setEnabled(false);
                                                            postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                                        }
                                                    } else {
                                                        postsale_btn.setEnabled(false);
                                                        postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                                    }
                                                } else {
                                                    postsale_btn.setEnabled(false);
                                                    postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                                }
                                            } else {
                                                postsale_btn.setEnabled(false);
                                                postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                            }
                                        } else {
                                            postsale_btn.setEnabled(false);
                                            postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                        }
                                    } else {
                                        postsale_btn.setEnabled(false);
                                        postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                    }
                                } else {
                                    postsale_btn.setEnabled(false);
                                    postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                                }
                            } else {
                                postsale_btn.setEnabled(false);
                                postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                            }
                        } else {
                            postsale_btn.setEnabled(false);
                            postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                        }
                    } else {
                        postsale_btn.setEnabled(false);
                        postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    postsale_btn.setEnabled(false);
                    postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                postsale_btn.setEnabled(false);
                postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            postsale_btn.setEnabled(false);
            postsale_btn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }


    private void setTextUser() {
        String uname = saveSignIn.getString("user_Name", "");
        String uphone = saveSignIn.getString("user_Phone", "");
        String uaddress = saveSignIn.getString("user_Address", "");
        String uliving = saveSignIn.getString("user_LivingArea", "");

        user_name_edt.setText(uname);
        user_phone_edt.setText(uphone);
        user_address_edt.setText(uaddress);
        user_province_tv.setText(uliving);
    }


    ////// get dữ liệu hãng xe
    @SuppressLint("CheckResult")
    private void getDataCompany() {
        APIRequest.getAllCompany(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("category", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<CompanyModel> companyModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<CompanyModel>>() {
                    }.getType());
                    companyModelList.addAll(companyModels);
                }, throwable -> {

                });
    }


    ////// get dữ liệu đời xe
    @SuppressLint("CheckResult")
    private void getDataCategory(int id) {
        APIRequest.getCategory(getContext(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("category", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<CategoryModel> categoryModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<CategoryModel>>() {
                    }.getType());
                    categoryModelList.addAll(categoryModels);
                }, throwable -> {

                });
    }


    ////// show dialog đăng nhập
    private void DialogSignIn() {
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
