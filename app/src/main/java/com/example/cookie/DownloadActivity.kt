package com.example.cookie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookie.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        imageView = findViewById(R.id.test_image)

        // Django에서 전달받은 이미지 파일 URL
        val imageUrl = "http://3.35.85.32:8000/cookie/dncskin_segmentation/return/temp_image.jpg"

        // 이미지 다운로드 및 표시
        DownloadImageTask().execute(imageUrl)
    }

    private inner class DownloadImageTask : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            val imageUrl = params[0]

            try {
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream: InputStream = connection.inputStream
                return BitmapFactory.decodeStream(inputStream)

            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                // 이미지를 성공적으로 다운로드한 경우 ImageView에 설정
                imageView.setImageBitmap(result)
            } else {
                // 이미지 다운로드 실패 시 처리
            }
        }
    }
}
