package com.demos.kotlin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_login_by_fingerprint).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_by_fingerprint:
                openDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 打开对话框，指纹识别的提示信息等
     */
    private void openDialog() {

    }


}
