package com.example.cookie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class UploadPhotoActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_PICK = 1
    }

    private lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photo)

        // 갤러리에서 사진 선택
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            // 선택한 이미지의 URI 가져오기
            data?.data?.let { uri ->
                selectedImageUri = uri

                // 사진 업로드 실행
                uploadPhotoToServer(selectedImageUri)
            }
        }
    }

    private fun uploadPhotoToServer(imageUri: Uri) {
        val retrofit = Retrofit.Builder()
            .baseUrl(CookieAPI.API_URL)
            .build()

        val apiService = retrofit.create(CookieAPI::class.java)

        try {
            // 선택한 이미지의 실제 경로 가져오기
            val inputStream: InputStream = contentResolver.openInputStream(imageUri)!!
            val file = File(cacheDir, "temp_image.jpg")
            val fileOutputStream = file.outputStream()
            inputStream.copyTo(fileOutputStream)
            fileOutputStream.close()

            // 파일의 경로를 가져온 후 MultipartBody.Part로 변환
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)

            // 사진 업로드 API 호출
            val call = apiService.uploadPhoto(filePart)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val intent = Intent(this@UploadPhotoActivity, LoadingActivity::class.java)
                        startActivity(intent)
                    } else {
                        // 사진 업로드 실패
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
