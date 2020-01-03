package com.demos.yxn.lifecircle.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义手写签名页面
 * 画布背景为白色，画笔为黑色
 * https://www.jianshu.com/p/c084c5928a9f
 */
public class LinerView extends View {

    private Context mContext;

    //画笔及画布属性
    private int paintColor;
    private int canvasColor;
    private int paintSize;

    //起点横纵坐标
    private int mX;
    private int mY;

    //画笔
    private final Paint mPaint = new Paint();

    //路径
    private Path mPath;

    //画布
    private Canvas mCanvas;

    //生成位图
    private Bitmap mBitmap;

    //是否已经签名
    private boolean isDrawed;

    //签名开始与结束
    private Touch touch;

    public LinerView(Context context) {
        super(context);
        init(context);
    }

    public LinerView(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        mPath = new Path();

        //抗锯齿
        mPaint.setAntiAlias(true);
        //画笔样式，实线
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintSize);
        mPaint.setColor(paintColor);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 按下时执行
     */
    private void touchDown(MotionEvent event) {

    }

    /**
     * 移动时执行
     */
    private void touchMove(MotionEvent event) {

    }

    /**
     * 清除画板
     */
    public void clear() {

    }

    public interface TouchListener {
        void onTouch(boolean isTouch);
    }

    public void setTouch(Touch touch) {
        this.touch = touch;
    }

    public void save(String path) {
        save(path, false, 0);
    }

    public void save(String path, boolean clearBlank, int blank) {

    }

    public Bitmap getBitmap() {
        return null;
    }

    private Bitmap clearBlank(Bitmap bp, int blank) {
        return null;
    }

    public void setPaintWidth(int mPaintWidth) {

    }

    public void setCanvasColor(int color) {
        canvasColor = color;
    }

    public void setPaintColor(int color) {
        paintColor = color;
        mPaint.setColor(paintColor);
    }

    public boolean getDrawed() {
        return isDrawed;
    }
}
