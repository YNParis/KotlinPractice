package com.bonc.ioc.chart.bean;

import java.io.Serializable;

/**
 *
 */
public class CommonRadioBean implements Serializable {

    private String text;
    private String id;

    public CommonRadioBean(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
