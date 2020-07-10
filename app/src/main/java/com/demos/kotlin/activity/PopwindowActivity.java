package com.demos.kotlin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.views.RelativePopWindow;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;

public class PopwindowActivity extends AppCompatActivity {


    RelativePopWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow);
        popWindow = new RelativePopWindow(this);
    }

    public void popTopLeft(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.LEFT);
    }

    public void popTopCenter(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER);
    }

    public void popTopRight(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.RIGHT);
    }

    public void popBottomLeft(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.BELOW, RelativePopupWindow.HorizontalPosition.LEFT);
    }

    public void popBottomCenter(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.BELOW, RelativePopupWindow.HorizontalPosition.CENTER);
    }

    public void popBottomRight(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.BELOW, RelativePopupWindow.HorizontalPosition.RIGHT);
    }

    public void popLeftTop(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.LEFT);
    }

    public void popLeftCenter(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.CENTER, RelativePopupWindow.HorizontalPosition.LEFT);
    }

    public void popLeftBottom(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.BELOW, RelativePopupWindow.HorizontalPosition.LEFT);
    }

    public void popRightTop(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.RIGHT);
    }

    public void popRightCenter(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.CENTER, RelativePopupWindow.HorizontalPosition.RIGHT);
    }

    public void popRightBottom(View view) {
        popWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.BELOW, RelativePopupWindow.HorizontalPosition.RIGHT);
    }
}
