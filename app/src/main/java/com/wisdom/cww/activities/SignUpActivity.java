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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wisdom.cww.domain.Result;
import com.wisdom.cww.domain.SignUp;

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


    public ArrayList<Result>  resultsArrayList = new ArrayList<Result>();


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
                        SignUp signup = new SignUp();
                        String temp = accountInput.getText().toString();
                        if (temp != ""){
                            signup.setUsername(temp);
                            signup.sendusername(serverUrl,boolback);
                            //user.setNickname (temp);
                        }

                    }
                }
        );

        signInBtn = (Button) findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        SignUp signup = new SignUp();

                        String tempUser = accountInput.getText().toString();
                        String tempPwd = pwdInput.getText().toString();
                        if (!tempUser.equals("") && !tempPwd.equals("")){
                            signup.setUsername(tempUser);
                            signup.setPassword(tempPwd);
                            signup.Login(serverUrl,boolSigninBack);
                        }


                    }
                }
        );



        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        SignUp signup = new SignUp();

                        String tempUser = accountInput.getText().toString();
                        String tempPwd = pwdInput.getText().toString();
                        if (!tempUser.equals("") && !tempPwd.equals("")){
                            signup.setUsername(tempUser);
                            signup.setPassword(tempPwd);
                            signup.signup(serverUrl,boolSignupBack);
                        }


                        /*User user = new User();
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
                        finish();*/

                    }
                }
        );





    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    userInputReact(msg);
                    break;
                case 4:
                    userSigninReact(msg);
                    break;
                case 5:
                    userSignupReact(msg);
                    break;
            }
        }
    };

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
            message.what=3;
            message.sendToTarget();
        }
    };

    private Callback boolSigninBack = new Callback() {
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
            message.what=4;
            message.sendToTarget();
        }
    };

    private Callback boolSignupBack = new Callback() {
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
            message.what=5;
            message.sendToTarget();
        }
    };


    public void userSigninReact (Message msg) {
        ArrayList<Result> res = (ArrayList<Result>)msg.obj;
        if(res.get(0).getResult().equals("failure")) {
            accountInput.setTextColor(Color.rgb(255, 0, 0));
            accountInput.setText(accountInput.getText());
            String signinFailure = "账户已存在，不可重复注册";
            Toast.makeText(getApplicationContext(), signinFailure, Toast.LENGTH_SHORT).show();
        } else if (res.get(0).getResult().equals("success")) {
            accountInput.setTextColor(Color.rgb(255, 255, 0));
            accountInput.setText(accountInput.getText());
        }
    }

    public void userSignupReact (Message msg) {
        ArrayList<Result> res = (ArrayList<Result>)msg.obj;
        if(res.get(0).getResult().equals("failure")) {
            accountInput.setTextColor(Color.rgb(255, 0, 255));
            accountInput.setText(accountInput.getText());
            String signupFailure = "用户名或密码错误";
            Toast.makeText(getApplicationContext(), signupFailure, Toast.LENGTH_SHORT).show();
        } else if (res.get(0).getResult().equals("success")) {
            accountInput.setTextColor(Color.rgb(0, 255, 255));
            accountInput.setText(accountInput.getText());

            Bundle bund = new Bundle();
            //bund.putSerializable();
            Intent accountActivity = new Intent(SignUpActivity.this, AccountActivity.class);
            accountActivity.putExtra("userInfo",bund);

            startActivity(accountActivity);


        }
    }



    public void userInputReact (Message msg) {
        ArrayList<Result> res = (ArrayList<Result>)msg.obj;
        if(res.get(0).getResult().equals("failure")) {
            accountInput.setTextColor(Color.rgb(255, 0, 0));
            accountInput.setText(accountInput.getText());
            String signcheckFailure = "账户名已存在";
            Toast.makeText(getApplicationContext(), signcheckFailure, Toast.LENGTH_SHORT).show();
        } else if (res.get(0).getResult().equals("success")) {
            accountInput.setTextColor(Color.rgb(0, 255, 0));
            accountInput.setText(accountInput.getText());
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
