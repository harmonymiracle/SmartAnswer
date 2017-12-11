package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wisdom.cww.domain.Answer;
import com.wisdom.cww.domain.Comment;
import com.wisdom.cww.domain.Question;
import com.wisdom.cww.domain.Request;
import com.wisdom.cww.domain.Result;
import com.wisdom.cww.domain.SendAnswerIds;
import com.wisdom.cww.domain.SendQuestion;
import com.wisdom.cww.domain.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();

    private String serverUrl = "http://39.106.114.141";
    public String userid;
    public String username;
    public EditText ideaText;
    public ArrayList<Result> resultsArrayList = new ArrayList<Result>();


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //    private Handler handler = new Handler() {
    //        @Override
    //        public void handleMessage (Message msg) {
    //            String result = (String) msg.obj;
    //            //rs.setText (result);
    //        }
    //    };
    public ArrayList<Question> questionArrayList = new ArrayList<Question>();
    public ArrayList<Answer> answerArrayList=new ArrayList<Answer>();
    public ArrayList<Comment> commentArrayList=new ArrayList<Comment>();
    public ArrayList<String>  stringArrayList=new ArrayList<String>();


    public Button searchBtn;
    public EditText questionTitle;
    public EditText matchQustionList;
    public LinearLayout mainLL;

    public LinearLayout ansL;
    public String question;
    public String actualQuestion;


    public ImageButton searchBar2;
    public ImageButton searchBar;
    public ImageButton pubBar;
    public ImageButton accountBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionTitle = (EditText)findViewById(R.id.question);
        mainLL = (LinearLayout) findViewById(R.id.mainLL);

        searchBar = (ImageButton)findViewById(R.id.searchBar);
        searchBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        pubBar = (ImageButton)findViewById(R.id.pubBar);
        pubBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Request request = new Request();
                        request.send(serverUrl,noAnsQuestionback);

                    }
                }
        );

        accountBar = (ImageButton)findViewById(R.id.accountBar);
        accountBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);

                        startActivity(accountIntent);
                    }
                }
        );


        searchBtn = (Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String quesStr = questionTitle.getText().toString();
                        question = quesStr;
                        Gson gson=new Gson();
                        SendQuestion Q = new SendQuestion(quesStr);
                        Q.sendQuestion(serverUrl,questionback);
                    }
                }
        );




    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    handleQuestion(msg);
                    break;
                case 1:
                    handleAnswers(msg);
                    break;
                case 2:
                    goPubPage(msg);
                    break;
            }
        }
    };


    public void handleQuestion (Message msg) {
        ArrayList<Question> Q=(ArrayList<Question>) msg.obj;
        for (int i = 0 ; i < questionArrayList.size(); i++) {
            EditText edt = new EditText(this);
            String content = "";
            content += questionArrayList.get(i).getDate();
            content += questionArrayList.get(i).getQuestion();

            final int temp = i;
            edt.setText(content);//edt.setText(content);
            edt.setFreezesText(true);
            edt.setCursorVisible(false);
            //edt.setEnabled(false);

            edt.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String answerIds = questionArrayList.get(temp).getAnswerIds();
                            actualQuestion = questionArrayList.get(temp).getQuestion();
                            Gson gson=new Gson();
                            SendAnswerIds Q = new SendAnswerIds(answerIds);
                            Q.sendAnswerIds(serverUrl,answerback);

                        }
                    }
            );
            mainLL.addView(edt);
        }

    }

    public void handleAnswers(Message msg) {
        ArrayList<Answer> answerArrayList=(ArrayList<Answer>)msg.obj;
        Intent answerIntent = new Intent(MainActivity.this, AnswerActivity.class);
        Bundle bunde = new Bundle();
        bunde.putSerializable("answerList",answerArrayList);
        bunde.putString("question",actualQuestion);
        answerIntent.putExtra("answer",bunde);
        startActivity(answerIntent);


        /*setContentView(R.layout.answer);

        searchBar2 = (ImageButton)findViewById(R.id.searchBar2);

        searchBar2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setContentView(R.layout.activity_main);
                    }
                }
        );


        ansL = (LinearLayout) findViewById(R.id.ansLL);

        EditText edt = new EditText(this);

        edt.setFreezesText(true);
        edt.setCursorVisible(false);
        edt.setTextColor(Color.rgb(60,185,20));
        edt.setTextSize(30);

        String content = "您正查看的问题是： \n";
        content += actualQuestion;
        edt.setText(content);
        ansL.addView(edt);


        for (int i = 0 ; i < answerArrayList.size(); i++) {
            edt = new EditText(this);
            content = "";
            content += "日期：  ";
            //content += String.valueOf(i);
            content += answerArrayList.get(i).getDate();
            content += "\n正文： ";
            //content += String.valueOf(i);
            content += answerArrayList.get(i).getAnswer();

            edt.setText(content);
            edt.setFreezesText(true);
            edt.setCursorVisible(false);


            ansL.addView(edt);

        }
*/


    }

    //跳转到提问界面
    public void goPubPage (Message msg) {
        ArrayList<Question> queslist = (ArrayList<Question>)msg.obj;
        Intent pubIntent = new Intent(MainActivity.this, IdeaActivity.class);

        Bundle bund = new Bundle();
        bund.putSerializable("queslist",queslist);
        User user = new User();
        user.setUsername(username);
        user.setUserId(userid);
        bund.putSerializable("user",user);
        pubIntent.putExtra("userInfo",bund);
        startActivity(pubIntent);
    }

    private Callback noAnsQuestionback=new Callback(){
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

            //            String m=new String();
            for(JsonElement question :jsonArray)
            {
                Question question1=gson.fromJson(question,new TypeToken<Question>() {}.getType());
                questionArrayList.add(question1);
                //                m=m+" "+question1.toString();
            }

            Message message=handler.obtainMessage();
            message.obj=questionArrayList;
            message.what=2;
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
            JsonArray jsonArray=jsonObject.getAsJsonArray("information");
            Gson gson=new Gson();
            for(JsonElement answer :jsonArray)
            {
                Answer answer1=gson.fromJson(answer,new TypeToken<Answer>() {}.getType());
                answerArrayList.add(answer1);
            }
            Message message=handler.obtainMessage();
            message.obj=answerArrayList;
            message.what=1;
            message.sendToTarget();
        }
    };

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

            //            String m=new String();
            for(JsonElement question :jsonArray)
            {
                Question question1=gson.fromJson(question,new TypeToken<Question>() {}.getType());
                questionArrayList.add(question1);
                //                m=m+" "+question1.toString();
            }

            Message message=handler.obtainMessage();
            message.obj=questionArrayList;
            message.what = 0;
            message.sendToTarget();
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
            for(JsonElement comment :jsonArray)
            {
                Comment comment1=gson.fromJson(comment,new TypeToken<Comment>() {}.getType());
                commentArrayList.add(comment1);
            }
            Message message=handler.obtainMessage();
            message.obj=commentArrayList;
            message.what=2;
            message.sendToTarget();
        }
    };

    private Callback boolback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i("MainActivity","onFailure");
            stringArrayList.clear();
            e.printStackTrace();
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Gson gson=new Gson();
            stringArrayList.clear();
            String jsonData = response.body().string();
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray("result");

            //            String m=new String();
            for(JsonElement string :jsonArray)
            {
                String  s=gson.fromJson(string,new TypeToken<String>() {}.getType());
                stringArrayList.add(s);
                //                m=m+" "+question1.toString();
            }

            Message message=handler.obtainMessage();
            message.obj=stringArrayList;
            message.what=3;
            message.sendToTarget();
        }
    };

}
