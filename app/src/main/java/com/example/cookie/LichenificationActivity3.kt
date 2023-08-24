package com.example.cookie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LichenificationActivity3 : AppCompatActivity() {

    private lateinit var btnHome: ImageButton // 홈 버튼
    private lateinit var btnLink1: ImageButton // 사료 링크 버튼
    private lateinit var btnLink2: ImageButton // 크림 링크 버튼
    private lateinit var btnLink3: ImageButton //오메가 3 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lichenification3)

        btnHome = findViewById(R.id.homeButton1)
        btnLink1 = findViewById(R.id.link1)
        btnLink2 = findViewById(R.id.link2)
        btnLink3 = findViewById(R.id.link3)

        btnHome.setOnClickListener {
            val intent = Intent(this@LichenificationActivity3, HomeMenuActivity::class.java)
            startActivity(intent)
        }

        btnLink1.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coupang.com/np/search?component=&q=%EC%A0%80%EC%95%8C%EB%9F%AC%EC%A7%80+%EC%82%AC%EB%A3%8C&channel=user"))
            startActivity(intent)
        }

        btnLink2.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coupang.com/np/search?component=&q=%EA%B0%95%EC%95%84%EC%A7%80+%EB%B3%B4%EC%8A%B5%EC%A0%9C&channel=user"))
            startActivity(intent)
        }
        btnLink3.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coupang.com/np/search?component=&q=%EB%B0%98%EB%A0%A4%EA%B2%AC+%EC%98%A4%EB%A9%94%EA%B0%803+%EC%98%81%EC%96%91%EC%A0%9C&channel=auto"))
            startActivity(intent)
        }
    }
}