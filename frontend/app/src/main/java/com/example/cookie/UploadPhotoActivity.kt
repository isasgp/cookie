package com.example.cookie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class UploadPhotoActivity : AppCompatActivity() {

    // 이미지 선택 요청 코드
    companion object {
        const val REQUEST_IMAGE_PICK = 1
    }

    // 선택한 이미지의 URI를 저장할 변수
    private lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val lalaGifImg: ImageView = findViewById(R.id.iv_loading)
        Glide.with(this).load(R.raw.loading_image).into(lalaGifImg)

        uploadPhotoToServer()

        // 갤러리에서 사진 선택
        // val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            // 선택한 이미지의 URI 가져오기
            data?.data?.let { uri ->
                selectedImageUri = uri

                // 사진 업로드 실행
                uploadPhotoToServer()
            }
        }
    }

    private fun uploadPhotoToServer() {
        // Retrofit을 사용하여 서버와 통신할 Retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl(CookieAPI.API_URL)
            .build()

        // CookieAPI 인터페이스를 사용하여 서버와 통신하는 API 서비스 생성
        val apiService = retrofit.create(CookieAPI::class.java)




        try {
            val intent = intent
            val img_name = intent.getStringExtra("image_name")

            val file = File(getExternalFilesDir(null), img_name)
            val temp_file = File(file.parentFile, "temp_image.jpg")

            if (file.exists()) {
                // 원본 이미지의 실제 경로 가져오기
                val inputStream: InputStream = FileInputStream(file)

                // 이미지를 임시 파일로 복사
                val fileOutputStream = FileOutputStream(temp_file)
                inputStream.copyTo(fileOutputStream)
                fileOutputStream.close()
            }

            // 파일의 경로를 가져온 후 MultipartBody.Part로 변환
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), temp_file)
            val filePart = MultipartBody.Part.createFormData("temp_image", temp_file.name, requestBody)

            // 사진 업로드 API 호출
            val call = apiService.uploadPhoto(filePart)

            // 업로드 결과에 대한 비동기 처리
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        try {
                            val responseMessage = response.body()?.string()
                            if (responseMessage.equals("{\"message\":\"True\"}")) {
                                val intent = Intent(this@UploadPhotoActivity, ResultActivity2::class.java)
                                startActivity(intent)
                            } else if (responseMessage.equals("{\"message\":\"False\"}")){
                                val intent = Intent(this@UploadPhotoActivity, ResultActivity1::class.java)
                                startActivity(intent)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // 네트워크 오류 등으로 인한 업로드 실패
                }
            })
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}