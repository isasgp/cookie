package com.example.cookie;

import static com.example.cookie.DjangoAPI.API_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private TextView breedText;
    private TextView walkText;
    private ImageButton btnHome; // 홈 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnHome = findViewById(R.id.homeButton1);
        breedText = findViewById(R.id.breed_explain);
        walkText = findViewById(R.id.walk_explain);

        GlobalVariable temp = (GlobalVariable) getApplication();
        // int pk= temp.getPET_ID();
        int pk= 1;

        // 강아지 이름 받아오는 메소드
        getDogInfo(pk, breedText, walkText);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDogInfo(int primary_key, TextView textView1, TextView textView2) {
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
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        DjangoAPI DogAPI = retrofit.create(DjangoAPI.class);

        Call<Pet> getCall = DogAPI.get_post_pk(primary_key);

        // GET 구현 코드
        getCall.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if(response.isSuccessful()){
                    getBreedInfo(response.body().getPET_BREED(), textView1);
                    getWalkInfo(response.body().getWALK_PLACE(), textView2);
                } else {
                    Toast.makeText(DetailActivity.this, "GET 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getBreedInfo(String primary_key, TextView textView1) {
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
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        DjangoAPI DogAPI = retrofit.create(DjangoAPI.class);

        Call<Breed> getCall = DogAPI.breed_get_pk(primary_key);

        // GET 구현 코드
        getCall.enqueue(new Callback<Breed>() {
            @Override
            public void onResponse(Call<Breed> call, Response<Breed> response) {
                if(response.isSuccessful()){
                    textView1.setText(response.body().getPET_METHOD()+"는 건강해요!");
                } else {
                    Toast.makeText(DetailActivity.this, "GET 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Breed> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getWalkInfo(String primary_key, TextView textView2) {
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
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        DjangoAPI DogAPI = retrofit.create(DjangoAPI.class);

        Call<Walk> getCall = DogAPI.walk_get_pk(primary_key);

        // GET 구현 코드
        getCall.enqueue(new Callback<Walk>() {
            @Override
            public void onResponse(Call<Walk> call, Response<Walk> response) {
                if(response.isSuccessful()){
                    textView2.setText(response.body().getWALK_METHOD());
                } else {
                    Toast.makeText(DetailActivity.this, "GET 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Walk> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
