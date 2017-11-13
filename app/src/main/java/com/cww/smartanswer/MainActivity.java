package com.cww.smartanswer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cww.smartanswer.model.Answer;
import com.cww.smartanswer.model.Comment;
import com.cww.smartanswer.model.Question;
import com.cww.smartanswer.model.SendAnswerId;
import com.cww.smartanswer.model.SendQuestion;
import com.cww.smartanswer.model.SendQuestionId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private String serverUrl = "http://192.168.1.176:9000/";
    private TextView rs;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Handler handler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            String result = (String) msg.obj;
            rs.setText (result);
        }
    };
    public ArrayList<Question> questionArrayList = new ArrayList<Question>();
    public ArrayList<Answer> answerArrayList=new ArrayList<>();
    public ArrayList<Comment> commentArrayList=new ArrayList<>();

    public Button searchBtn;
    public EditText questionTitle;
    public EditText matchQustionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionTitle = (EditText)findViewById(R.id.question);

        searchBtn = (Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String quesStr = questionTitle.getText().toString();
                        SendQuestion Q = new SendQuestion();
                        Q.setQuestion(quesStr);
                        Q.setTypeId("0");

                        sendQuestion(serverUrl, Q);
                        questionTitle.setText( String.valueOf(questionArrayList.size()) );

                    }
                }
        );
    }


    /*
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.btn_get){
            }else if(view.getId()==R.id.btn_post){
                SendQuestion Q=new SendQuestion();
                Q.setQuestion("who are you");
                Q.setTypeId("0");
                sendQuestion(url,Q);
            }
        }
    };
    */

    private void sendQuestion(String url,SendQuestion Question){
        //向服务器发送用户问题
        Gson gson=new Gson();
        String json=gson.toJson(Question);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(questionback);//返回问题链表
    }

    private void sendQuestionId(String url,SendQuestionId QuestionId){
        //向服务器发送用户选择的问题
        Gson gson=new Gson();
        String json=gson.toJson(QuestionId);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(answerback);//返回答案列表
    }

    private void sendAnswerId(String url,SendAnswerId AnswerId){
        //向服务器发送用户选择的答案
        Gson gson=new Gson();
        String json=gson.toJson(AnswerId);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(commentback);//返回所选用户
    }


    private Callback questionback=new Callback(){
        @Override

        public void onFailure(Call call, IOException e) {
            Log.i("MainActivity","onFailure");
            questionArrayList.clear();
            e.printStackTrace();
        }

        @Override
        public  void  onResponse(Call call, Response response) throws IOException {
            //从response从获取服务器返回的数据，转成字符串处理
            questionArrayList.clear();
            Gson gson=new Gson();
            String jsonData = response.body().string();
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray("information");

            String m=new String();
            for(JsonElement question :jsonArray)
            {
                Question question1=gson.fromJson(question,new TypeToken<Question>() {}.getType());
                questionArrayList.add(question1);
                m=m+question1.toString();
            }

                        Message message=handler.obtainMessage();
                        message.obj=jsonData;
                        message.sendToTarget();
        }
    };


    private Callback answerback=new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i("MainActivity","onFailure");
            answerArrayList.clear();
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            //从response从获取服务器返回的答案列表
            answerArrayList.clear();
            String jsonData = response.body().string();
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray("answer");
            Gson gson=new Gson();

            //String m=new String();
            for(JsonElement answer :jsonArray)
            {
                Answer answer1=gson.fromJson(answer,new TypeToken<Answer>() {}.getType());
                answerArrayList.add(answer1);
            }
            //通过handler更新UI
            //            Message message=handler.obtainMessage();
            //            message.obj=m;
            //            message.sendToTarget();
        }
    };

    private Callback commentback=new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i("MainActivity","onFailure");
            commentArrayList.clear();
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            //从response从获取服务器返回的答案列表
            commentArrayList.clear();
            String jsonData = response.body().string();
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray("answer");
            Gson gson=new Gson();
            //String m=new String();
            for(JsonElement comment :jsonArray)
            {
                Comment comment1=gson.fromJson(comment,new TypeToken<Comment>() {}.getType());
                commentArrayList.add(comment1);
                // m=m+comment1.toString();
            }
            //            Message message=handler.obtainMessage();
            //            message.obj=m;
            //            message.sendToTarget();
        }
    };



}
