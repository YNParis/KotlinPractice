package com.demos.kotlin.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.Const2;
import com.demos.kotlin.R;
import com.demos.kotlin.algorithm.BinarySearch;

public class JavaActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_run).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
            }
        });
        result = findViewById(R.id.tv_res);
    }

    private void execute() {
        Log.e(Const2.lOG_TAG, "[1, 2, 3, 4, 5, 6, 7, 8]，期望1->结果：" + BinarySearch.findMin(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
        Log.e(Const2.lOG_TAG, "[6, 7, 8, 1, 2, 3, 4, 5]，期望1->结果：" + BinarySearch.findMin(new int[]{6, 7, 8, 1, 2, 3, 4, 5}));
        Log.e(Const2.lOG_TAG, "[3, 4, 5, 6, 7, 8, 1, 2]，期望1->结果：" + BinarySearch.findMin(new int[]{3, 4, 5, 6, 7, 8, 1, 2}));
        Log.e(Const2.lOG_TAG, "[6, 5]，期望5->结果：" + BinarySearch.findMin(new int[]{6, 5}));
        Log.e(Const2.lOG_TAG, "[1, 2]，期望1->结果：" + BinarySearch.findMin(new int[]{1, 2}));
        Log.e(Const2.lOG_TAG, "[5]，期望5->结果：" + BinarySearch.findMin(new int[]{5}));
    }

}