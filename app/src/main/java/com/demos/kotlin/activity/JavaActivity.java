package com.demos.kotlin.activity;

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
//        Log.e(Const2.lOG_TAG, "countAndSay(1)->结果：" + Strings.countAndSay(1));
//        Log.e(Const2.lOG_TAG, "countAndSay(2)->结果：" + Strings.countAndSay(2));
//        Log.e(Const2.lOG_TAG, "countAndSay(3)->结果：" + Strings.countAndSay(3));
        Log.e(Const2.lOG_TAG, "countAndSay(4)->结果：" + Strings.countAndSay(4));
        Log.e(Const2.lOG_TAG, "countAndSay(5)->结果：" + Strings.countAndSay(5));
        Log.e(Const2.lOG_TAG, "countAndSay(6)->结果：" + Strings.countAndSay(6));
        Log.e(Const2.lOG_TAG, "countAndSay(7)->结果：" + Strings.countAndSay(7));
        Log.e(Const2.lOG_TAG, "countAndSay(8)->结果：" + Strings.countAndSay(8));
        Log.e(Const2.lOG_TAG, "countAndSay(9)->结果：" + Strings.countAndSay(9));
        Log.e(Const2.lOG_TAG, "countAndSay(10)->结果：" + Strings.countAndSay(10));
        Log.e(Const2.lOG_TAG, "countAndSay(11)->结果：" + Strings.countAndSay(11));
    }

}