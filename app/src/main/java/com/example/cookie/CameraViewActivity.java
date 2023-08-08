package com.example.cookie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CameraViewActivity extends AppCompatActivity {
    private PreviewView previewView;
    private Button startButton, stopButton, changeButton, flashButton;
    private ProcessCameraProvider processCameraProvider;
    private final int backFacing = CameraSelector.LENS_FACING_BACK;
    private final int frontFacing = CameraSelector.LENS_FACING_FRONT;
    boolean flashState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        previewView = findViewById(R.id.previewView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        changeButton = findViewById(R.id.changeButton);
        flashButton = findViewById(R.id.flashButton);

        try {
            processCameraProvider = ProcessCameraProvider.getInstance(this).get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(CameraViewActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            bindPreview();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CameraViewActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    bindPreview();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCameraProvider.unbindAll();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 카메라 전면 후면 전환 구현 필요
            }
        });

        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    if(flashState) {
                        flashState = false;
                        controlFlash(flashState);
                    } else {
                        flashState = true;
                        controlFlash(flashState);
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    bindPreview();
                }
                else {
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void bindPreview() {
        previewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(backFacing)
                .build();
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3) //디폴트 표준 비율
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        processCameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }

    public void controlFlash(boolean state) {
        // android.hardware.camera2.CameraAccessException: CAMERA_IN_USE (4): setTorchMode:2504: Torch for camera "0" is not available due to an existing camera user
        // 수정 해야 할듯
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, state);
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Camera Flash Error", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        processCameraProvider.unbindAll();
    }
}
