package com.example.cookie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtId: EditText // 아이디 입력창
    private lateinit var edtPassword: EditText // 비밀번호 입력창
    private lateinit var edtPasswordCheck: EditText // 비밀번호 확인 입력창
    private lateinit var btnSignUp: ImageButton // 회원가입 버튼
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

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
                val userData = SignInfo(id, pw)
                insertSignInfo(userData)
            }
        }
    }

    private fun insertSignInfo(data: SignInfo) {
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
            .baseUrl(LoginAPI.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // OkHttpClient 설정
            .build()

        val loginApi = retrofit.create(LoginAPI::class.java)

        val insertCall = loginApi.createSignInfo(data)

        insertCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("D_insert", "OnFailure+${t.message}")
                Toast.makeText(this@SignUpActivity, "회원가입 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}