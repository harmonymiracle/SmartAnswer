package com.wisdom.cww.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wisdom.cww.domain.Answer;
import com.wisdom.cww.domain.Comment;
import com.wisdom.cww.domain.Question;
import com.wisdom.cww.domain.SignUp;
import com.wisdom.cww.domain.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private String serverUrl = "http://39.106.114.141";
    public EditText accountInput;
    public EditText pwdInput;

    public Button checkBtn;
    public Button signUpBtn;
    public Button signInBtn;


    public ArrayList<String>  stringArrayList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.sign_up_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);


        accountInput = (EditText) findViewById(R.id.accountInput);
        pwdInput = (EditText) findViewById(R.id.pwdInput);

        checkBtn = (Button) findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        User user = new User();
                        SignUp signup = new SignUp();

                        //AccountInfo acInfo = new AccountInfo();
                        String temp = accountInput.getText().toString();
                        if (temp != ""){
                            signup.setUsername(temp);
                            signup.sendusername(serverUrl,boolback);
                            //user.setNickname (temp);
                        }

                    }
                }
        );



        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        User user = new User();
                        //AccountInfo acInfo = new AccountInfo();
                        String tempUser = accountInput.getText().toString();
                        String tempPwd = pwdInput.getText().toString();
                        if (tempUser != ""){
                            user.setUsername(tempUser);
                            user.setPassword(tempPwd);
                        }
                        Bundle bunde = new Bundle();
                        bunde.putSerializable("user",user);
                        Intent intent = new Intent(SignUpActivity.this, AccountActivity.class);
                        intent.putExtra("user",bunde);
                        setResult(RESULT_OK,intent);
                        finish();

                    }
                }
        );

        signInBtn = (Button) findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        User user = new User();
                        //AccountInfo acInfo = new AccountInfo();
                        String tempUser = accountInput.getText().toString();
                        String tempPwd = pwdInput.getText().toString();
                        if (tempUser != ""){
                            user.setUsername(tempUser);
                            user.setPassword(tempPwd);
                        }

                    }
                }
        );



    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ArrayList<Question> Q=(ArrayList<Question>) msg.obj;

                    break;
                case 1:
                    ArrayList<Answer> A=(ArrayList<Answer>)msg.obj;

                    break;
                case 2:
                    ArrayList<Comment> C=(ArrayList<Comment>)msg.obj;
                    break;
                case 3:
                    userInputReact(msg);
                    break;
            }
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
            JsonArray jsonArray=jsonObject.getAsJsonArray("information");

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

    public void userInputReact (Message msg) {
        if (msg.obj.toString().equals("failure")){
            accountInput.setTextColor(Color.rgb(255, 0, 0));
        } else if (msg.obj.toString().equals("success")) {
            accountInput.setTextColor(Color.rgb(0, 255, 0));
        }

    }



/*
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }*/


}
