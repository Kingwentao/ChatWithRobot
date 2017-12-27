package com.example.robotinterction.utils;

import android.util.Log;
import android.widget.Toast;

import com.example.robotinterction.bean.ChatMessage;
import com.example.robotinterction.bean.GetResult;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;


/**
 * Created by 金文韬 on 2017/12/3.
 */

/**
 * 网络连接请求工具类
 */
public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "2177b727b162469391c7e07c62dbfdb1";
    private static String result="";   //返回的数据结果

    /*
     * @param msg  用户发送的消息
     * @return 获取网络返回的数据
     */
    public static ChatMessage sendMessage(String msg){
        ChatMessage chatMessage=new ChatMessage();
        String jsonMsg=doGet(msg);
        Gson gson=new Gson();
        try {
            GetResult result = gson.fromJson(jsonMsg, GetResult.class);
            chatMessage.setMsg(result.getText());
        }catch(Exception e){
           //chatMessage.setMsg("服务器异常，请尝试重新连接");
        };
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.InComing);
        return chatMessage;
    }

    /**
     * 获得网络上的Gson数据
     * @param msg
     * @return
     */
    public static String doGet(final String msg) {
                //拿到封装好的url
                String url = setParams(msg);
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                    try {
                        URL urlNet = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
                        conn.setReadTimeout(5000);
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        is = conn.getInputStream();
                        int len = -1;
                        byte[] buf = new byte[128];
                        baos = new ByteArrayOutputStream();
                        while ((len = is.read(buf)) != -1) {
                            baos.write(buf, 0, len);
                        }
                        baos.flush();
                        //得到返回的结果
                        Log.d("syso", "doGet: "+new String(baos.toByteArray()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        HttpUtils.result=new String(baos.toByteArray());
                        Log.d("syso", "doGet: "+result);
                    if (baos != null) {
                        try {
                            baos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        return result;
    }


    /**
     * 封装url
     * @param msg
     * @return
     */
    private static String setParams(String msg) {
        String url = "";
        try {
            url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}