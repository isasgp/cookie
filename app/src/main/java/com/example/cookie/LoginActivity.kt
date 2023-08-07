package com.example.cookie

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : AppCompatActivity() {
    private lateinit var edtId: EditText // 아이디 입력창
    private lateinit var edtPassword: EditText // 비밀번호 입력창
    private lateinit var btnSignUp : Button // 회원이 아니신가요? 버튼
    private lateinit var btnLogin : Button // 로그인 버튼

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtId = findViewById(R.id.edt_id)
        edtPassword = findViewById(R.id.edt_pass)
        btnSignUp = findViewById(R.id.btn_signup)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            val id = edtId.text.toString().trim() // 아이디
            val pw = edtPassword.text.toString().trim() // 비밀번호

            if (id.isEmpty() || pw.isEmpty()) {
                makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val task = SelectData()
                task.execute("http://ip주소/~~~~.php", id, pw)
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    inner class SelectData : AsyncTask<String, Void, String>() {
        private var progressDialog: ProgressDialog? = null
        override fun doInBackground(vararg params: String): String {
            val serverURL = params[0]
            val id = params[1]
            val pw = params[2]

            val postParameters = "id=$id&password=$pw"

            try {
                val url = URL(serverURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connect()

                val outputStream = httpURLConnection.outputStream
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()

                val responseStatusCode = httpURLConnection.responseCode
                Log.d(TAG, "POST response code - $responseStatusCode")

                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream
                }

                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)
                val sb = StringBuilder()
                var line: String? = null

                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line)
                }

                bufferedReader.close()
                Log.d("php 값 :", sb.toString())
                return sb.toString()
            } catch (e: Exception) {
                Log.d(TAG, "SelectData: Error", e)
                return "Error " + e.message
            }
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            if (result == "success") {
                // 로그인 성공
                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                // 로그인 성공 시 homeactivity로 전환
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // 로그인 실패
                Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
