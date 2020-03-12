package com.demos.kotlin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.demos.kotlin.picture.FileProvider7;
import com.demos.kotlin.picture.GetFileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 自定义拍照页面的功能。
 * 实现：1.预览、拍照；2.相册选择；3.裁剪；4.返回等其他功能。
 */
public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_CROP_REQUEST = 0xa2;

    private int facing = Camera.CameraInfo.CAMERA_FACING_BACK;

    private Uri desUri;
    private File finalFile;
    private File originalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();

    }

    private void initView() {
        surfaceView = findViewById(R.id.surface_view_preview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        findViewById(R.id.btn_open_gallery).setOnClickListener(this);
        findViewById(R.id.btn_capture).setOnClickListener(this);
        findViewById(R.id.btn_back_camera).setOnClickListener(this);
        findViewById(R.id.btn_info).setOnClickListener(this);
        findViewById(R.id.btn_switch_camera).setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null) {
            openCamera();
        }
        startPreview(surfaceHolder);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    public void capture() {
        //TODO 保存图片 设置分辨率
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setPictureFormat(ImageFormat.JPEG);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        parameters.setPictureSize(720, 1080);
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            savePicture(data);
                        }
                    });
                }
            }
        });
    }

    private void savePicture(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
        //TODO 存在镜像问题
        matrix.setRotate(facing == Camera.CameraInfo.CAMERA_FACING_BACK ? 90 : 270);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        saveToFile(bitmap);
    }

    private void saveToFile(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                originalFile = new File(getExternalCacheDir() + "/photo.jpeg");
                if (!originalFile.exists()) {
                    originalFile.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(originalFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                crop();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void crop() {
        if (requestPermission(CODE_CROP_REQUEST)) {
            desUri = Uri.fromFile(new File(
                    getExternalCacheDir() + "/crop.jpg"));
            Uri originUri = FileProvider7.getUriForFile(this,
                    new File(originalFile.getPath()));
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            cropIntent.setDataAndType(originUri, "image/*");
            cropIntent.putExtra("crop", "true");
            //将剪切的图片保存到目标Uri中
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 480);
            cropIntent.putExtra("outputY", 480);
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", false);
            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
            startActivityForResult(cropIntent, CODE_CROP_REQUEST);
        }
    }

    public void openGallery() {
        if (requestPermission(CODE_GALLERY_REQUEST)) {
            //TODO 打开相册
            choosePhoto();
        }
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CODE_GALLERY_REQUEST);
    }

    /**
     * 获取系统的camera对象
     *
     * @return
     */
    private boolean openCamera() {

        if (requestPermission(CODE_CAMERA_REQUEST)) {
            //已授权，获取照片
            boolean supportCameraFacing = supportCameraFacing(facing);
            if (supportCameraFacing) {
                try {
                    camera = Camera.open(facing);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 是否支持某个摄像头
     *
     * @param cameraFacing 0，前置；1，后置
     * @return
     */
    private boolean supportCameraFacing(int cameraFacing) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == cameraFacing) return true;
        }
        return false;
    }

    /**
     * 开始预览相机内容
     */
    private void startPreview(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();

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
        if (camera != null) startPreview(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera != null) {
            camera.stopPreview();
            startPreview(holder);
        }
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
                openCamera();
                break;
            case CODE_GALLERY_REQUEST:   //相册选择照片权限申请返回
                openGallery();
                break;
            case CODE_CROP_REQUEST:
                crop();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    Uri uri = data.getData();
                    String filePath = GetFileUtil.getFilePathByUri(this, uri);
                    if (filePath != null) {
                        originalFile = new File(filePath);
                        crop();
                    }
                    break;
                case CODE_CROP_REQUEST:
                    finalFile = new File(desUri.getPath());
                    searchWithFile(finalFile);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 通过图片搜索
     *
     * @param finalFile
     */
    private void searchWithFile(File finalFile) {
        //TODO 修改跳转到查找页面
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("file", finalFile.getAbsolutePath());
        startActivity(intent);
        finish();
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
            case R.id.btn_back_camera:
                finish();
                break;
            case R.id.btn_info:
                showInfo();
                break;
            case R.id.btn_switch_camera:
                switchCamera();
                break;
            default:
                break;
        }
    }

    /**
     * 提示信息
     */
    private void showInfo() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_info, null);
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.findViewById(R.id.btn_close_info_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.show();
    }

    /**
     * 转换摄像头
     */
    private void switchCamera() {
        releaseCamera();
        facing = facing == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        if (openCamera()) startPreview(surfaceHolder);

    }

}
