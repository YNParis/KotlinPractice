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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.demos.kotlin.utils.picture.BitmapUtil;
import com.demos.kotlin.utils.picture.FileProvider7;
import com.demos.kotlin.utils.picture.GetFileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
    private Camera.Parameters mParameters;


    private int picWidth = 1080;     //保存图片的宽，设置太大，保存时间会太长
    private int picHeight = 1920;      //保存图片的高

    private final String tag = "camera";


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

    /**
     * 调用摄像头的拍照
     */
    public void capture() {
        Log.e(tag, "点击了拍照");
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.e(tag, "onPictureTaken");
                savePicture(data);
            }
        });
    }

    private void savePicture(byte[] data) {
        Log.e(tag, "savePicture");
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
        matrix.setRotate(facing == Camera.CameraInfo.CAMERA_FACING_BACK ? 90 : 270);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            bitmap = BitmapUtil.mirror(bitmap);
        }
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
                    initParameters(camera);
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
        if (camera != null) {
            initParameters(camera);
            startPreview(holder);
        }
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

    //配置相机参数
    private void initParameters(Camera camera) {
        try {
            mParameters = camera.getParameters();
            mParameters.setPreviewFormat(ImageFormat.NV21); //设置预览图片的格式

            //获取与指定宽高相等或最接近的尺寸
            //设置预览尺寸
            Camera.Size bestPreviewSize = getBestSize(surfaceView.getWidth(), surfaceView.getHeight(),
                    mParameters.getSupportedPreviewSizes());
            mParameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
            //设置保存图片尺寸
            Camera.Size bestPicSize = getBestSize(picWidth, picHeight, mParameters.getSupportedPictureSizes());
            mParameters.setPictureSize(bestPicSize.width, bestPicSize.height);
            //对焦模式
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            camera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(tag, "相机初始化失败!");
        }
    }

    /**
     * 获取与指定宽高相等或最接近的尺寸
     */
    private Camera.Size getBestSize(int targetWidth, int targetHeight, List<Camera.Size> sizeList) {
        Camera.Size bestSize = null;
        double targetRatio = (targetHeight + 0.0) / targetWidth;//目标大小的宽高比
        double minDiff = targetRatio;

        for (Camera.Size eachSize : sizeList) {
            double supportedRatio = (eachSize.width + 0.0) / eachSize.height;
            Log.e(tag, "系统支持的尺寸 : " + eachSize.width + " * " + eachSize.height + ",    比例 " + supportedRatio);
        }
        for (Camera.Size eachSize : sizeList) {
            if (eachSize.width == targetHeight && eachSize.height == targetWidth) {
                bestSize = eachSize;
                break;
            }
            double supportedRatio = (eachSize.width + 0.0) / eachSize.height;
            if (Math.abs(supportedRatio - targetRatio) < minDiff) {
                minDiff = Math.abs(supportedRatio - targetRatio);
                bestSize = eachSize;
            }
        }
        Log.e(tag, "目标尺寸 " + targetWidth + "* " + targetHeight + " ，   比例  " + targetRatio);
        Log.e(tag, "最优尺寸 ：" + bestSize.height + " * " + bestSize.width);
        return bestSize;
    }


}
