package com.wisdom.cww.domain;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.wisdom.cww.activities.MainActivity.JSON;

/**
 * Created by Mr.Wang on 2017/12/3.
 */

public class SendQuestion {
    private OkHttpClient client = new OkHttpClient();
    Question_send question_send=new Question_send();

    private ArrayList<Question> questionArrayList = new ArrayList<Question>();
    public void setTypeId(String typeId){this.question_send.typeId=typeId;}
    public void setQuestion(String question){this.question_send.question=question;}
    public String getTypeId(){return question_send.typeId;}
    public String getQuestion(){return question_send.question;}

    public SendQuestion(String question)
    {
        setQuestion(question);
    }

    public  void sendQuestion(String url,Callback back){
        //向服务器发送用户问题
        Gson gson=new Gson();
        String json=gson.toJson(question_send);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }


    class Question_send
    {
        String typeId="0";
        String question;
    }
}
