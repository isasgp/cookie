package com.example.cookie;

import static com.example.cookie.DjangoAPI.API_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetUserPetInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView lalaGifImg = findViewById(R.id.iv_loading);
        Glide.with(this).load(R.raw.loading_image).into(lalaGifImg);

        GlobalVariable temp = (GlobalVariable) getApplication();
        String pk= temp.getUSER_ID();

        UserInfoGET(pk);
    }

    private void UserInfoGET(String primary_key) {
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

        Call<CookieUser> getCall = DogAPI.user_get_pk(primary_key);

        // GET 구현 코드
        getCall.enqueue(new Callback<CookieUser>() {
            @Override
            public void onResponse(Call<CookieUser> call, Response<CookieUser> response) {
                if(response.isSuccessful()){
                    Toast.makeText(GetUserPetInfo.this, "GET 성공", Toast.LENGTH_SHORT).show();
                    GlobalVariable temp = (GlobalVariable) getApplication();
                    temp.setPET_ID(response.body().getPET_ID());

                    Intent intent = new Intent(GetUserPetInfo.this, HomeMenuActivity.class);
                    startActivity(intent);
                    
                } else {
                    Toast.makeText(GetUserPetInfo.this, "GET 실패", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GetUserPetInfo.this, HomeMenuActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<CookieUser> call, Throwable t) {
                Toast.makeText(GetUserPetInfo.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GetUserPetInfo.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}