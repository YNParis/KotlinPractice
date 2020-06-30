package com.demos.kotlin.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.views.mapview.CityItem;
import com.demos.kotlin.views.mapview.CityMapView;

public class CustomMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_map);
        CityMapView detailMapView = (CityMapView) findViewById(R.id.svg_map);
        detailMapView.setOnMapClickListener(new CityMapView.OnMapClickListener() {
            @Override
            public void onClick(CityItem cityItem) {
                Toast.makeText(CustomMapActivity.this, cityItem.getCityName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
