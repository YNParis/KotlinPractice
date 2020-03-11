package com.demos.kotlin;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.utils.WaterMarkUtils;

public class WaterMarkActivity extends AppCompatActivity {
    LinearLayout father;
    LinearLayout child1;
    LinearLayout child2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_mark);
        initView();
    }

    private void initView() {
        father = findViewById(R.id.layout_water_mark_father);
        child1 = findViewById(R.id.layout_water_mark_child_1);
        child2 = findViewById(R.id.layout_water_mark_child_2);

        WaterMarkUtils.getInstance().setRotation(45).setText("水印1").setTextColor(getResources().getColor(R.color.colorBlue)).setTextSize(15).show(this, child1);
        WaterMarkUtils.getInstance().setRotation(135).setText("水印2").setTextColor(getResources().getColor(R.color.colorRed)).setTextSize(20).show(this, child2);
        WaterMarkUtils.getInstance().setRotation(90).setText("水印3").setTextColor(getResources().getColor(R.color.colorGreen)).setTextSize(15).show(this, father);
    }
}
