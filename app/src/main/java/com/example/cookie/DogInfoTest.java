package com.example.cookie;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookie.databinding.ActivityDogInfoBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogInfoTest extends AppCompatActivity {
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
    private final Calendar calendar = Calendar.getInstance();
    private int birthYear = calendar.get(Calendar.YEAR);
    private int birthMonth = calendar.get(Calendar.MONTH);
    private int birthDay = calendar.get(Calendar.DAY_OF_MONTH);
    private ActivityDogInfoBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDogInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);

        DogInfo dogInfo = new DogInfo();

        btnMale.setBackgroundColor(getColor(R.color.white));
        btnFemale.setBackgroundColor(getColor(R.color.white));

        btnMale.setOnClickListener(view -> {
            btnMale.setBackgroundColor(getColor(R.color.beige));
            btnFemale.setBackgroundColor(getColor(R.color.white));
            DogInfo dogI = new DogInfo("aaaaf", "M", "Y", new GregorianCalendar(2020,10,10), "말티즈", "1일 3회 이상", "산, 숲길");

            useDogInfoAPI(dogI);
            dogInfo.setPet_gender("Male");
        });

        btnFemale.setOnClickListener(view -> {
            btnMale.setBackgroundColor(getColor(R.color.white));
            btnFemale.setBackgroundColor(getColor(R.color.beige));
            dogInfo.setPet_gender("Female");
        });

        btnCalendar = findViewById(R.id.btn_calendar);
        edtBirthYear = findViewById(R.id.edt_birthyear);
        edtBirthMonth = findViewById(R.id.edt_birthmonth);
        edtBirthDay = findViewById(R.id.edt_birthday);
        btnCalendar.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
                edtBirthYear.setText(Integer.toString(year));
                edtBirthMonth.setText(Integer.toString(month + 1));
                edtBirthDay.setText(Integer.toString(day));
            }, birthYear, birthMonth, birthDay);
            datePickerDialog.show();
            //dogInfo.setPet_birth(new Date(birthYear, birthMonth, birthDay));
        });

        ArrayList<String> dogCategory = new ArrayList<>();
        dogCategory.add("견종을 알려주세요");
        dogCategory.add("믹스견");
        dogCategory.add("말티즈");
        // 이하 dogCategory.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogCategory);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogCategory.setAdapter(adapter1);

        ArrayList<String> dogWalk = new ArrayList<>();
        dogWalk.add("산책 빈도");
        dogWalk.add("1일 3회 이상");

        // 이하 dogWalk.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogWalk);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogWalk.setAdapter(adapter2);

        ArrayList<String> dogPlace = new ArrayList<>();
        dogPlace.add("산, 숲길");
        dogPlace.add("애견 운동장");

        // 이하 dogPlace.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogPlace);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogPlace.setAdapter(adapter3);

        ArrayList<String> diseaseCategory = new ArrayList<>();
        diseaseCategory.add("질병을 선택해주세요");
        diseaseCategory.add("알러지성 피부염");
        // 이하 diseaseCategory.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diseaseCategory);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.diseaseCategory.setAdapter(adapter4);

        switchDisease = findViewById(R.id.switch_disease);
        diseaseInfo = findViewById(R.id.disease_info);

        switchDisease.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                diseaseInfo.setVisibility(View.VISIBLE);
            } else {
                diseaseInfo.setVisibility(View.INVISIBLE);
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

        btnDrug.setOnClickListener(view -> {
            if (!isColorChangedDrug) {
                btnDrug.setBackgroundColor(getResources().getColor(R.color.beige, null));
            } else {
                btnDrug.setBackgroundColor(originalColor);
            }
            isColorChangedDrug = !isColorChangedDrug;
        });

        btnCream.setOnClickListener(view -> {
            if (!isColorChangedCream) {
                btnCream.setBackgroundColor(getResources().getColor(R.color.beige, null));
            } else {
                btnCream.setBackgroundColor(originalColor);
            }
            isColorChangedCream = !isColorChangedCream;
        });

        btnShampoo.setOnClickListener(view -> {
            if (!isColorChangedShampoo) {
                btnShampoo.setBackgroundColor(getResources().getColor(R.color.beige, null));
            } else {
                btnShampoo.setBackgroundColor(originalColor);
            }
            isColorChangedShampoo = !isColorChangedShampoo;
        });

        btnHospital.setOnClickListener(view -> {
            if (!isColorChangedHospital) {
                btnHospital.setBackgroundColor(getResources().getColor(R.color.beige, null));
            } else {
                btnHospital.setBackgroundColor(originalColor);
            }
            isColorChangedHospital = !isColorChangedHospital;
        });

        btnNutrients.setOnClickListener(view -> {
            if (!isColorChangedNutrients) {
                btnNutrients.setBackgroundColor(getResources().getColor(R.color.beige, null));
            } else {
                btnNutrients.setBackgroundColor(originalColor);
            }
            isColorChangedNutrients = !isColorChangedNutrients;
        });

        btnNone.setOnClickListener(view -> {
            if (!isColorChangedNone) {
                btnNone.setBackgroundColor(getResources().getColor(R.color.beige, null));
            } else {
                btnNone.setBackgroundColor(originalColor);
            }
            isColorChangedNone = !isColorChangedNone;
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(view -> {
            Intent intent = new Intent(DogInfoTest.this, HomeMenuActivity.class);
            startActivity(intent);
        });

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(DogInfoTest.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void useDogInfoAPI(DogInfo info) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        // Retrofit 빌더 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DogInfoAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        DogInfoAPI DogAPI = retrofit.create(DogInfoAPI.class);

        DogInfo dogInfo = new DogInfo(info.getPet_name(), info.getPet_gender(), info.getPet_neuter(), info.getPet_birth(), info.getPet_breed(), info.getWalk_time(), info.getWalk_place());

        Call<DogInfo> postCall = DogAPI.post_posts(dogInfo);
        postCall.enqueue(new Callback<DogInfo>() {
            @Override
            public void onResponse(Call<DogInfo> call, Response<DogInfo> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DogInfoTest.this, "등록 완료", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DogInfoTest.this, "등록 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogInfo> call, Throwable t) {
                Toast.makeText(DogInfoTest.this, "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
