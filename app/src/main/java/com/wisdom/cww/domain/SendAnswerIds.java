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

public class SendAnswerIds {
    private OkHttpClient client = new OkHttpClient();
    Answerid answerid=new Answerid();
    public void setTypeId (String typeId){
        answerid.typeId=typeId;
    }
    public String getTypeId(){
        return answerid.typeId ;
    }

    public void setAnswerIds(String answerIds){
        this.answerid.answerIds=answerIds;
    }

    public String getAnswerIds(){
        return answerid.answerIds;
    }

    public SendAnswerIds(String answerIds) {
        answerid.answerIds=answerIds;
    }
    public void sendAnswerIds(String url,Callback back)
    {
        Gson gson=new Gson();
        String json=gson.toJson(answerid);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回所选用户
    }


    class Answerid
    {
        String typeId="1";
        String answerIds;
    }
}
