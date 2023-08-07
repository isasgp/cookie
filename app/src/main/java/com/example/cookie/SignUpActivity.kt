package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtId: EditText // 아이디 입력창
    private lateinit var edtPassword: EditText // 비밀번호 입력창
    private lateinit var edtPasswordCheck: EditText // 비밀번호 확인 입력창
    private lateinit var btnSignUp: Button // 회원가입 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edtId = findViewById(R.id.edt_id)
        edtPassword = findViewById(R.id.edt_pass)
        edtPasswordCheck = findViewById(R.id.edt_passck)
        btnSignUp = findViewById(R.id.btn_signup)

        btnSignUp.setOnClickListener {
            Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}