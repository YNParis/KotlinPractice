package com.demos.kotlin.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.utils.WrapLayoutUtils;
import com.demos.kotlin.views.WrapLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WidgetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets);
        initView();
    }

    private void initView() {
        ((Switch) findViewById(R.id.switch_01)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        List<String> data = new ArrayList<>();
        data.add("周杰伦");
        data.add("潘玮柏");
        data.add("林俊杰");
        data.add("S.H.E");
        WrapLayoutUtils.setColorfulWrapLayout(this, data, (WrapLinearLayout) findViewById(R.id.wrap));
    }
}
