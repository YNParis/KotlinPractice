package com.demos.kotlin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.demos.kotlin.picture.CompressHelper;
import com.demos.kotlin.picture.FileProvider7;
import com.demos.kotlin.picture.GetFileUtil;
import com.demos.kotlin.picture.GlideUtils;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 拍照或着从相册选取，然后裁剪，上传，显示到屏幕的过程
 * 必须的文件：
 * provider
 */
public class PictureActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog dialog;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private File photoFile;
    private Uri desUri;
    private File finalFile;
    private File originalFile;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        imageView = findViewById(R.id.img_pic_result);
        findViewById(R.id.btn_get_pic).setOnClickListener(this);
    }

    @Override public void onClick(@Nullable View v) {
        switch (v.getId()) {
            case R.id.btn_get_pic:
                chooseIcon();
                break;
            case R.id.choose_from_album:
                dialog.dismiss();
                chooseFromAlbum();
                break;
            case R.id.take_photo:
                dialog.dismiss();
                takePic();
                break;
            case R.id.cancel_dialog:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * 编辑头像
     */
    private void chooseIcon() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_pic, null);
        dialog = new Dialog(this, R.style.BottomDialog);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.findViewById(R.id.choose_from_album).setOnClickListener(this);
        contentView.findViewById(R.id.take_photo).setOnClickListener(this);
        contentView.findViewById(R.id.cancel_dialog).setOnClickListener(this);
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.show();
    }

    /**
     * 从相册中选取
     */
    private void chooseFromAlbum() {
        if (requestPermission(CODE_GALLERY_REQUEST)) {
            //已授权，获取照片
            choosePhoto();
        }
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CODE_GALLERY_REQUEST);
    }

    /**
     * 拍摄照片
     */
    private void takePic() {
        if (requestPermission(CODE_CAMERA_REQUEST)) {
            //有权限直接调用系统相机拍照
            if (hasSdcard()) {
                takePhoto();
            } else {
                Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private void takePhoto() {
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = new File(getExternalCacheDir() + "/photo.jpeg");
        if (photoFile.exists()) {
            photoFile.delete();
        }
        Uri imageUri = FileProvider7.getUriForFile(this, photoFile);
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, CODE_CAMERA_REQUEST);
    }

    private void crop() {
        if (requestPermission(CODE_RESULT_REQUEST)) {
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
            startActivityForResult(cropIntent, CODE_RESULT_REQUEST);
        }
    }

    /**
     * 上传用户头像
     */
    private void upLoadUserIcon(File imgFile) {

        //将图片放入file文件中并压缩图片
        File file = CompressHelper.getDefault(this).compressToFile(imgFile);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part =
            MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        //TODO 上传图片
        //imageView.setImageURI(desUri);
        GlideUtils.setDefaultPic(getApplicationContext(), file.getAbsolutePath(), imageView);
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
                takePic();
                break;
            case CODE_GALLERY_REQUEST:   //相册选择照片权限申请返回
                chooseFromAlbum();
                break;
            case CODE_RESULT_REQUEST:
                crop();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    originalFile = photoFile;
                    crop();
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    Uri uri = data.getData();
                    String filePath = GetFileUtil.getFilePathByUri(this, uri);
                    if (filePath != null) {
                        originalFile = new File(filePath);
                        crop();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    finalFile = new File(desUri.getPath());
                    upLoadUserIcon(finalFile);
                    break;
                default:
                    break;
            }
        }
    }
}
