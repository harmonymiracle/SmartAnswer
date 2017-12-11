package com.wisdom.cww.domain;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.wisdom.cww.activities.MainActivity.JSON;

/**
 * Created by Mr.Wang on 2017/12/10.
 */

public class User implements Serializable{
    String username;
    String password;
    int userid;

    String nickname;
    ArrayList<Question> questions;         //发布的问题
    ArrayList<Answer>   answers;           //发布的答案

    //  ArrayList<Eassy>   eassies;  创作    //创作的文章
    ArrayList<Question> questionsCollected;//收藏的问题
    ArrayList<Question> questionsFollowed; //关注的问题
    ArrayList<User>     usersFollowed;     //关注的人
    ArrayList<User>     fans;              //粉丝
    //  ArrayList<Question> questionsUpvoted;//赞同的答案
    ArrayList<Answer>   answersUpvoted;    //赞同的回


    public void                setUsername(String username) {this.username=username;}
    public void                setPassword(String password) {this.password=password;}
    public void                setNickname(String nickname) {this.nickname=nickname;}
    public int              getUserid()                  {return userid;}
    public String              getUsername()                {return username;}
    public String              getPassword()                {return password;}
    public String              getNickname()                {return nickname;}
    public ArrayList<Question> getQuestions()               {return questions;}
    public ArrayList<Answer>   getAnswers()                 {return answers;}
    public ArrayList<Question> getQuestionsCollected()      {return questionsCollected;}
    public ArrayList<Question> getQuestionsFollowed()       {return questionsFollowed;}
    public ArrayList<User>     getUsersFollowed()           {return usersFollowed;}
    public ArrayList<User>     getFans()                    {return fans;}
    //  public ArrayList<Question> getQuestionsUpvoted()        {return questionsUpvoted;}
    public ArrayList<Answer>   getAnswersUpvoted()          {return answersUpvoted;}

    public void addQuestion(String question,Callback back,String url)                    //提问
    {
        OkHttpClient client = new OkHttpClient();
        AddQuestion addQuestion=new AddQuestion(userid,question);

        Gson gson=new Gson();
        String json=gson.toJson(addQuestion);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class AddQuestion
    {
        private int userid;
        private String questionId;
        private String typeId;
        public void setUserid(int userid){this.userid=userid;}
        public void setQuestionId(String questionId){this.questionId=questionId;}
        AddQuestion(int userid,String question)
        {
            setQuestionId(question);
            setUserid(userid);
        }
    }
    public void addAnswer(String answer,Callback back,String url)      //回答
    {
        OkHttpClient client = new OkHttpClient();
        AddAnswer addAnswer=new AddAnswer(userid,answer);

        Gson gson=new Gson();
        String json=gson.toJson(addAnswer);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class AddAnswer
    {
        private int userId;
        private String answer;
        private String typeId;
        public void setUserId(int userId){this.userId=userId;}
        public void setAnswer(String answer){this.answer=answer;}
        AddAnswer(int userId,String answer)
        {
            setAnswer(answer);
            setUserId(userId);
        }

    }
    public void followUser(int userid2,String url,Callback back)                      //关注人
    {
        OkHttpClient client = new OkHttpClient();
        FollowUser followUser=new FollowUser(userid,userid2);

        Gson gson=new Gson();
        String json=gson.toJson(followUser);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class FollowUser
    {
        private int userId;
        private int userid2;
        private String typeId;
        public void setUserId(int userId){this.userId=userId;}
        public void setUserid2(int userid2){this.userid2=userid2;}
        FollowUser(int userId,int userid2)
        {
            setUserId(userId);
            setUserid2(userid2);
        }
    }
    public void followQuestion(String questionId,String url,Callback back)               //关注问题
    {
        OkHttpClient client = new OkHttpClient();
        FollowQuestion followQuestion=new FollowQuestion(userid,questionId);
        Gson gson=new Gson();
        String json=gson.toJson(followQuestion);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class FollowQuestion
    {
        private int userId;
        private String questionId;
        private String typeId;
        public void setUserId(int userId){this.userId=userId;}
        public void setQuestionId(String questionId){this.questionId=questionId;}
        FollowQuestion(int userId,String questionId)
        {
            setQuestionId(questionId);
            setUserId(userId);
        }
    }
    public void upvoteAnswer(String answerId,String url,Callback back)                   //点赞答案
    {
        OkHttpClient client = new OkHttpClient();
        Upvote upvote=new Upvote(userid,answerId);
        Gson gson=new Gson();
        String json=gson.toJson(upvote);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class Upvote
    {
        private int userId;
        private String answerId;
        private String typeId;
        public void setUserId(int userId){this.userId=userId;}
        public void setAnswerId(String answerId){this.answerId=answerId;}
        Upvote(int userId,String answerId)
        {
            setUserId(userId);
            setAnswerId(answerId);
        }
    }

    public void collectQuestion(String questionId,String url,Callback back)              //收藏问题
    {
        OkHttpClient client = new OkHttpClient();
        CollectionQuestion collectionQuestion=new CollectionQuestion(userid,questionId);
        Gson gson=new Gson();
        String json=gson.toJson(collectionQuestion);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class CollectionQuestion
    {
        private int userId;
        private String questionId;
        private String typeId;
        public void setUserId(int userId){this.userId=userId;}
        public void setQuestionId(String questionId){this.questionId=questionId;}
        CollectionQuestion(int userId,String questionId)
        {
            setQuestionId(questionId);
            setUserId(userId);
        }
    }
}
