package com.wisdom.cww.domain;

import com.google.gson.Gson;

import java.io.Serializable;

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
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String result;
    String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    String username;
    String password;


               //粉丝
    String   nickname;
    String   articleIds;
    String   questionCollectedIds;//收藏的问题
    String   questionFollowedIds; //关注的问题
    String   fansIds;              //粉丝
    String   usersFollowedIds;     //关注的人
    String   questionPublishedIds;         //发布的问题
    String   answerPublishedIds;           //发布的答案
    String   commentPublishedIds;
    String   answersUpvotedIds;    //赞同的回



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(String articleIds) {
        this.articleIds = articleIds;
    }

    public String getQuestionCollectedIds() {
        return questionCollectedIds;
    }

    public void setQuestionCollectedIds(String questionCollectedIds) {
        this.questionCollectedIds = questionCollectedIds;
    }

    public String getQuestionFollowedIds() {
        return questionFollowedIds;
    }

    public void setQuestionFollowedIds(String questionFollowedIds) {
        this.questionFollowedIds = questionFollowedIds;
    }

    public String getFansIds() {
        return fansIds;
    }

    public void setFansIds(String fansIds) {
        this.fansIds = fansIds;
    }

    public String getUsersFollowedIds() {
        return usersFollowedIds;
    }

    public void setUsersFollowedIds(String usersFollowedIds) {
        this.usersFollowedIds = usersFollowedIds;
    }

    public String getQuestionPublishedIds() {
        return questionPublishedIds;
    }

    public void setQuestionPublishedIds(String questionPublishedIds) {
        this.questionPublishedIds = questionPublishedIds;
    }

    public String getAnswerPublishedIds() {
        return answerPublishedIds;
    }

    public void setAnswerPublishedIds(String answerPublishedIds) {
        this.answerPublishedIds = answerPublishedIds;
    }

    public String getCommentPublishedIds() {
        return commentPublishedIds;
    }

    public void setCommentPublishedIds(String commentPublishedIds) {
        this.commentPublishedIds = commentPublishedIds;
    }

    public String getAnswersUpvotedIds() {
        return answersUpvotedIds;
    }

    public void setAnswersUpvotedIds(String answersUpvotedIds) {
        this.answersUpvotedIds = answersUpvotedIds;
    }


    public void addQuestion(String question,Callback back,String url)                    //提问
    {
        OkHttpClient client = new OkHttpClient();
        AddQuestion addQuestion=new AddQuestion(userId,question);

        Gson gson=new Gson();
        String json=gson.toJson(addQuestion);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class AddQuestion
    {
        private String userId;
        private String question;
        private String typeId = "4";
        public void setUserid(String userId){this.userId=userId;}
        public void setQuestion(String question){this.question=question;}
        public AddQuestion(String userId,String question)
        {
            setQuestion(question);
            setUserid(userId);
        }
    }
    public void addAnswer(String answer,String questionid,Callback back,String url)      //回答
    {
        OkHttpClient client = new OkHttpClient();
        AddAnswer addAnswer=new AddAnswer(userId,answer, questionid);

        Gson gson=new Gson();
        String json=gson.toJson(addAnswer);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class AddAnswer
    {
        public String getUserId() {
            return userId;
        }

        public String getAnswer() {
            return answer;
        }

        private String userId;
        private String questionId;
        private String answer;
        private String typeId="5";

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }


        public void setUserId(String userId){this.userId=userId;}
        public void setAnswer(String answer){this.answer=answer;}
        public AddAnswer(String userId,String answer,String questionId)
        {
            setAnswer(answer);
            setUserId(userId);
            setQuestionId(questionId);
        }

    }
    public void followUser(String userid2,String url,Callback back)                      //关注人
    {
        OkHttpClient client = new OkHttpClient();
        FollowUser followUser=new FollowUser(userId,userid2);

        Gson gson=new Gson();
        String json=gson.toJson(followUser);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class FollowUser
    {
        private String userId;
        private String userid2;
        private String typeId;
        public void setUserId(String userId){this.userId=userId;}
        public void setUserid2(String userid2){this.userid2=userid2;}
        FollowUser(String userId,String userid2)
        {
            setUserId(userId);
            setUserid2(userid2);
        }
    }
    public void followQuestion(String questionId,String url,Callback back)               //关注问题
    {
        OkHttpClient client = new OkHttpClient();
        FollowQuestion followQuestion=new FollowQuestion(userId,questionId);
        Gson gson=new Gson();
        String json=gson.toJson(followQuestion);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class FollowQuestion
    {
        private String userId;
        private String questionId;
        private String typeId;
        public void setUserId(String userId){this.userId=userId;}
        public void setQuestionId(String questionId){this.questionId=questionId;}
        FollowQuestion(String userId,String questionId)
        {
            setQuestionId(questionId);
            setUserId(userId);
        }
    }
    public void upvoteAnswer(String answerId,String url,Callback back)                   //点赞答案
    {
        OkHttpClient client = new OkHttpClient();
        Upvote upvote=new Upvote(userId,answerId);
        Gson gson=new Gson();
        String json=gson.toJson(upvote);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class Upvote
    {
        private String userId;
        private String answerId;
        private String typeId;
        public void setUserId(String userId){this.userId=userId;}
        public void setAnswerId(String answerId){this.answerId=answerId;}
        Upvote(String userId,String answerId)
        {
            setUserId(userId);
            setAnswerId(answerId);
        }
    }

    public void collectQuestion(String questionId,String url,Callback back)              //收藏问题
    {
        OkHttpClient client = new OkHttpClient();
        CollectionQuestion collectionQuestion=new CollectionQuestion(userId,questionId);
        Gson gson=new Gson();
        String json=gson.toJson(collectionQuestion);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        Call call = client.newCall(builder.build());
        call.enqueue(back);//返回问题链表
    }
    private class CollectionQuestion
    {
        private String userId;
        private String questionId;
        private String typeId;
        public void setUserId(String userId){this.userId=userId;}
        public void setQuestionId(String questionId){this.questionId=questionId;}
        CollectionQuestion(String userId,String questionId)
        {
            setQuestionId(questionId);
            setUserId(userId);
        }
    }
}
