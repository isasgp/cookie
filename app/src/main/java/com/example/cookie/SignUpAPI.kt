package com.example.cookie

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpAPI {
    companion object {
        public val API_URL = "http://3.35.85.32:8000/"
    }

    @POST("cookie")
    fun insertSignInfo(@Body data: SignUpActivity.SignInfo): Call<ResponseBody>
}
