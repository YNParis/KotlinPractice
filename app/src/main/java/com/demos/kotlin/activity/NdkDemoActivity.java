package com.demos.kotlin.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;

/**
 * Created by YXN on 2018/9/7.
 */

public class NdkDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "NdkDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);
        findViewById(R.id.button_touch).setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        if (v.getId() != R.id.button_touch) return;
        Log.e(TAG, TAG + " button_touch is onClicked");
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, TAG + " onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, TAG + " onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, TAG + " onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, TAG + " onTouchEvent ACTION_CANCEL");
                break;
            default:
                break;
        }
        Log.e(TAG, "onTouchEvent " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, TAG + " dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, TAG + " dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, TAG + " dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, TAG + " dispatchTouchEvent ACTION_CANCEL");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
