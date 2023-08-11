package com.example.cookie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuideActivity1 extends AppCompatActivity {

    Button homeButton, nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide1);

        homeButton = findViewById(R.id.homeButton1);
        nextButton = findViewById(R.id.nextButton1);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity1.this, GuideActivity2.class);
                startActivity(intent);
            }
        });
    }
}