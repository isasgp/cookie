package com.example.cookie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide

class LoadingActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val lalaGifImg: ImageView = findViewById(R.id.iv_loading)
        Glide.with(this).load(R.raw.loading_image).into(lalaGifImg)

    }
}
