package com.demos.kotlin.picture;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.demos.kotlin.R;

/**
 * @Descrpiton:Glide加载图片
 * @TYPE1:setDefaultPic_默认显示
 * @TYPE2:setCircleImage_圆形显示
 * @TYPE3:setCenterCropImage_居中显示有裁剪
 */
public class GlideUtils {
    /**
     * 默认显示
     */
    public static void setDefaultPic(Context context, String url, ImageView imageView) {
        setDefaultPic(context, url, imageView, 0);
    }

    public static void setDefaultPic(Context context, String url, ImageView imageView,
        int errorRes) {
        if (errorRes == 0) {
            errorRes = R.drawable.ic_error;
        }
        if (url == null || ("").equals(url)) {
            imageView.setImageResource(errorRes);
        } else {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.errorOf(errorRes))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imageView);
        }
    }

    /**
     * 圆形显示
     */
    public static void setCircleImage(Context context, String url, ImageView imageview) {
        setCircleImage(context, url, imageview, 0);
    }

    public static void setCircleImage(Context context, String url, ImageView imageView,
        int errorRes) {
        //若传了默认失败图像，则用默认的，不然用R.drawable.ic_error
        if (errorRes == 0) {
            errorRes = R.drawable.ic_error;
        }
        if (url == null || ("").equals(url)) {
            imageView.setImageResource(errorRes);
        } else {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.errorOf(errorRes))
                .into(imageView);
        }
    }

    /**
     * CenterCrop显示
     */
    public static void setCenterCropImage(Context context, String url, ImageView imageView) {
        setCenterCropImage(context, url, imageView, 0);
    }

    public static void setCenterCropImage(Context context, String url, ImageView imageView,
        int errorRes) {
        if (url == null || ("").equals(url)) {
            if (errorRes == 0) {
                imageView.setImageResource(R.drawable.ic_error);
            } else {
                imageView.setImageResource(errorRes);
            }
        } else {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.errorOf(R.drawable.ic_error))
                .into(imageView);
        }
    }

    /**
     * fitCenter显示
     */
    public static void setFitCenterImage(Context context, String url, ImageView imageView) {
        setFitCenterImage(context, url, imageView, 0);
    }

    public static void setFitCenterImage(Context context, String url, ImageView imageView,
        int errorRes) {
        if (url == null || ("").equals(url)) {
            if (errorRes == 0) {
                imageView.setImageResource(R.drawable.ic_error);
            } else {
                imageView.setImageResource(errorRes);
            }
        } else {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.errorOf(R.drawable.ic_error))
                .into(imageView);
        }
    }

    /**
     * 圆角显示
     */
    public static void setCornerImage(Context context, String url, ImageView imageView,
        int corner) {
        setCornerImage(context, url, imageView, 0, corner);
    }

    public static void setCornerImage(Context context, String url, ImageView imageView,
        int errorRes, int corner) {
        if (url == null || ("").equals(url)) {
            if (errorRes == 0) {
                imageView.setImageResource(R.drawable.ic_error);
            } else {
                imageView.setImageResource(errorRes);
            }
        } else {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(corner)))
                .apply(RequestOptions.errorOf(R.drawable.ic_error))
                .into(imageView);
        }
    }
}

