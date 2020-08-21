package com.demos.kotlin.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.views.RangeFilterBar;

public class SeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        RangeFilterBar bar = findViewById(R.id.range_filter_bar);
        bar.setOnRangeChangedListener(new RangeFilterBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(int left, int right) {
                Log.e("range", "left:" + left + " right:" + right);
            }
        });
        bar.setRange(20, 500);

    }
}
