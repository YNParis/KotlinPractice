package com.demos.kotlin.kotlinsyntax;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.Const2;
import com.demos.kotlin.Constants;
import com.demos.kotlin.R;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        Log.e(Constants.lOG_TAG, "JavaActivity 打印");
        Log.e(Const2.lOG_TAG, "JavaActivity 打印");
    }

}