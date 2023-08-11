package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FailedActivity : AppCompatActivity() {

    lateinit var btnHome : Button
    lateinit var btnRetry : Button
    lateinit var btnGuide : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed)

        btnHome = findViewById(R.id.btn_home)
        btnRetry = findViewById(R.id.btn_retry)
        btnGuide = findViewById(R.id.btn_guide)

        btnHome.setOnClickListener {
            val intent = Intent(this@FailedActivity, HomeMenuActivity::class.java)
            startActivity(intent)
        }
        btnRetry.setOnClickListener {
            val intent = Intent(this@FailedActivity, CameraViewActivity::class.java)
            startActivity(intent)
        }
        btnGuide.setOnClickListener {
            val intent = Intent(this@FailedActivity, GuideActivity1::class.java)
            startActivity(intent)
        }

    }
}