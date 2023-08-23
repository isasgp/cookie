package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var btnHome: ImageButton // 홈 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btnHome = findViewById(R.id.homeButton1)

        btnHome.setOnClickListener {
            val intent = Intent(this@DetailActivity, HomeMenuActivity::class.java)
            startActivity(intent)
        }
    }
}