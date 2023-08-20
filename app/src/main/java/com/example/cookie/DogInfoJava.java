package com.example.cookie;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogInfoJava extends AppCompatActivity {
    private ImageButton btnMale;
    private ImageButton btnFemale;
    private ImageButton btnCalendar;
    private EditText edtBirthYear;
    private EditText edtBirthMonth;
    private EditText edtBirthDay;
    private Switch switchDisease;
    private LinearLayout diseaseInfo;
    private ImageButton btnDrug;
    private ImageButton btnCream;
    private ImageButton btnShampoo;
    private ImageButton btnHospital;
    private ImageButton btnNutrients;
    private ImageButton btnNone;
    private boolean isColorChangedDrug = false;
    private boolean isColorChangedCream = false;
    private boolean isColorChangedShampoo = false;
    private boolean isColorChangedHospital = false;
    private boolean isColorChangedNutrients = false;
    private boolean isColorChangedNone = false;
    private ImageButton btnSave;
    private ImageButton btnBack;
    private Calendar calendar = Calendar.getInstance();
    private int birthYear = calendar.get(Calendar.YEAR);
    private int birthMonth = calendar.get(Calendar.MONTH);
    private int birthDay = calendar.get(Calendar.DAY_OF_MONTH);

    private Spinner dogCategorySpinner;
    private Spinner dogWalkSpinner;
    private Spinner dogPlaceSpinner;
    private Spinner diseaseCategorySpinner;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info);

        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);

        btnMale.setBackgroundColor(getResources().getColor(R.color.white));
        btnFemale.setBackgroundColor(getResources().getColor(R.color.white));

        DogInfo dogInfo = new DogInfo();

        //DogInfo dogI = new DogInfo("bbb", "Female", "No", new Calendar(2022,8,12) {}, "믹스견", "1일 3회 이상", "산, 숲길");


        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundColor(getResources().getColor(R.color.beige));
                btnFemale.setBackgroundColor(getResources().getColor(R.color.white));
                //useDogInfoAPI(dogI);
                dogInfo.setPet_gender("Male");
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundColor(getResources().getColor(R.color.white));
                btnFemale.setBackgroundColor(getResources().getColor(R.color.beige));
                dogInfo.setPet_gender("Female");
            }
        });

        btnCalendar = findViewById(R.id.btn_calendar);
        edtBirthYear = findViewById(R.id.edt_birthyear);
        edtBirthMonth = findViewById(R.id.edt_birthmonth);
        edtBirthDay = findViewById(R.id.edt_birthday);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DogInfoJava.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtBirthYear.setText(String.valueOf(year));
                        edtBirthMonth.setText(String.valueOf(month + 1));
                        edtBirthDay.setText(String.valueOf(dayOfMonth));
                    }
                }, birthYear, birthMonth, birthDay);
                datePickerDialog.show();
                //dogInfo.setPet_birth(new Date(birthYear, birthMonth, birthDay));
            }
        });

        List<String> dogCategoryList = new ArrayList<>();
        dogCategoryList.add("견종을 알려주세요");
        dogCategoryList.add("믹스견");
        dogCategoryList.add("말티즈");
        // 나머지 목록도 동일하게 추가

        ArrayAdapter<String> dogCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogCategoryList);
        dogCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogCategorySpinner.setAdapter(dogCategoryAdapter);

        List<String> dogWalkList = new ArrayList<>();
        dogWalkList.add("산책 빈도");
        dogWalkList.add("1일 3회 이상");
        dogWalkList.add("1일 2회");
        // 나머지 목록도 동일하게 추가

        ArrayAdapter<String> dogWalkAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogWalkList);
        dogWalkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogWalkSpinner.setAdapter(dogWalkAdapter);

        List<String> dogPlaceList = new ArrayList<>();
        dogPlaceList.add("산책 장소");
        dogPlaceList.add("산, 숲길");
        dogPlaceList.add("애견 운동장");
        // 나머지 목록도 동일하게 추가

        ArrayAdapter<String> dogPlaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogPlaceList);
        dogPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogPlaceSpinner.setAdapter(dogPlaceAdapter);

        List<String> diseaseCategoryList = new ArrayList<>();
        diseaseCategoryList.add("질병을 선택해주세요");
        diseaseCategoryList.add("알러지성 피부염");
        diseaseCategoryList.add("효모균 감염");
        // 나머지 목록도 동일하게 추가

        ArrayAdapter<String> diseaseCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diseaseCategoryList);
        diseaseCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diseaseCategorySpinner.setAdapter(diseaseCategoryAdapter);

        switchDisease = findViewById(R.id.switch_disease);
        diseaseInfo = findViewById(R.id.disease_info);

        switchDisease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    diseaseInfo.setVisibility(View.VISIBLE);
                } else {
                    diseaseInfo.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnDrug = findViewById(R.id.btn_drug);
        btnCream = findViewById(R.id.btn_cream);
        btnShampoo = findViewById(R.id.btn_shampoo);
        btnHospital = findViewById(R.id.btn_hospital);
        btnNutrients = findViewById(R.id.btn_nutrients);
        btnNone = findViewById(R.id.btn_none);

        int originalColor = getResources().getColor(R.color.white, null);
        btnDrug.setBackgroundColor(originalColor);
        btnCream.setBackgroundColor(originalColor);
        btnShampoo.setBackgroundColor(originalColor);
        btnHospital.setBackgroundColor(originalColor);
        btnNutrients.setBackgroundColor(originalColor);
        btnNone.setBackgroundColor(originalColor);

        btnDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isColorChangedDrug) {
                    btnDrug.setBackgroundColor(getResources().getColor(R.color.beige, null));
                } else {
                    btnDrug.setBackgroundColor(originalColor);
                }
                isColorChangedDrug = !isColorChangedDrug;
            }
        });

        btnCream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isColorChangedCream) {
                    btnCream.setBackgroundColor(getResources().getColor(R.color.beige, null));
                } else {
                    btnCream.setBackgroundColor(originalColor);
                }
                isColorChangedCream = !isColorChangedCream;
            }
        });

        btnShampoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isColorChangedShampoo) {
                    btnShampoo.setBackgroundColor(getResources().getColor(R.color.beige, null));
                } else {
                    btnShampoo.setBackgroundColor(originalColor);
                }
                isColorChangedShampoo = !isColorChangedShampoo;
            }
        });

        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isColorChangedHospital) {
                    btnHospital.setBackgroundColor(getResources().getColor(R.color.beige, null));
                } else {
                    btnHospital.setBackgroundColor(originalColor);
                }
                isColorChangedHospital = !isColorChangedHospital;
            }
        });

        btnNutrients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isColorChangedNutrients) {
                    btnNutrients.setBackgroundColor(getResources().getColor(R.color.beige, null));
                } else {
                    btnNutrients.setBackgroundColor(originalColor);
                }
                isColorChangedNutrients = !isColorChangedNutrients;
            }
        });

        btnNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isColorChangedNone) {
                    btnNone.setBackgroundColor(getResources().getColor(R.color.beige, null));
                } else {
                    btnNone.setBackgroundColor(originalColor);
                }
                isColorChangedNone = !isColorChangedNone;
            }
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogInfoJava.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogInfoJava.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void useDogInfoAPI(DogInfo info) {
        // Retrofit 빌더 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.35.85.32:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogInfoAPI DogAPI = retrofit.create(DogInfoAPI.class);

        DogInfo dogInfo = new DogInfo(info.getPet_name(), info.getPet_gender(), info.getPet_neuter(), info.getPet_birth(), info.getPet_breed(), info.getWalk_time(), info.getWalk_place());
        Call<DogInfo> postCall = DogAPI.post_posts(dogInfo);
        postCall.enqueue(new Callback<DogInfo>() {
            @Override
            public void onResponse(Call<DogInfo> call, Response<DogInfo> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"등록 완료");
                }else {
                    Log.d(TAG,"Status Code : " + response.code());
                    Log.d(TAG,response.errorBody().toString());
                    Log.d(TAG,call.request().body().toString());
                }
            }

            @Override
            public void onFailure(Call<DogInfo> call, Throwable t) {
                Log.d(TAG,"Fail msg : " + t.getMessage());
            }
        });
    }
}
