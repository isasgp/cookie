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

        // 이거 자바 코드 그대로 코틀린 변환 버튼만 누른건데 이런 형식으로 쓰면 될듯
           // 앞 액티비티인 ImageViewActivity에서 putExtra 해준거 받아오는 코드
                                                                    // CamerViewActivity에서 찍은 이미지 이름 받아오는 것
        // val photoFile = File(getExternalFilesDir(null), img_name)   // 파일 위치, 파일 이름으로 파일 받아와서 생성 하는 코드
                                                                         // getExternalFilesDir(null) 메소드가 앱 설치시 자동으로 생성되는 앱 내부의 절대 공간에 접근하는 메소드 외부에서는 보이지 않음
                                                                         // 아마 이 파일이 .jpg 파일 그대로 일 듯
        // 이 밑으로 파일 처리해서 Upload 구현하면 될 듯 (파이팅!!)


        // 갤러리에서 사진 선택
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
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
            // val file = File(cacheDir, "temp_image.jpg")
            val intent = intent
            val img_name = intent.getStringExtra("image_name")
            val file = File(getExternalFilesDir(null), img_name)
            val temp_file = File("temp_image.jpg")
            file.renameTo(temp_file)
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
