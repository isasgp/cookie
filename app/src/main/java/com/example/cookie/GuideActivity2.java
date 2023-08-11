package com.example.cookie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuideActivity2 extends AppCompatActivity {

    Button homeButton, nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);

        homeButton = findViewById(R.id.homeButton2);
        nextButton = findViewById(R.id.nextButton2);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity2.this, GuideActivity3.class);
                startActivity(intent);
            }
        });
    }
}