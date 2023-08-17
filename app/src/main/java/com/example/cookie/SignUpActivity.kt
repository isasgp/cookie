package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    companion object {
        lateinit var auth: FirebaseAuth // 파이어베이스 인증 객체
    }

    private lateinit var edtId: EditText // 아이디 입력창
    private lateinit var edtPassword: EditText // 비밀번호 입력창
    private lateinit var edtPasswordCheck: EditText // 비밀번호 확인 입력창
    private lateinit var btnSignUp: ImageButton // 회원가입 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth
        edtId = findViewById(R.id.edt_id)
        edtPassword = findViewById(R.id.edt_pass)
        edtPasswordCheck = findViewById(R.id.edt_passck)
        btnSignUp = findViewById(R.id.btn_signup)

        btnSignUp.setOnClickListener {
            val id = edtId.text.toString() // 아이디(이메일)
            val pw = edtPassword.text.toString() // 비밀번호
            val pwcheck = edtPasswordCheck.text.toString() // 비밀번호 확인

            if (id.isEmpty() || pw.isEmpty() || pwcheck.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "회원정보를 모두 기입해주세요.", Toast.LENGTH_SHORT).show()
            } else if (pw != pwcheck) {
                Toast.makeText(this@SignUpActivity, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(id, pw)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    if (sendTask.isSuccessful) {
                                        // 인증 메일 전송 성공
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "메일이 전송되었습니다. 이메일 인증 시 회원가입이 완료됩니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        // 인증 메일 전송 실패
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "메일 전송 실패",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else { // 회원가입 실패
                            Toast.makeText(this@SignUpActivity, "회원 가입 실패", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }
}