package com.example.cookie;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CameraViewActivity extends AppCompatActivity {
    private final int REQUEST_IMAGE = 101;
    private PreviewView previewView;
    private Button captureButton, changeButton, flashButton;
    private Button failed, loading;     // 추후 삭제
    private ProcessCameraProvider processCameraProvider;
    private ImageCapture imageCapture;
    private ImageView tempoView;
    private final int backFacing = CameraSelector.LENS_FACING_BACK;
    private final int frontFacing = CameraSelector.LENS_FACING_FRONT;
    private boolean flashState = false;
    private Bitmap capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        previewView = findViewById(R.id.previewView);
        captureButton = findViewById(R.id.captureButton);
        changeButton = findViewById(R.id.changeButton);
        flashButton = findViewById(R.id.flashButton);
        tempoView = findViewById(R.id.tempoView);

        // 추후 삭제
        loading = findViewById(R.id.loading);
        failed = findViewById(R.id.failed);

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
            bindImageCapture();
        }

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CameraViewActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    captureImage();
                    /*
                    Intent intent = new Intent(ImageViewActivity.this, ImageViewActivity.class);
                    startActivity(intent);
                    */

                }
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
                // TODO: 카메라 플래시 구현 필요
                /*
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    if(flashState) {
                        flashState = false;
                        controlFlash(flashState);
                    } else {
                        flashState = true;
                        controlFlash(flashState);
                    }
                }
                */
            }
        });

        // 추후 삭제
        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraViewActivity.this, FailedActivity.class);
                startActivity(intent);
            }
        });

        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraViewActivity.this, LoadingActivity.class);
                startActivity(intent);
            }
        });


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

    void bindImageCapture() {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(backFacing)
                .build();
        imageCapture = new ImageCapture.Builder()
                .build();

        processCameraProvider.bindToLifecycle(this, cameraSelector, imageCapture);
    }

    private void captureImage() {
        imageCapture.takePicture(ContextCompat.getMainExecutor(CameraViewActivity.this),
                new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image) {
                        super.onCaptureSuccess(image);

                        Bitmap capturedBitmap = imageProxyToBitmap(image);
                        image.close();
                        tempoView.setImageBitmap(rotateImage(capturedBitmap, 90));
                                /*
                                Intent previewIntent = new Intent(MainActivity.this, ImageViewActivity.class);
                                previewIntent.putExtra("capturedImage", capturedBitmap);
                                startActivity(previewIntent);

                                 */
                    }

                }
        );
    }

    private Bitmap imageProxyToBitmap(ImageProxy imageProxy) {
        ByteBuffer buffer = imageProxy.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),src.getHeight(), matrix, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        processCameraProvider.unbindAll();
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
    public void controlFacing() {}
}

