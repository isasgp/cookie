package com.example.cookie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraViewActivity extends AppCompatActivity {
    Camera camera;
    ProcessCameraProvider cameraProvider;
    PreviewView previewView;
    ImageCapture imageCapture;
    ImageButton captureButton;
    ImageButton backButton;
    ImageButton guideButton;

    Preview preview;
    CameraSelector cameraSelector;
    String currentTime;


    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        previewView = findViewById(R.id.previewView);
        captureButton = findViewById(R.id.captureButton);
        backButton = findViewById(R.id.backButton);
        guideButton = findViewById(R.id.btn_guide);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        currentTime = "IMG_" + System.currentTimeMillis() + ".jpg";
        File photoFile = new File(getExternalFilesDir(null), currentTime);

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(CameraViewActivity.this, "카메라 연결 실패", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture(outputFileOptions);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraViewActivity.this, GuideActivity1.class);
                startActivity(intent);
            }
        });
    }
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);

        preview = new Preview.Builder()
                .build();

        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder().build();

        camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);
    }

    private void capturePicture(ImageCapture.OutputFileOptions outputFileOptions) {
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                //String msg = photoFile.getAbsolutePath();
                //Toast.makeText(CameraViewActivity.this, "촬영 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CameraViewActivity.this, ImageViewActivity.class);
                intent.putExtra("image_name", currentTime);
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(CameraViewActivity.this, "촬영 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

}