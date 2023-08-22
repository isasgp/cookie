package com.example.cookie

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DetailActivity3 : AppCompatActivity() {
    var homeButton: ImageButton? = null
    var nextButton: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail3)
    }
}