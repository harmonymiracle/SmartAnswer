package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wisdom.cww.domain.Answer;
import com.wisdom.cww.domain.Comment;
import com.wisdom.cww.domain.Question;
import com.wisdom.cww.domain.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IdeaActivity extends AppCompatActivity {

    private String serverUrl = "http://39.106.114.141";
    public ArrayList<Answer> answerArrayList=new ArrayList<Answer>();
    public ImageButton searchBar;
    public ImageButton pubBar;
    public ImageButton accountBar;

    public LinearLayout questionList;


    public String userId = "-1";
    public String userName = "尚未登录";
    public boolean hasLogin = false;

    public ArrayList<Question> questionArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.idea_title);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);
        questionList = (LinearLayout) findViewById(R.id.questionLL);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                if (userId.equals("-1")) {
                    String writeFailure = "发布问题请登录";
                    Toast.makeText(getApplicationContext(), writeFailure, Toast.LENGTH_SHORT).show();
                    Intent signupIntent = new Intent(IdeaActivity.this, AccountActivity.class);
                    startActivity(signupIntent);
                } else {
                    Intent writeIntent = new Intent(IdeaActivity.this, WriteIdeaActivity.class);
                    if (hasLogin) {

                        User user = new User();
                        user.setUserId(userId);
                        user.setUsername(userName);
                        Bundle bund = new Bundle();
                        bund.putSerializable("user",user);

                        writeIntent.putExtra("userInfo",bund);
                        startActivity(writeIntent);
                    }

                }


            }
        });

        searchBar = (ImageButton)findViewById(R.id.searchBar);
        searchBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent searchIntent = new Intent(IdeaActivity.this, MainActivity.class);
                        if (hasLogin) {

                            User user = new User();
                            user.setUserId(userId);
                            user.setUsername(userName);
                            Bundle bund = new Bundle();
                            bund.putSerializable("user",user);
                            searchIntent.putExtra("userInfo",bund);

                        }
                        startActivity(searchIntent);
                    }
                }
        );

        pubBar = (ImageButton)findViewById(R.id.pubBar);
        pubBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        accountBar = (ImageButton)findViewById(R.id.accountBar);
        accountBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent accountIntent = new Intent(IdeaActivity.this, AccountActivity.class);
                        if (hasLogin) {

                            User user = new User();
                            user.setUserId(userId);
                            user.setUsername(userName);
                            Bundle bund = new Bundle();
                            bund.putSerializable("user",user);

                            accountIntent.putExtra("userInfo",bund);

                        }
                        startActivity(accountIntent);

                    }
                }
        );

        Intent intent = getIntent();
        Bundle bund = intent.getBundleExtra("userInfo");
        if (bund != null) {
            User user = (User) bund.getSerializable("user");
            if (user != null) {
                userId = user.getUserId();
                userName = user.getUsername();
                hasLogin = true;
            }

        }
        Bundle questionBund = intent.getBundleExtra("userInfo");
        if (questionBund != null) {
            questionArrayList = (ArrayList<Question>) questionBund.getSerializable("queslist");
            if(questionArrayList != null){
                RefreshDisplay();
            }

        } else {
        }

    }

    public void RefreshDisplay () {

        String publishSuccess = "新问题已更新";
        Toast.makeText(getApplicationContext(), publishSuccess, Toast.LENGTH_SHORT).show();
        for (int i = 0 ; i < questionArrayList.size(); i++) {
            EditText edt = new EditText(this);
            String content = "";
            //content += questionArrayList.get(i).getDate();
            content += questionArrayList.get(i).getQuestion();

            final int temp = i;
            edt.setText(content);//edt.setText(content);
            edt.setFreezesText(true);
            edt.setCursorVisible(false);
            edt.setFocusable(false);
            //edt.setEnabled(false);

            edt.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            User user = (User)getIntent().getBundleExtra("userInfo").getSerializable("user");
                            Intent replyIntent = new Intent(IdeaActivity.this, ReplyActivity.class);
                            Bundle bunde = new Bundle();
                            bunde.putSerializable("user",user);
                            bunde.putSerializable("questionId",questionArrayList.get(temp).getQuestionId());
                            replyIntent.putExtra("userInfo",bunde);
                            startActivity(replyIntent);



                            /*final String answerIds = questionArrayList.get(temp).getAnswerIds();
                            actualQuestion = questionArrayList.get(temp).getQuestion();
                            Gson gson=new Gson();
                            SendAnswerIds Q = new SendAnswerIds(answerIds);
                            Q.sendAnswerIds(serverUrl,answerback);*/


                        }
                    }
            );

            questionList.addView(edt);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ArrayList<Question> Q=(ArrayList<Question>) msg.obj;
                    //handleQuestion();
                    break;
                case 1:
                    ArrayList<Answer> A=(ArrayList<Answer>)msg.obj;
                    //handleAnswers();
                    break;
                case 2:
                    ArrayList<Comment> C=(ArrayList<Comment>)msg.obj;
                    break;
            }
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
            Message message= handler.obtainMessage();
            message.obj=answerArrayList;
            message.what=1;
            message.sendToTarget();
        }
    };




}
