package com.wisdom.cww.domain;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.wisdom.cww.activities.MainActivity.JSON;

/**
 * Created by Mr.Wang on 2017/12/3.
 */
public class SendCommentIds {

    class CommmentId
    {
        String commentIds;
        String typeId="2";
    }

    private OkHttpClient client = new OkHttpClient();
    CommmentId commentId=new CommmentId();

    public void setCommentIds(String  commentIds)
    {
        this.commentId.commentIds=commentIds;
    }
    public void setTypeId(String typeId)
    {
        this.commentId.typeId=typeId;
    }
    public String getCommentIds()
    {
        return commentId.commentIds;
    }
    public String getTypeId()
    {
        return commentId.typeId;
    }

    private void sendCommentIds(String url, Callback back){
        //向服务器发送用户问题
        Gson gson=new Gson();
        String json=gson.toJson(commentId);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }

}
