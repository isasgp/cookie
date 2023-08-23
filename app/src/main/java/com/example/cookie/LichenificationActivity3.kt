package com.example.cookie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LichenificationActivity3 : AppCompatActivity() {

    private lateinit var btnHome: ImageButton // 홈 버튼
    private lateinit var btnLink1: ImageButton // 약용 샴푸 링크 버튼
    private lateinit var btnLink2: ImageButton // 일상 관리 링크 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lichenification3)

        btnHome = findViewById(R.id.homeButton1)
        btnLink1 = findViewById(R.id.link1)
        btnLink2 = findViewById(R.id.link2)

        btnHome.setOnClickListener {
            val intent = Intent(this@LichenificationActivity3, HomeMenuActivity::class.java)
            startActivity(intent)
        }

        btnLink1.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coupang.com/np/search?component=&q=%ED%95%98%EC%9D%B4%EB%93%9C%EB%A1%9C+%EC%BD%94%EB%A5%B4%ED%8B%B0%EC%86%90+%EC%95%BD%EC%9A%A9%EC%83%B4%ED%91%B8&channel=auto"))
            startActivity(intent)
        }

        btnLink2.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coupang.com/np/search?component=&q=%EB%B0%98%EB%A0%A4%EA%B2%AC+%EC%98%A4%EB%A9%94%EA%B0%803+%EC%98%81%EC%96%91%EC%A0%9C&channel=auto"))
            startActivity(intent)
        }
    }
}