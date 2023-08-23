package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LichenificationActivity2 : AppCompatActivity() {

    private lateinit var btnHome: ImageButton // 홈 버튼
    private lateinit var btnNext: ImageButton // 다음 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lichenification2)

        btnHome = findViewById(R.id.homeButton1)
        btnNext = findViewById(R.id.how_button)

        btnHome.setOnClickListener {
            val intent = Intent(this@LichenificationActivity2, HomeMenuActivity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val intent = Intent(this@LichenificationActivity2, LichenificationActivity3::class.java)
            startActivity(intent)
        }
    }
}