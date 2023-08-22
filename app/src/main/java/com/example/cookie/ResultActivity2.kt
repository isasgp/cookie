package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
class ResultActivity2 : AppCompatActivity() {

    lateinit var result_image : ImageView
    lateinit var homeButton2: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result2_layout)

        result_image = findViewById(R.id.result_image)
        homeButton2 = findViewById(R.id.homeButton2)

        homeButton2.setOnClickListener {
            val intent = Intent(this@ResultActivity2, HomeMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
