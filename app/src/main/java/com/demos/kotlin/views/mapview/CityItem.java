package com.demos.kotlin.views.mapview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;

/**
 * @auther sxshi on 2017/6/1.
 * @email emotiona_xiaoshi@126.com
 */

public class CityItem {
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
            paint.setShadowLayer(8, 10, 10, selectedFillColor);
            canvas.drawPath(mPath, paint);
            paint.clearShadowLayer();
            paint.setColor(selectedStrokeColor);
            paint.setStrokeWidth(4);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, paint);
        } else {
            paint.clearShadowLayer();
            paint.setColor(normalFillColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mPath, paint);
            paint.setColor(normalStrokeColor);
            paint.setStrokeWidth(2);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, paint);
        }
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
