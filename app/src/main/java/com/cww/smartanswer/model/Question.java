package com.cww.smartanswer.model;

/**
 * Created by Mr.Wang on 2017/11/13.
 */

public class Question {
    String questionId;
    String question;
    String date;
    String userId;
    String answerIds;

    public Question() {
        question = "who are you";
        userId = "0";
    }
    public Question(String qus, String uid) {
        question = qus;
        userId = uid;
    }

    public Question (String qusId, String qus, String dat, String uid, String ansIds) {
        questionId = qusId;
        question = qus;
        date = dat;
        userId = uid;
        answerIds = ansIds;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }

    public void setAnswerids(String answerids) {
        this.answerIds = answerids;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public String getAnswerIds() {
        return answerIds;
    }

    public String toString() {
        return question + questionId + userId + answerIds + date;
    }
}
