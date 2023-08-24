package com.example.cookie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity1 extends AppCompatActivity {

    private ImageView result_image;
    private ImageButton homeButton1;
    private TextView result_text;
    private String pet_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result1_layout);

        result_image = findViewById(R.id.result_image);
        homeButton1 = findViewById(R.id.homeButton1);
        result_text = findViewById(R.id.pet_name);

        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity1.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });

        // 앱 전체 전역변수 받아오기
        GlobalVariable temp = (GlobalVariable) getApplication();
        int pk = temp.getPET_ID();

        // 강아지 이름 받아오는 메소드
        getDogInfo(pk);

        result_text.setText(pet_name);
    }

    private void getDogInfo(int primary_key) {
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        DjangoAPI DogAPI = retrofit.create(DjangoAPI.class);

        Call<Pet> getCall = DogAPI.get_post_pk(primary_key);

        // GET 구현 코드
        getCall.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if( response.isSuccessful()){
                    String temp = response.body().getPET_NAME();
                    pet_name = temp;
                    // getPET_NAME, getPET_GENDER 등등 가능

                } else {
                    Toast.makeText(ResultActivity1.this, "GET 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(ResultActivity1.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
