package com.example.cookie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 웹에서 레트로픽방식으로 주소에 필요한 내용을 어떻게 보낼 것인지
interface ApiService {
    companion object {  // 정적 필드(static)
        // 주소 담기
        public val API_URL = "http://3.34.247.181:8000/"
    }

    // GET으로 cookie를 통해 웹사이트 접근
    @GET("cookie")
    // 안드로이드에서 get_tests 부를 때 파라메터 하나 들어가는데
    // 그 파라메터가
    // format이라는 쿼리에 들어가는 값, json이라는 문자열을 파라메터로 전달해 줘야함
    fun get_tests(@Query("format") json:String) : Call<List<LoginActivity.SignInfo>>
}