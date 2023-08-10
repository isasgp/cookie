package com.example.cookie

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cookie.databinding.ActivityDogInfoBinding
import com.example.cookie.ui.theme.CookieTheme
import com.google.common.io.Resources.getResource
import java.util.Calendar

class DogInfoActivity : AppCompatActivity() {

    lateinit var btnMale : Button
    lateinit var btnFemale : Button
    lateinit var btnCalendar : Button
    lateinit var edtBirthYear : EditText
    lateinit var edtBirthMonth : EditText
    lateinit var edtBirthDay : EditText
    lateinit var btnSave : Button
    lateinit var btnBack : Button
    private var calendar = Calendar.getInstance()
    private var birthYear = calendar.get(Calendar.YEAR)
    private var birthMonth = calendar.get(Calendar.MONTH)
    private var birthDay = calendar.get(Calendar.DAY_OF_MONTH)
    lateinit var binding : ActivityDogInfoBinding
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnMale = findViewById(R.id.btn_male)
        btnFemale = findViewById(R.id.btn_female)

        btnMale.setBackgroundColor(getColor(R.color.white))
        btnFemale.setBackgroundColor(getColor(R.color.white))

        btnMale.setOnClickListener {
            btnMale.setBackgroundColor(getColor(R.color.beige))
            btnFemale.setBackgroundColor(getColor(R.color.white))
        }

        btnFemale.setOnClickListener {
            btnMale.setBackgroundColor(getColor(R.color.white))
            btnFemale.setBackgroundColor(getColor(R.color.beige))
        }

        btnCalendar = findViewById(R.id.btn_calendar)
        edtBirthYear = findViewById(R.id.edt_birthyear)
        edtBirthMonth = findViewById(R.id.edt_birthmonth)
        edtBirthDay = findViewById(R.id.edt_birthday)
        btnCalendar.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                edtBirthYear.setText(year.toString())
                edtBirthMonth.setText((month + 1).toString())
                edtBirthDay.setText(day.toString())
            }, birthYear, birthMonth, birthDay)
            datePickerDialog.show()
        }

        val dogCategory = listOf(
            "견종을 알려주세요",
            "믹스견",
            "말티즈",
            "토이 푸들",
            "미니어처 푸들",
            "스탠다드 푸들",
            "포메라니안",
            "비숑 프리제",
            "시츄",
            "치와와",
            "웰시코기 펨브록",
            "재패니즈 스피츠",
            "요크셔 테리어",
            "래브라도 리트리버",
            "골든 리트리버",
            "진돗개",
            "시바",
            "닥스훈트",
            "미니어쳐 슈나우저",
            "비글",
            "미니어처 핀션",
            "파피용",
            "사모예드",
            "보더 콜리",
            "프렌치 불독",
            "보스턴 테리어",
            "아메리칸 코커 스파니엘",
            "일글리쉬 코커 스파니엘",
            "잭 러셀 테리어",
            "퍼그",
            "그레이하운드",
            "페키니즈",
            "저먼 셰퍼드 독",
            "알래스카 말라뮤트",
            "도베르만",
            "베들린턴 테리어",
            "도사견",
            "달마시안",
            "롯트와일러",
            "올드 잉글리쉬 쉽독",
            "아프간 하운드",
            "삽살개",
            "풍산개",
            "아메리칸 불독"
        )

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dogCategory)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dogCategory.adapter = adapter1
        val dogWalk = listOf(
            "산책 빈도",
            "1일 3회 이상",
            "1일 2회",
            "1일 1회",
            "1주 4회 이상",
            "1주 2회 이상",
            "1주 1회",
            "산책을 거의 시키지 않음"
        )

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dogWalk)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dogWalk.adapter = adapter2

        val dogPlace = listOf(
            "산책 장소",
            "산, 숲길",
            "애견 운동장",
            "일반 보행자 도로",
            "일반 공원",
            "바닷가"
        )

        val adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dogPlace)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dogPlace.adapter = adapter3

        btnSave = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            val intent = Intent(this@DogInfoActivity, SkinInfoActivity::class.java)
            startActivity(intent)
        }

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this@DogInfoActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
