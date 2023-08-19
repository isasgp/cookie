package com.example.cookie

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// 웹에서 레트로픽방식으로 주소에 필요한 내용을 어떻게 보낼 것인지
interface LoginAPI {
    companion object {
        public val API_URL = "http://3.35.85.32:8000/"
    }
    @POST("cookie/sign_up/")
    fun createSignInfo(@Body data: SignInfo): Call<ResponseBody>

    @POST("cookie/log_in/")
    fun readLoginInfo(@Body data: LoginInfo): Call<ResponseBody>
}
data class SignInfo(val user_id: String, val password: String)
data class LoginInfo(val user_id: String, val password: String)