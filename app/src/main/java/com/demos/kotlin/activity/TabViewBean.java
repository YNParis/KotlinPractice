package com.demos.kotlin.activity;

import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

public class TabViewBean {
    private View view;
    private String title;
    private List<String> subTitles;

    public TabViewBean(View view, String title, @Nullable List<String> subTitles) {
        this.view = view;
        this.title = title;
        this.subTitles = subTitles;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSubTitles() {
        return subTitles;
    }

    public void setSubTitles(List<String> subTitles) {
        this.subTitles = subTitles;
    }
}
