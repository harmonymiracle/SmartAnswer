package com.wisdom.cww.domain;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.wisdom.cww.activities.MainActivity.JSON;

/**
 * Created by Mr.Wang on 2017/12/11.
 */

public class Request {
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    String typeId;

    public Request (){
        typeId = "12";
    }
    public Request(String typeId)
    {
        setTypeId(typeId);
    }

    public void send(String url, Callback back)
    {
        OkHttpClient client = new OkHttpClient();
        Request request=new Request(typeId);

        Gson gson=new Gson();
        String json=gson.toJson(request);
        RequestBody requestBody = RequestBody.create(JSON, json);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }

    public void send(String typeId,String url, Callback back)
    {
        OkHttpClient client = new OkHttpClient();
        Request request=new Request(typeId);

        Gson gson=new Gson();
        String json=gson.toJson(typeId);
        RequestBody requestBody = RequestBody.create(JSON, json);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }




}
