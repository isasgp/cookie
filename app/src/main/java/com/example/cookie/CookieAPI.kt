package com.example.cookie

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface CookieAPI {
    companion object {
        public val API_URL = "http://3.35.85.32:8000/"
    }
    @POST("cookie/sign_up/")
    fun createSignInfo(@Body data: SignInfo): Call<ResponseBody>

    @POST("cookie/log_in/")
    fun readLoginInfo(@Body data: LoginInfo): Call<ResponseBody>

    @Multipart
    @POST("cookie/model_views")
    fun uploadPhoto(@Part file: MultipartBody.Part): Call<ResponseBody>

    // 이미지 삭제를 위한 DELETE 요청
//    @DELETE("delete_photo/{imagePath}")
//    fun deletePhoto(@Path("imagePath") imagePath: String?): Call<ResponseBody?>?
}
data class SignInfo(val user_id: String, val password: String)
data class LoginInfo(val user_id: String, val password: String)