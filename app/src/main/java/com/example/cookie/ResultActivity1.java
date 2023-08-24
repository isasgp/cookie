package com.example.cookie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity1 extends AppCompatActivity {

    private ImageView result_image;
    private ImageButton homeButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result1_layout);

        result_image = findViewById(R.id.result_image);
        homeButton1 = findViewById(R.id.homeButton1);

        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity1.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
