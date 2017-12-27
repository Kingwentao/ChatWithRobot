package com.example.robotinterction.bean;

import java.util.Date;

/**
 * Created by 金文韬 on 2017/12/4.
 */

/**
 * 封装一个聊天信息类
 */
public class ChatMessage {
    private String name;
    private String msg;
    private Date date;
    private Type type;

    public ChatMessage(){

    }
    public ChatMessage(String msg, Date date, Type type) {
        this.msg = msg;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date data) {
        this.date= data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 消息的两种类型，接受和发送两种
     */
    public enum Type{
        OutComing,InComing;
    }
 }
