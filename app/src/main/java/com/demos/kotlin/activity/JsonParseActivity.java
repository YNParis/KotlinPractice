package com.demos.kotlin.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.bean.FishboneRecordListBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonParseActivity extends AppCompatActivity {

    private String string;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        loadFile();
        parseJson(string);
    }

    private void parseJson(String string) {

        Gson gson = new Gson();
        try {
            FishboneRecordListBean fishboneRecordListBean = gson.fromJson(string, FishboneRecordListBean.class);
            Log.e("json", fishboneRecordListBean.toString());
            ((TextView) findViewById(R.id.tv_json)).setText(fishboneRecordListBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("json/json_example.txt")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
                Log.d("AAA", line);
            }
            string = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
