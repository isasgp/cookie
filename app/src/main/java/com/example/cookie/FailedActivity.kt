package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FailedActivity : AppCompatActivity() {

    lateinit var btnHome : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed)

        btnHome = findViewById(R.id.btn_home)
        btnHome.setOnClickListener {
            val intent = Intent(this@FailedActivity, HomeMenuActivity::class.java)
            startActivity(intent)
        }
    }
}