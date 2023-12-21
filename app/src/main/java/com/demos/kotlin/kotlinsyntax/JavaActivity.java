package com.demos.kotlin.kotlinsyntax;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.Const2;
import com.demos.kotlin.Constants;
import com.demos.kotlin.R;
import com.demos.kotlin.algorithm.AlgorithmInJava;

public class JavaActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        Log.e(Constants.lOG_TAG, "JavaActivity 打印");
        Log.e(Const2.lOG_TAG, "JavaActivity 打印");
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
        result.setText("[2,7,11,15]，9->结果：" + AlgorithmInJava.twoSum(new int[]{2, 7, 11, 15}, 9));
    }

}