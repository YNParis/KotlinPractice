package com.demos.kotlin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

/**
 * 实现自定义拍照页面的功能。
 * 实现：1.预览、拍照；2.相册选择；3.裁剪；4.返回等其他功能。
 */
public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private Camera camera;
    private SurfaceHolder surfaceHolder;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_CROP_REQUEST = 0xa2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        SurfaceView surfaceView = findViewById(R.id.surface_view_preview);
        findViewById(R.id.btn_open_gallery).setOnClickListener(this);
        findViewById(R.id.btn_capture).setOnClickListener(this);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null) {
            camera = getCamera();
        }
        if (surfaceHolder != null) {
            startPreview(camera, surfaceHolder);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    public void capture() {
//        if (requestPermission(CODE_CROP_REQUEST)) {
        //TODO 打开相册
        Toast.makeText(this, "拍摄", Toast.LENGTH_SHORT).show();
//        }
    }

    public void openGallery() {
        if (requestPermission(CODE_GALLERY_REQUEST)) {
            //TODO 打开相册
            Toast.makeText(this, "打开相册", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取系统的camera对象
     *
     * @return
     */
    private Camera getCamera() {
        Camera camera = null;
        if (requestPermission(CODE_CAMERA_REQUEST)) {
            //已授权，获取照片
            try {
                camera = Camera.open();
            } catch (Exception e) {
                Log.e("camera", "--------------" + e.getMessage());
                e.printStackTrace();
            }
        }
        return camera;
    }

    /**
     * 开始预览相机内容
     */
    private void startPreview(Camera camera1, SurfaceHolder holder) {
        try {
            camera1.setPreviewDisplay(holder);
            camera1.setDisplayOrientation(90);
            camera1.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Toast.makeText(this, "获取camera为空", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放相机占用资源
     */
    private void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(camera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        camera.stopPreview();
        startPreview(camera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    private boolean requestPermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ActivityCompat.requestPermissions(this,
                            permissions, requestCode);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:   //拍照权限申请返回
                getCamera();
                break;
            case CODE_GALLERY_REQUEST:   //相册选择照片权限申请返回
                openGallery();
                break;
            case CODE_CROP_REQUEST:
//                crop();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_capture:
                capture();
                break;
            case R.id.btn_open_gallery:
                openGallery();
                break;
            default:
                break;
        }
    }
}
