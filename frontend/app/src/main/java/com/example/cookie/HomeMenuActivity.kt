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

    private lateinit var btnNoDogInfo : ImageButton
    private lateinit var btnNoDiagnosis : ImageButton
    private lateinit var btnRecord: ImageButton
    private lateinit var btnDiagnosis: ImageButton
    private lateinit var btnSetting: ImageButton
    private lateinit var btnCommunity: ImageButton

    private var isDogInfo : Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemenu)

        val temp = application as GlobalVariable
        isDogInfo = temp.getPET_ID()

        btnNoDogInfo = findViewById(R.id.no_doginfo)
        btnNoDiagnosis = findViewById(R.id.no_diagnosis)

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

        if (isDogInfo != 0) { // 강아지 정보 입력했으면
            btnNoDiagnosis.visibility = View.VISIBLE // 진단 버튼 활성화
            btnNoDogInfo.visibility = View.INVISIBLE
        } else if (isDogInfo == 0){ // 강아지 정보를 입력하지 않았으면
            btnNoDiagnosis.visibility = View.INVISIBLE
            btnNoDogInfo.visibility = View.VISIBLE // 강아지 정보 입력 버튼 활성화
        }

        btnNoDogInfo.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, DogInfoActivity::class.java)
            startActivity(intent)
        }

        btnNoDiagnosis.setOnClickListener {
            val intent = Intent(this@HomeMenuActivity, GuideActivity1::class.java)
            startActivity(intent)
        }

        btnRecord.setOnClickListener {
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
                btnHome.setImageResource(R.drawable.cookie_open)
            }
            else{
                homePage.visibility = View.VISIBLE
                btnHome.setImageResource(R.drawable.cookie_close)
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
