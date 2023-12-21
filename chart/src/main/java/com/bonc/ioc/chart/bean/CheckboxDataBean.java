package com.bonc.ioc.chart.bean;

/**
 * 图表上方，多选列表的数据
 */
public class CheckboxDataBean {
    private String text;
    private String id;
    private int color;//多选框的颜色
    private boolean isSelected;

    public CheckboxDataBean(String text, String id, int color, boolean isSelected) {
        this.text = text;
        this.id = id;
        this.color = color;
        this.isSelected = isSelected;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
