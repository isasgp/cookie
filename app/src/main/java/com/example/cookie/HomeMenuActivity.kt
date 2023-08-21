package com.example.cookie

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout


class HomeMenuActivity : AppCompatActivity() {

    var ishomePageOpen : Boolean = false
    lateinit var LeftAnim : Animation
    lateinit var RightAnim : Animation
    lateinit var btnHome : ImageButton
    lateinit var homePage : LinearLayout

    private lateinit var btnRecord: ImageButton
    private lateinit var btnDiagnosis: ImageButton
    private lateinit var btnSetting: ImageButton
    private lateinit var btnCommunity: ImageButton

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

        btnRecord = findViewById(R.id.btn_record)
        btnDiagnosis = findViewById(R.id.btn_diagnosis)
        btnSetting = findViewById(R.id.btn_setting)
        btnCommunity = findViewById(R.id.btn_community)


        btnRecord.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, UploadPhotoActivity::class.java)
            startActivity(intent)
        }

        btnDiagnosis.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, GuideActivity1::class.java)
            startActivity(intent)
        }

        btnSetting.setOnClickListener {

        }

        btnCommunity.setOnClickListener {
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

                ishomePageOpen = false
            }
            else{
                ishomePageOpen = true
            }
        }

        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    }
}
