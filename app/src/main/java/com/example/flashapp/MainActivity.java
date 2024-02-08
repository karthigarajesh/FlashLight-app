package com.example.flashapp;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    ToggleButton button;
    CameraManager cameraManager;
    String cameraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            if (cameraIds.length > 0) {
                cameraID = cameraIds[0];
            } else {
                showToast("No camera available");
                button.setEnabled(false);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            showToast("Failed to access camera: " + e.getMessage());
            button.setEnabled(false);
        }

        button.setOnClickListener(v -> {
            if (button.isChecked()) {
                try {
                    cameraManager.setTorchMode(cameraID, true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    showToast("Failed to turn on flashlight");
                }
                showToast("Flashlight is turned ON");
            } else {
                try {
                    cameraManager.setTorchMode(cameraID, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    showToast("Failed to turn off flashlight");
                }
                showToast("Flashlight is turned OFF");
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

