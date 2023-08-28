package com.example.cookie;

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var edtId: EditText // 아이디 입력창
    private lateinit var edtPassword: EditText // 비밀번호 입력창
    private lateinit var btnSignUp: Button // 회원이 아니신가요? 버튼 (회원가입 버튼)
    private lateinit var btnLogin: ImageButton // 로그인 버튼
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val temp = application as GlobalVariable
        temp.setUSER_ID(null);
        temp.setPET_ID(0);

        edtId = findViewById(R.id.edt_id)
        edtPassword = findViewById(R.id.edt_pass)
        btnSignUp = findViewById(R.id.btn_signup)
        btnLogin = findViewById(R.id.btn_login)

        if (ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this@LoginActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this@LoginActivity, PermissionActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            id = edtId.text.toString() // 아이디(이메일)
            val pw = edtPassword.text.toString() // 비밀번호

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val userData = LoginInfo(id, pw)
                selectLoginInfo(userData)
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectLoginInfo(data: LoginInfo) {
        // OkHttpClient 생성 및 인터셉터 설정
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        // Retrofit 빌더 생성
        val retrofit = Retrofit.Builder()
            .baseUrl(CookieAPI.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // OkHttpClient 설정
            .build()

        val loginApi = retrofit.create(CookieAPI::class.java)

        val selectCall = loginApi.readLoginInfo(data)

        selectCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseMessage = response.body()?.string()
                    if (responseMessage.equals("{\"message\": \"login_success\"}")) {
                        // 로그인 성공 처리
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val temp = application as GlobalVariable
                        temp.setUSER_ID(id)
                        val intent = Intent(this@LoginActivity, GetUserPetInfo::class.java)
                        startActivity(intent)
                    } else {
                        // 로그인 실패 처리
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, GetUserPetInfo::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Login", "Error: ${t.message}")
                Toast.makeText(this@LoginActivity, "로그인 오류", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, GetUserPetInfo::class.java)
                startActivity(intent)
            }
        })
    }
}
