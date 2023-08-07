package com.example.cookie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PermissionActivity extends AppCompatActivity {


    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private static final int REQUEST_STORAGE_PERMISSION = 1002;
    private static final int REQUEST_LOCATION_PERMISSION = 1003;
    private static final int REQUEST_ALL_PERMISSIONS = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Button button1 = findViewById(R.id.permissionButton);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestAllPermissions();
            }
        },1000);	//700밀리 초 동안 딜레이

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PermissionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void requestAllPermissions() {
        String[] permissions = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        };

        boolean allPermissionsGranted = true;

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }

        if (!allPermissionsGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_ALL_PERMISSIONS);
        } else {
            // 이미 모든 권한이 허용되었을 경우, 해당 권한들을 사용하여 작업을 수행하면 됩니다.
        }
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 허용된 경우, 카메라 기능 실행
                    // TODO: 카메라 실행 코드 작성
                } else {
                    // 권한이 거부된 경우
                    // TODO: 권한 거부 시 처리할 코드 작성
                }
                break;
            case REQUEST_STORAGE_PERMISSION:
                // 저장소 권한에 대한 처리도 동일하게 구현합니다.
                break;
            case REQUEST_LOCATION_PERMISSION:
                // 위치 권한에 대한 처리도 동일하게 구현합니다.
                break;
            // 다른 권한들이 추가된 경우에도 이와 같이 처리합니다.
        }
    }
}