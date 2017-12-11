package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wisdom.cww.domain.Question;
import com.wisdom.cww.domain.Request;
import com.wisdom.cww.domain.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AccountActivity extends AppCompatActivity {

    public final int forSign = 1000;
    public ImageButton accountImage;
    private String serverUrl = "http://39.106.114.141";
    public ArrayList<Question> questionArrayList = new ArrayList<Question>();

    public ImageButton searchBar;
    public ImageButton pubBar;
    public ImageButton accountBar;

    public EditText nickName;
    public EditText myFollowQuestion;
    public EditText myFollowAnswer;

    public String userName;
    public String userPwd;
    public String userId = "-1";
    public boolean hasLogin;

    private void Init () {
        userName = "未登录";
        userPwd  = "";
        hasLogin = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchBar = (ImageButton)findViewById(R.id.searchBar);
        searchBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent searchIntent = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(searchIntent);
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

                    }
                }
        );

        nickName = (EditText)findViewById(R.id.nickName);
        nickName.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        if (!hasLogin) {
                            Intent SignIntent = new Intent(AccountActivity.this, SignUpActivity.class);
                            startActivityForResult(SignIntent,forSign);
                        }
                        else {
                            nickName.setText(userName);
                        }
                    }
                }
        );


        Init();
        Intent data = getIntent();
        Bundle bund = data.getBundleExtra("userInfo");
        if (bund != null) {
            User userInfo = (User) bund.getSerializable("user");
            if(userInfo.getUsername() != null) {
                userName = userInfo.getUsername();
            } else {
                userName = "未登录";
            }
            //.equals("")?"未登录":userInfo.getUsername();
            userId = userInfo.getUserId();

            RefreshState();
        }


    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:
                    goPubPage(msg);
                    break;
            }
        }
    };

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


    public void goPubPage (Message msg) {
        ArrayList<Question> queslist = (ArrayList<Question>)msg.obj;
        Intent pubIntent = new Intent(AccountActivity.this, IdeaActivity.class);

        if(!userId.equals("-1") ){

            User user = new User();
            user.setUserId(userId);
            user.setUsername(userName);
            Bundle bund = new Bundle();
            bund.putSerializable("user",user);
            bund.putSerializable("queslist",queslist);

            pubIntent.putExtra("userInfo",bund);
            startActivity(pubIntent);
        } else {
            String writeWarning = "请注意，您尚未登录";
            Toast.makeText(getApplicationContext(), writeWarning, Toast.LENGTH_SHORT).show();

        }
        startActivity(pubIntent);

    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (1000 == requestCode && resultCode == RESULT_OK) {
            Bundle bunde = data.getBundleExtra("user");
            User user = (User)bunde.getSerializable("user");
            //AccountInfo acinfo = (AccountInfo) bunde.getSerializable("user");
            String userNameTemp = user.getUsername();
            String userPwdTemp = user.getPassword();

            //String userNameTemp = data.getStringExtra("username");
            //String userPwdTemp = data.getStringExtra("userPwd");
            setUser(userNameTemp, userPwdTemp);
            RefreshState();

        }
    }

    private void setUser (String Name, String Pwd) {
        userName = Name;
        userPwd = Pwd;
        hasLogin = true;
    }

    private void RefreshState() {
        nickName.setText(userName);
    }

}
