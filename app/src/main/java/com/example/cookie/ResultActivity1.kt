package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity1 : AppCompatActivity() {

    lateinit var result_image : ImageView
    lateinit var homeButton1: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result1_layout)

        result_image = findViewById(R.id.result_image)
        homeButton1 = findViewById(R.id.homeButton1)

        homeButton1.setOnClickListener {
            val intent = Intent(this@ResultActivity1, HomeMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
