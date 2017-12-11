package com.wisdom.cww.domain;

/**
 * Created by Mr.Wang on 2017/12/10.
 */
import com.google.gson.Gson;

import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.wisdom.cww.activities.MainActivity.JSON;

/**
 * Created by cheng on 2017/12/5.
 */

public class SignUp implements Serializable{
    private OkHttpClient client = new OkHttpClient();
    private signup s = new signup();
    public void setTypeId(String typeId) { s.typeId=typeId; }
    public void setUsername(String username){ s.username=username; }
    public void setPassword(String password){ s.password=password; }
    public String getTypeId(){return s.typeId; }
    public String getUsername(){ return s.username; }
    public String getPassword() { return s.password; }


    public  void sendusername(String url,Callback back){
        //向服务器发送用户问题
        setTypeId("7");
        send(url,back);
    }
    public  void Login(String url,Callback back){
        //向服务器发送用户问题
        setTypeId("8");
        send(url,back);
    }
    public  void signup(String url,Callback back){
        //向服务器发送用户问题
        setTypeId("9");
        send(url,back);
    }
    private void  send(String url,Callback back)
    {
        Gson gson=new Gson();
        String json=gson.toJson(s);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }



    private class signup
    {
        String typeId;
        String username;
        String password;
    }
}
