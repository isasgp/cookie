package com.example.cookie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.ButtonBarLayout


class HomeMenuActivity : AppCompatActivity() {

    var isPageOpen : Boolean = false
    lateinit var LeftAnim : Animation
    lateinit var RightAnim : Animation
    lateinit var button : Button
    lateinit var page : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemenu)

        LeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left)
        RightAnim = AnimationUtils.loadAnimation(this,R.anim.translate_right)

        LeftAnim.setAnimationListener(SlidingPageAnimationListener())
        RightAnim.setAnimationListener(SlidingPageAnimationListener())

        button = findViewById(R.id.button)
        page = findViewById(R.id.page)

        button.setOnClickListener {
            if (isPageOpen) {
                page.startAnimation(RightAnim)
            }
            else{
                page.visibility = View.VISIBLE
                page.startAnimation(LeftAnim)
            }
        }
    }

    private inner class SlidingPageAnimationListener : Animation.AnimationListener {

        override fun onAnimationEnd(animation: Animation?) : Unit {
            if(isPageOpen){
                page.visibility=View.INVISIBLE

                button.text="메뉴 보기"
                isPageOpen = false
            }
            else{
                button.text="닫기"
                isPageOpen = true
            }
        }

        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    }
}
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
//
//class HomeMenuActivity : AppCompatActivity() {
//
//    private lateinit var btnMenu1: Button
//    private lateinit var btnMenu2: Button
//    private lateinit var btnMenu3: Button
//    private lateinit var btnMenu4: Button
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_homemenu)
//
//        btnMenu1 = findViewById(R.id.btn_home_menu1)
//        btnMenu2 = findViewById(R.id.btn_home_menu2)
//        btnMenu3 = findViewById(R.id.btn_home_menu3)
//        btnMenu4 = findViewById(R.id.btn_home_menu4)
//
//
//        btnMenu1.setOnClickListener {
//            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
//            startActivity(intent)
//        }
//
//        btnMenu2.setOnClickListener {
//            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
//            startActivity(intent)
//        }
//
//        btnMenu3.setOnClickListener {
//            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
//            startActivity(intent)
//        }
//
//        btnMenu4.setOnClickListener {
//            val intent = Intent(this@HomeMenuActivity, LoadingActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}