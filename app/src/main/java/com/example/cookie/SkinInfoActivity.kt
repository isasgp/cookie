package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SkinInfoActivity : AppCompatActivity() {

    lateinit var btnBack : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin_info)

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this@SkinInfoActivity, DogInfoActivity::class.java)
            startActivity(intent)
        }
    }
}