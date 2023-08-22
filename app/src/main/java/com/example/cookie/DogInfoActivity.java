package com.example.cookie;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookie.databinding.ActivityDogInfoBinding;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogInfoActivity extends AppCompatActivity {
    private ImageButton btnMale;
    private ImageButton btnFemale;
    private ImageButton btnCalendar;
    private EditText edtBirthYear;
    private EditText edtBirthMonth;
    private EditText edtBirthDay;
    private EditText edName;
    private Switch switchNeuter;
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
        edName = findViewById(R.id.edt_name);

        btnMale.setImageResource(R.drawable.dog_info_m_l);
        btnFemale.setImageResource(R.drawable.dog_info_f_l);
        DogInfo userDogInfo = new DogInfo();

        btnMale.setOnClickListener(view -> {
            btnMale.setImageResource(R.drawable.dog_info_m_d);
            btnFemale.setImageResource(R.drawable.dog_info_f_l);
            userDogInfo.setPET_GENDER("M");
        });

        btnFemale.setOnClickListener(view -> {
            btnMale.setImageResource(R.drawable.dog_info_m_l);
            btnFemale.setImageResource(R.drawable.dog_info_f_d);
            userDogInfo.setPET_GENDER("F");
        });

        switchNeuter = findViewById(R.id.switch_neuter);
        switchNeuter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    userDogInfo.setPET_NEUTER("Y");
                } else {
                    userDogInfo.setPET_NEUTER("N");
                }
            }
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
            String month = String.format("%02d", birthMonth);
            userDogInfo.setPET_BIRTH(birthYear+"-"+month+"-"+birthDay);
        });

        ArrayList<String> dogCategory = new ArrayList<>();
        dogCategory.add("견종을 알려주세요");
        dogCategory.add("믹스견");
        dogCategory.add("말티즈");
        dogCategory.add("토이 푸들");
        dogCategory.add("미니어처 푸들");
        dogCategory.add("포메라니안");
        dogCategory.add("비숑 프리제");
        dogCategory.add("시츄");
        dogCategory.add("치와와");
        dogCategory.add("웰시코기 펨브록");
        dogCategory.add("재패니즈 스피츠");
        dogCategory.add("요크셔 테리어");
        dogCategory.add("래브라도 리트리버");
        dogCategory.add("골든 리트리버");
        dogCategory.add("진돗개");
        dogCategory.add("시바");
        dogCategory.add("닥스훈트");
        dogCategory.add("미니어쳐 슈나우저");
        dogCategory.add("비글");
        dogCategory.add("미니어처 핀션");
        dogCategory.add("파피용");
        dogCategory.add("사모예드");
        dogCategory.add("보더 콜리");
        dogCategory.add("프렌치 불독");
        dogCategory.add("보스턴 테리어");
        dogCategory.add("아메리칸 코커 스파니엘");
        dogCategory.add("잉글리쉬 코커 스파니엘");
        dogCategory.add("잭 러셀 테리어");
        dogCategory.add("퍼그");
        dogCategory.add("그레이하운드");
        dogCategory.add("페키니즈");
        dogCategory.add("스탠다드 푸들");
        dogCategory.add("저먼 셰퍼드 독");
        dogCategory.add("알래스카 말라뮤트");
        dogCategory.add("도베르만");
        dogCategory.add("베들린턴 테리어");
        dogCategory.add("도사견");
        dogCategory.add("달마시안");
        dogCategory.add("롯트와일러");
        dogCategory.add("올드 잉글리쉬 쉽독");
        dogCategory.add("아프간 하운드");
        dogCategory.add("삽살개");
        dogCategory.add("풍산개");
        dogCategory.add("아메리칸 불독");
        // 이하 dogCategory.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogCategory);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogCategory.setAdapter(adapter1);
        binding.dogCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedBreed = (String) adapterView.getItemAtPosition(position);
                userDogInfo.setPET_BREED(selectedBreed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> dogWalk = new ArrayList<>();
        dogWalk.add("산책 빈도");
        dogWalk.add("1일 3회 이상");
        dogWalk.add("1일 2회");
        dogWalk.add("1일 1회");
        dogWalk.add("1주 4회 이상");
        dogWalk.add("1주 2회 이상");
        dogWalk.add("1주 1회");
        dogWalk.add("산책을 거의 시키지 않음");
        // 이하 dogWalk.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogWalk);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogWalk.setAdapter(adapter2);
        binding.dogWalk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedWalk = (String) adapterView.getItemAtPosition(position);
                userDogInfo.setWALK_TIME(selectedWalk);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> dogPlace = new ArrayList<>();
        dogPlace.add("산, 숲길");
        dogPlace.add("애견 운동장");
        dogPlace.add("일반 공원");
        dogPlace.add("일반 보행자 도로");
        dogPlace.add("바닷가");
        // 이하 dogPlace.add로 데이터 추가 (생략)

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogPlace);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogPlace.setAdapter(adapter3);
        binding.dogPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedPlace = (String) adapterView.getItemAtPosition(position);
                userDogInfo.setWALK_PLACE(selectedPlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> diseaseCategory = new ArrayList<>();
        diseaseCategory.add("질병을 선택해주세요");
        diseaseCategory.add("알러지성 피부염");
        diseaseCategory.add("효모균 감염");
        diseaseCategory.add("모낭염");
        diseaseCategory.add("농가진");
        diseaseCategory.add("지루");

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

        btnDrug.setImageResource(R.drawable.dog_care_pill_l);
        btnCream.setImageResource(R.drawable.dog_care_cream_l);
        btnShampoo.setImageResource(R.drawable.dog_care_shampoo_l);
        btnHospital.setImageResource(R.drawable.dog_care_hospital_l);
        btnNutrients.setImageResource(R.drawable.dog_care_nutrient_l);
        btnNone.setImageResource(R.drawable.dog_care_no_l);

        btnDrug.setOnClickListener(view -> {
            if (!isColorChangedDrug) {
                btnDrug.setImageResource(R.drawable.dog_care_pill_d);
            } else {
                btnDrug.setImageResource(R.drawable.dog_care_pill_l);
            }
            isColorChangedDrug = !isColorChangedDrug;
        });

        btnCream.setOnClickListener(view -> {
            if (!isColorChangedCream) {
                btnCream.setImageResource(R.drawable.dog_care_cream_d);
            } else {
                btnCream.setImageResource(R.drawable.dog_care_cream_l);
            }
            isColorChangedCream = !isColorChangedCream;
        });

        btnShampoo.setOnClickListener(view -> {
            if (!isColorChangedShampoo) {
                btnShampoo.setImageResource(R.drawable.dog_care_shampoo_d);
            } else {
                btnShampoo.setImageResource(R.drawable.dog_care_shampoo_l);
            }
            isColorChangedShampoo = !isColorChangedShampoo;
        });

        btnHospital.setOnClickListener(view -> {
            if (!isColorChangedHospital) {
                btnHospital.setImageResource(R.drawable.dog_care_hospital_d);
            } else {
                btnHospital.setImageResource(R.drawable.dog_care_hospital_l);
            }
            isColorChangedHospital = !isColorChangedHospital;
        });

        btnNutrients.setOnClickListener(view -> {
            if (!isColorChangedNutrients) {
                btnNutrients.setImageResource(R.drawable.dog_care_nutrient_d);
            } else {
                btnNutrients.setImageResource(R.drawable.dog_care_nutrient_l);
            }
            isColorChangedNutrients = !isColorChangedNutrients;
        });

        btnNone.setOnClickListener(view -> {
            if (!isColorChangedNone) {
                btnNone.setImageResource(R.drawable.dog_care_no_d);
            } else {
                btnNone.setImageResource(R.drawable.dog_care_no_l);
            }
            isColorChangedNone = !isColorChangedNone;
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(view -> {
            userDogInfo.setPET_NAME(edName.getText().toString());
            if (userDogInfo.getPET_NEUTER() == null){
                userDogInfo.setPET_NEUTER("N");
            }

            if (userDogInfo.getPET_NAME() == null || userDogInfo.getPET_GENDER() == null ||
                    userDogInfo.getPET_NEUTER() == null || userDogInfo.getPET_BIRTH() == null ||
                    userDogInfo.getPET_BREED() == null || userDogInfo.getWALK_TIME() == null ||
                    userDogInfo.getWALK_PLACE() == null || userDogInfo.getWALK_TIME().equals("산책 빈도")) {
                Toast.makeText(DogInfoActivity.this, "모든 정보를 입력해주세요. ", Toast.LENGTH_SHORT).show();
            } else {
                useDogInfoAPI(userDogInfo);
            }
        });

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            finish();
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

        DogInfo dogInfo = new DogInfo(info.getPET_NAME(), info.getPET_GENDER(), info.getPET_NEUTER(), info.getPET_BIRTH(), info.getPET_BREED(), info.getWALK_TIME(), info.getWALK_PLACE());

        Call<DogInfo> postCall = DogAPI.post_posts(dogInfo);
        postCall.enqueue(new Callback<DogInfo>() {
            @Override
            public void onResponse(Call<DogInfo> call, Response<DogInfo> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DogInfoActivity.this, "등록 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DogInfoActivity.this, HomeMenuActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(DogInfoActivity.this, "등록 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogInfo> call, Throwable t) {
                Toast.makeText(DogInfoActivity.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }
}