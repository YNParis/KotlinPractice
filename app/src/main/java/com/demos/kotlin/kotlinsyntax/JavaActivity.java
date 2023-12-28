package com.demos.kotlin.kotlinsyntax;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.Const2;
import com.demos.kotlin.R;
import com.demos.kotlin.algorithm.BinarySearch;
import com.demos.kotlin.algorithm.Strings;

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
        Log.e(Const2.lOG_TAG, "13123123123，123->结果：" + Strings.strStr("13123123123", "123"));
        Log.e(Const2.lOG_TAG, "13123123123，1234->结果：" + Strings.strStr("13123123123", "1234"));
        Log.e(Const2.lOG_TAG, "131231231234，1234->结果：" + Strings.strStr("131231231234", "1234"));
    }

}