package com.example.robotinterction.bean;

/**
 * Created by 金文韬 on 2017/12/15.
 */

/**
 * 选择头像的bean类
 */
public class Headers {
    private String headerName;
    private int imageId;

    public Headers(String headerName, int imageId) {
        this.headerName = headerName;
        this.imageId = imageId;
    }

    public String getHeaderName() {
        return headerName;

    }


    public int getImageId() {
        return imageId;
    }

}
