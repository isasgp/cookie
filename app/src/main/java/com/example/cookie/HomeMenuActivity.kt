package com.example.cookie

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout


class HomeMenuActivity : AppCompatActivity() {

    var ishomePageOpen : Boolean = false
    lateinit var LeftAnim : Animation
    lateinit var RightAnim : Animation
    lateinit var btnHome : Button
    lateinit var homePage : LinearLayout

    private lateinit var btnMenu1: Button
    private lateinit var btnMenu2: Button
    private lateinit var btnMenu3: Button
    private lateinit var btnMenu4: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemenu)

        LeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left)
        RightAnim = AnimationUtils.loadAnimation(this,R.anim.translate_right)

        LeftAnim.setAnimationListener(SlidinghomePageAnimationListener())
        RightAnim.setAnimationListener(SlidinghomePageAnimationListener())

        btnHome = findViewById(R.id.btn_home)
        homePage = findViewById(R.id.page)

        btnMenu1 = findViewById(R.id.btn_home_menu1)
        btnMenu2 = findViewById(R.id.btn_home_menu2)
        btnMenu3 = findViewById(R.id.btn_home_menu3)
        btnMenu4 = findViewById(R.id.btn_home_menu4)


        btnMenu1.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
            startActivity(intent)
        }

        btnMenu2.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
            startActivity(intent)
        }

        btnMenu3.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
            startActivity(intent)
        }

        btnMenu4.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
            startActivity(intent)
        }

        btnHome.setOnClickListener {
            if (ishomePageOpen) {
                homePage.startAnimation(RightAnim)
            }
            else{
                homePage.visibility = View.VISIBLE
                homePage.startAnimation(LeftAnim)
            }
        }
    }

    private inner class SlidinghomePageAnimationListener : Animation.AnimationListener {

        override fun onAnimationEnd(animation: Animation?) : Unit {
            if(ishomePageOpen){
                homePage.visibility=View.INVISIBLE

                btnHome.text="메뉴 보기"
                ishomePageOpen = false
            }
            else{
                btnHome.text="닫기"
                ishomePageOpen = true
            }
        }

        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    }
}
