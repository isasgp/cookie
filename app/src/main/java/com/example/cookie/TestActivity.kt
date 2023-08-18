package com.example.cookie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestActivity : AppCompatActivity() {
    private lateinit var btn: Button // 회원이 아니신가요? 버튼 (회원가입 버튼)
    private lateinit var tv1: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        tv1=findViewById(R.id.textView1)
        btn=findViewById(R.id.button)
        btn.setOnClickListener {
            fetchDjangoJson()
        }
    }

    data class SignInfo (val USER_ID: String?, val PASSWORD: String?)
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
                        stringBuilder.append("USER_ID: ${item.USER_ID}\n")
                        stringBuilder.append("PASSWORD: ${item.PASSWORD}\n")
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


