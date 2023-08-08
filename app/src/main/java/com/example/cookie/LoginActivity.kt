package com.example.cookie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class LoginActivity : AppCompatActivity() {
    companion object {
        lateinit var auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage
        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }
    }

    private lateinit var edtId: EditText // 아이디 입력창
    private lateinit var edtPassword: EditText // 비밀번호 입력창
    private lateinit var btnSignUp: Button // 회원이 아니신가요? 버튼 (회원가입 버튼)
    private lateinit var btnLogin: Button // 로그인 버튼

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage

        edtId = findViewById(R.id.edt_id)
        edtPassword = findViewById(R.id.edt_pass)
        btnSignUp = findViewById(R.id.btn_signup)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            val id = edtId.text.toString() // 아이디(이메일)
            val pw = edtPassword.text.toString() // 비밀번호

            if (id.isEmpty() || pw.isEmpty()) {
                makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(id, pw)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            if (checkAuth()) {
                                email = id
                                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@LoginActivity, "이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
