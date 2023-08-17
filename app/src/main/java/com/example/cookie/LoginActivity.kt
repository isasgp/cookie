package com.example.cookie

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var btn: Button // 회원이 아니신가요? 버튼 (회원가입 버튼)
    private lateinit var tv1: TextView
    private lateinit var tv2: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        tv1=findViewById(R.id.textView1)
        tv2=findViewById(R.id.textView2)
        btn=findViewById(R.id.button)
        btn.setOnClickListener {
            fetchDjangoJson()
        }
    }

    private fun fetchDjangoJson() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiService.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val tests = apiService.get_tests("json")

        tests.enqueue(object : Callback<List<SignInfo>> {
            override fun onResponse(call: Call<List<SignInfo>>, response: Response<List<SignInfo>>) {
                if (response.isSuccessful) {
                    val mList = response.body()!!

                    val stringBuilder = StringBuilder()
                    for (item in mList) {
                        stringBuilder.append("user_id: ${item.user_id}\n")
                        stringBuilder.append("password: ${item.password}\n")
                    }

                    runOnUiThread {
                        tv1.text = stringBuilder.toString()
                    }
                }
            }
            override fun onFailure(call: Call<List<SignInfo>>, t: Throwable) {
                Log.e("D_tests", "OnFailure+${t.message}")
            }
        })
    }
}

//class LoginActivity : AppCompatActivity() {
//    companion object {
//        lateinit var auth: FirebaseAuth
//        var email: String? = null
//        lateinit var db: FirebaseFirestore
//        lateinit var storage: FirebaseStorage
//        fun checkAuth(): Boolean {
//            var currentUser = auth.currentUser
//            return currentUser?.let {
//                email = currentUser.email
//                currentUser.isEmailVerified
//            } ?: let {
//                false
//            }
//        }
//    }
//
//    private lateinit var edtId: EditText // 아이디 입력창
//    private lateinit var edtPassword: EditText // 비밀번호 입력창
//    private lateinit var btnSignUp: Button // 회원이 아니신가요? 버튼 (회원가입 버튼)
//    private lateinit var btnLogin: ImageButton // 로그인 버튼
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        auth = Firebase.auth
//        db = FirebaseFirestore.getInstance()
//        storage = Firebase.storage
//
//        edtId = findViewById(R.id.edt_id)
//        edtPassword = findViewById(R.id.edt_pass)
//        btnSignUp = findViewById(R.id.btn_signup)
//        btnLogin = findViewById(R.id.btn_login)
//
//        // 권한 없을 시 해당 액티비티로 이동
//        if (ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//            ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//            ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            val intent = Intent(this@LoginActivity, PermissionActivity::class.java)
//            startActivity(intent)
//        }
//
//        btnLogin.setOnClickListener {
//            val id = edtId.text.toString() // 아이디(이메일)
//            val pw = edtPassword.text.toString() // 비밀번호
//
//            if (id.isEmpty() || pw.isEmpty()) {
//                makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
//            } else {
//                auth.signInWithEmailAndPassword(id, pw)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            if (checkAuth()) {
//                                email = id
//                                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
//                                val intent = Intent(this@LoginActivity, DogInfoActivity::class.java)
//                                startActivity(intent)
//                            } else {
//                                Toast.makeText(this@LoginActivity, "이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
//                            }
//                        } else {
//                            Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@LoginActivity, HomeMenuActivity::class.java)
//                            startActivity(intent)
//                        }
//                    }
//            }
//        }
//
//        btnSignUp.setOnClickListener {
//            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}
