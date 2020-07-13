package com.demos.kotlin.bean;

public class MarkerDataBean {

    private String key;
    private String value;
    private int color;

    public MarkerDataBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public MarkerDataBean(String key, String value, int color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
