package com.demos.yxn.lifecircle.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.app808.HelloNdk;
import com.demos.yxn.lifecircle.R;

/**
 * Created by YXN on 2018/9/7.
 */

public class NdkDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String string = HelloNdk.getLen(100.01, 45.01) + "";
        ((Button) findViewById(R.id.button)).setText(string);
    }


}