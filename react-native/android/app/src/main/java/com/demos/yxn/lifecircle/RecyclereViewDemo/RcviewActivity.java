package com.demos.yxn.lifecircle.RecyclereViewDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.demos.yxn.lifecircle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YXN on 2018/7/13.
 */

public class RcviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcview);
        recyclerView = findViewById(R.id.rcview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        for (int i = 1; i <= 20; i++) {
            list.add("第" + i + "条数据");
        }
        recyclerView.setAdapter(new MyRcviewAdapter(this, list));
    }
}
