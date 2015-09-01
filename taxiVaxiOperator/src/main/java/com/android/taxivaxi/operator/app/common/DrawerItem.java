package com.android.taxivaxi.operator.app.common;

/**
 * Created by MB on 9/1/2015.
 */
public class DrawerItem {
    private int imageId;
    private String title;
    private String desc;

    public DrawerItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;

    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
