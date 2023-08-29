package com.example.cookie;

import static com.example.cookie.DjangoAPI.API_URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity1 extends AppCompatActivity {

    private ImageView result_image;
    private ImageButton homeButton1;
    private TextView result_text;
    private ImageButton delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result1_layout);

        result_image = findViewById(R.id.result_image);
        homeButton1 = findViewById(R.id.homeButton1);
        result_text = findViewById(R.id.pet_name);
        delete_button =findViewById(R.id.button);

        // Django에서 전달받을 이미지 URL
        String imageUrl = "http://3.35.85.32:8000/cookie/dncskin_segmentation/return/blended_image.jpg";

        // 이미지 가져오는 함수
        new DownloadImageTask().execute(imageUrl);

        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity1.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });

        // 앱 전체 전역변수 받아오기
        GlobalVariable temp = (GlobalVariable) getApplication();
        int pk= temp.getPET_ID();

        // 강아지 이름 받아오는 메소드
        getDogInfo(pk, result_text);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImageFile(); // 이미지 삭제 함수
                Intent intent = new Intent(ResultActivity1.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // 이미지를 성공적으로 다운로드한 경우 ImageView에 설정
                result_image.setImageBitmap(rotateImage(result, 90));
            } else {
                // 이미지 다운로드 실패 시 처리
            }
        }
    }

    private void getDogInfo(int primary_key, TextView textView) {
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
                    textView.setText(response.body().getPET_NAME()+"는 건강해요!");
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

    public void deleteImageFile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CookieAPI api = retrofit.create(CookieAPI.class);

        Call<ResponseBody> call = api.deleteFile();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Toast.makeText(ResultActivity1.this, "파일 삭제 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResultActivity1.this, "파일 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ResultActivity1.this, "파일 삭제", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),src.getHeight(), matrix, true);
    }
}
