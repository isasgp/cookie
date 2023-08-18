// LoginActivity.kt
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookie.DogInfoActivity
import com.example.cookie.LoginAPI
import com.example.cookie.R
import com.example.cookie.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    private lateinit var btnLogin: Button // 로그인 버튼
    private lateinit var loginApi: LoginAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtId = findViewById(R.id.edt_id)
        edtPassword = findViewById(R.id.edt_pass)
        btnSignUp = findViewById(R.id.btn_signup)
        btnLogin = findViewById(R.id.btn_login)

        // Retrofit 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginAPI.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        loginApi = retrofit.create(LoginAPI::class.java)

        btnLogin.setOnClickListener {
            val id = edtId.text.toString() // 아이디(이메일)
            val pw = edtPassword.text.toString() // 비밀번호

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                performLogin(id, pw)
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(userId: String, password: String) {
        // GET 요청으로 로그인 처리
        val call = loginApi.getLoginInfo(userId, password)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, DogInfoActivity::class.java)
                    startActivity(intent)
                    // 로그인 성공시 처리
                } else {
                    Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    // 로그인 실패시 처리
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Login", "Error: ${t.message}")
                Toast.makeText(this@LoginActivity, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
