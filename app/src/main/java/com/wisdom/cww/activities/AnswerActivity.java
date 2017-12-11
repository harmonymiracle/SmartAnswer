package com.wisdom.cww.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.wisdom.cww.domain.Answer;
import com.wisdom.cww.domain.Question;
import com.wisdom.cww.domain.Result;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {

    private String serverUrl = "http://39.106.114.141";
    public String userid;
    public String username;
    public EditText ideaText;
    public ArrayList<Result> resultsArrayList = new ArrayList<Result>();

    public ArrayList<Question> questionArrayList = new ArrayList<Question>();
    public ArrayList<Answer> answerArrayList=new ArrayList<Answer>();
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
        setContentView(R.layout.activity_answer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.answer_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);

        Intent intent = getIntent();
        Bundle bunde = intent.getBundleExtra("answer");
        actualQuestion = bunde.getString("question");
        ArrayList<Answer> answerArrayList = (ArrayList<Answer>) bunde.getSerializable("answerList");



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


    }

    public void handleAnswers(Message msg) {
        ArrayList<Answer> answerArrayList=(ArrayList<Answer>)msg.obj;
        setContentView(R.layout.answer);

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



    }


}
