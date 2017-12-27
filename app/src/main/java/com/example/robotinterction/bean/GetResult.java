package com.example.robotinterction.bean;

/**
 * Created by 金文韬 on 2017/12/4.
 */

/**
 * 对服务器返回的结果匹配的baan
 */
public class GetResult {
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
