package com.demos.kotlin.utils.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-03-08  9:03
 */

public class BitmapUtil {

    private static String TAG = "BitmapUtil";

    private BitmapUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    static Bitmap getScaledBitmap(Context context, Uri imageUri, float maxWidth, float maxHeight, Bitmap.Config bitmapConfig) {
        String filePath = FileUtil.getRealPathFromURI(context, imageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();

        //by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        //you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        if (bmp == null) {
            InputStream inputStream;
            try {
                inputStream = new FileInputStream(filePath);
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, exception.getMessage());

            }
        }

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if (actualHeight == -1 || actualWidth == -1) {
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                actualHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
                actualWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        if (actualWidth <= 0 || actualHeight <= 0) {
            Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);
            if (bitmap2 != null) {
                actualWidth = bitmap2.getWidth();
                actualHeight = bitmap2.getHeight();
            } else {
                return null;
            }
        }

        float imgRatio = (float) actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        //width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        //this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            // load the bitmap getTempFile its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {
                InputStream inputStream = new FileInputStream(filePath);
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            }
        } catch (OutOfMemoryError | IOException exception) {
            Log.e(TAG, exception.getMessage());
        }
        if (actualHeight <= 0 || actualWidth <= 0) {
            return null;
        }
        // 采用 ExitInterface 设置图片旋转方向
        ExifInterface exif;
        Bitmap scaledBitmap;
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, bitmapConfig);
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, 0, 0);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            return Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError | IOException exception) {
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }

    static File compressImage(Context context, Uri imageUri, float maxWidth, float maxHeight,
                              Bitmap.CompressFormat compressFormat, Bitmap.Config bitmapConfig,
                              int quality, String parentPath, String prefix, String fileName) {
        FileOutputStream out = null;
        String filename = generateFilePath(context, parentPath, imageUri, compressFormat.name().toLowerCase(), prefix, fileName);
        try {
            out = new FileOutputStream(filename);
            // 通过文件名写入
            Bitmap newBmp = BitmapUtil.getScaledBitmap(context, imageUri, maxWidth, maxHeight, bitmapConfig);
            if (newBmp != null) {
                newBmp.compress(compressFormat, quality, out);
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

        }

        return new File(filename);
    }

    private static String generateFilePath(Context context, String parentPath, Uri uri,
                                           String extension, String prefix, String fileName) {
        File file = new File(parentPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        /** if prefix is null, set prefix "" */
        /** reset fileName by prefix and custom file name */
        String name = TextUtils.isEmpty(fileName) ? (TextUtils.isEmpty(prefix) ? "" : prefix) + FileUtil.splitFileName(FileUtil.getFileName(context, uri))[0] : fileName;
        return file.getAbsolutePath() + File.separator + name + "." + extension;
    }


    /**
     * 计算inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final float height = options.outHeight;
        final float width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round(height / (float) reqHeight);
            final int widthRatio = Math.round(width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final int totalPixels = (int) (width * height);
        final int totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * 水平镜像翻转
     */
    public static Bitmap mirror(Bitmap rawBitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1f, 1f);
        return Bitmap.createBitmap(rawBitmap, 0, 0, rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);
    }

    /**
     * 旋转
     */
    public static Bitmap rotate(Bitmap rawBitmap, Float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(rawBitmap, 0, 0, rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);
    }

}
