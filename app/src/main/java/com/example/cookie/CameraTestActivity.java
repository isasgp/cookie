package com.example.cookie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public class CameraTestActivity extends AppCompatActivity {

    PreviewView previewView;
    ImageButton captureButton;
    ProcessCameraProvider processCameraProvider;
    int lensFacing = CameraSelector.LENS_FACING_BACK;
    ImageCapture imageCapture;
    ImageButton backButton;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        
        // 카메라 나중에 자세히 구현필요

        previewView = findViewById(R.id.previewView);
        captureButton = findViewById(R.id.captureButton);
        backButton = findViewById(R.id.backButton);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(CameraTestActivity.this, "카메라 연결 실패", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));


        if (ActivityCompat.checkSelfPermission(CameraTestActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            bindPreview();
            bindImageCapture();
        }

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(ContextCompat.getMainExecutor(CameraTestActivity.this),
                        new ImageCapture.OnImageCapturedCallback() {
                            @Override
                            public void onCaptureSuccess(@NonNull ImageProxy image) {
                                super.onCaptureSuccess(image);
                                Bitmap capturedBitmap = imageProxyToBitmap(image);
                                Toast.makeText(CameraTestActivity.this, "캡쳐 성공", Toast.LENGTH_LONG);
                                /*
                                Intent intent = new Intent(CameraTestActivity.this, ImageViewActivity.class);
                                intent.putExtra("capturedImage", capturedBitmap);
                                startActivity(intent);*/
                            }
                            @Override
                            public void onError(@NonNull ImageCaptureException exception) {
                                // 이미지 캡처 오류 처리
                                Toast.makeText(CameraTestActivity.this, "캡쳐 실패", Toast.LENGTH_LONG);
                            }
                        }
                );
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void bindPreview() {
        previewView.setScaleType(PreviewView.ScaleType.FIT_CENTER);
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3) //디폴트 표준 비율
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        processCameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setScaleType(PreviewView.ScaleType.FIT_CENTER);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
    }

    void bindImageCapture() {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        imageCapture = new ImageCapture.Builder()
                .build();

        processCameraProvider.bindToLifecycle(this, cameraSelector, imageCapture);
    }
    private Bitmap imageProxyToBitmap(ImageProxy imageProxy) {
        ByteBuffer buffer = imageProxy.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    @Override
    protected void onPause() {
        super.onPause();
        processCameraProvider.unbindAll();
    }
}