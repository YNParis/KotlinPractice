package com.demos.kotlin.kotlinsyntax;

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
        Log.e(Const2.lOG_TAG, "2147483647->结果：" + BinarySearch.mySqrt(2147483647));
        Log.e(Const2.lOG_TAG, "123->结果：" + BinarySearch.mySqrt(123));
        Log.e(Const2.lOG_TAG, "64->结果：" + BinarySearch.mySqrt(64));
        Log.e(Const2.lOG_TAG, "1000000->结果：" + BinarySearch.mySqrt(1000000));
        Log.e(Const2.lOG_TAG, "9->结果：" + BinarySearch.mySqrt(9));
        Log.e(Const2.lOG_TAG, "0->结果：" + BinarySearch.mySqrt(0));
        Log.e(Const2.lOG_TAG, "1->结果：" + BinarySearch.mySqrt(1));
    }

}