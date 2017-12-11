package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wisdom.cww.domain.Question;
import com.wisdom.cww.domain.Request;
import com.wisdom.cww.domain.Result;
import com.wisdom.cww.domain.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReplyActivity extends AppCompatActivity {


    private String serverUrl = "http://39.106.114.141";
    public String userid;
    public String username;
    public EditText ideaText;
    public ArrayList<Result> resultsArrayList = new ArrayList<Result>();
    public ArrayList<Question> questionArrayList = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //toolbar.setTitle(R.string.write_idea_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_publish:
                                String ans = ideaText.getText().toString();
                                User user = new User();
                                user.setUsername(username);
                                user.setUserId(userid);
                                if(!ans.equals("")){
                                    String questionId = (String)getIntent().getBundleExtra("userInfo").getSerializable("questionId");
                                    user.addAnswer(ans,questionId,boolback,serverUrl);
                                }
                                break;
                        }
                        return true;

                    }
                }
        );

        ideaText = (EditText) findViewById(R.id.ideaText);

        Init();

        Intent intent = getIntent();
        Bundle bund = intent.getBundleExtra("userInfo");
        if (bund != null) {
            User user = (User) bund.getSerializable("user");
            userid = user.getUserId();
            username = user.getUsername();
        }


    }

    private void Init () {
        userid = "-1";
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    publishReact(msg);
                    break;
                case 1:
                    backPreviousPage(msg);
                    break;
            }
        }
    };

    public void backPreviousPage(Message msg) {

        if (msg != null) {
            ArrayList<Question> queslist = (ArrayList<Question>)msg.obj;
            Intent ideaIntent = new Intent(ReplyActivity.this, IdeaActivity.class);
            Bundle bund = new Bundle();
            bund.putSerializable("queslist",queslist);
            User user = new User();
            user.setUsername(username);
            user.setUserId(userid);
            bund.putSerializable("user",user);

            ideaIntent.putExtra("userInfo",bund);
            startActivity(ideaIntent);
        }

    }


    public void publishReact (Message msg) {
        ArrayList<Result> res = (ArrayList<Result>)msg.obj;
        if(res.get(0).getResult().equals("failure")) {

            String publishFailure = "答案发布失败，检查网络连接";
            Toast.makeText(getApplicationContext(), publishFailure, Toast.LENGTH_SHORT).show();
        } else if (res.get(0).getResult().equals("success")) {
            String publishSuccess = "答案已发布";
            Toast.makeText(getApplicationContext(), publishSuccess, Toast.LENGTH_SHORT).show();

            Request request = new Request();
            request.send(serverUrl,questionback);

        }

        /*if (!ideaText.getText().toString().equals("")){
            String publishSuccess = "新问题已发布";
            Toast.makeText(getApplicationContext(), publishSuccess, Toast.LENGTH_SHORT).show();

            Intent ideaIntent = new Intent(WriteIdeaActivity.this, IdeaActivity.class);
            Bundle bund = new Bundle();
            //bund.putSerializable();
            ideaIntent.putExtra("userInfo",bund);
            startActivity(ideaIntent);
        }*/
    }


    private Callback boolback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i("MainActivity","onFailure");
            resultsArrayList.clear();
            e.printStackTrace();
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Gson gson=new Gson();
            resultsArrayList.clear();
            String jsonData = response.body().string();
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray("information");

            //            String m=new String();
            for(JsonElement result:jsonArray)
            {
                Result result1=gson.fromJson(result,new TypeToken<Result>() {}.getType());
                resultsArrayList.add(result1);
                //                m=m+" "+question1.toString();
            }

            Message message=handler.obtainMessage();
            message.obj=resultsArrayList;
            message.what = 0;
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
            message.what = 1;
            message.sendToTarget();
        }
    };




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_reply,menu);
        return true;
    }


}
