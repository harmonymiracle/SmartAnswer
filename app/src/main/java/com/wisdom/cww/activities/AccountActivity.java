package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.wisdom.cww.domain.User;

public class AccountActivity extends AppCompatActivity {

    public final int forSign = 1000;
    public ImageButton accountImage;

    public ImageButton searchBar;
    public ImageButton pubBar;
    public ImageButton accountBar;

    public EditText nickName;
    public EditText myFollowQuestion;
    public EditText myFollowAnswer;

    public String userName;
    public String userPwd;
    public boolean hasLogin;

    private void Init () {
        userName = "";
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
                        Intent searchIntent = new Intent(AccountActivity.this, IdeaActivity.class);
                        Bundle bunde = new Bundle();
                        //bunde.putSerializable();
                        startActivity(searchIntent);
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
            userName = userInfo.getUsername();
            RefreshState();
        }


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
