package com.demos.kotlin.views.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.text.TextUtils;

import com.demos.kotlin.R;

/**
 * @auther sxshi on 2017/6/1.
 * @email emotiona_xiaoshi@126.com
 */

public class CustomCityItem {
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 城市编码
     */
    private String cityId;
    /**
     * 区域路径
     */
    private Path mPath;
    /**
     * 路径区域
     */
    private Region mRegion;
    /**
     * 区域背景色，默认白色
     */
    private int normalFillColor = Color.TRANSPARENT;
    private int normalStrokeColor = Color.BLUE;
    private int selectedFillColor = Color.WHITE;
    private int selectedStrokeColor = Color.WHITE;

    private Context context;
    private Bitmap normalBitmap;
    private Bitmap selectedBitmap;

    public CustomCityItem(Context context) {
        this.context = context;
        normalBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_capture_bg);
        selectedBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_camera_blue);
    }

    public Path getmPath() {
        return mPath;
    }

    public void setmPath(Path mPath) {
        this.mPath = mPath;
    }

    public Region getmRegion() {
        return mRegion;
    }

    public void setmRegion(Region mRegion) {
        this.mRegion = mRegion;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 绘制出自己
     *
     * @param canvas     画布
     * @param paint      画笔
     * @param isSelected 是否是选中的地图
     */
    public void onDraw(Canvas canvas, Paint paint, boolean isSelected) {
        if (isSelected) {
            paint.setColor(selectedFillColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mPath, paint);
            paint.setColor(selectedStrokeColor);
            paint.setStrokeWidth(4);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, paint);
        } else {
            paint.setColor(normalFillColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mPath, paint);
            paint.setColor(normalStrokeColor);
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, paint);
        }
        drawTitleAndDrawable(isSelected, paint, canvas);
    }

    private void drawTitleAndDrawable(boolean isSelected, Paint paint, Canvas canvas) {
        if (TextUtils.isEmpty(cityName)) {
            return;
        }
        int textSize = isSelected ? 40 : 30;
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        int offsetX = (mRegion.getBounds().left + mRegion.getBounds().right - cityName.length() * textSize) / 2;
        int offsetY = (mRegion.getBounds().top + mRegion.getBounds().bottom) / 2;
        canvas.drawText(cityName, offsetX, offsetY, paint);
        paint.setAntiAlias(true);
        Bitmap bitmap = isSelected ? selectedBitmap : normalBitmap;
        int offsetDX = offsetX + (cityName.length() * textSize - bitmap.getWidth()) / 2;
        int offsetDY = offsetY - bitmap.getHeight() - textSize;
        canvas.drawBitmap(bitmap, offsetDX, offsetDY, paint);
    }

    /**
     * 当前地图是否是在点击区域
     *
     * @return ture 当前点在Path内，fasle不在当前Path区域内
     */
    public boolean isOnTouch(int x, int y) {
        return mRegion.contains(x, y);
    }
}
