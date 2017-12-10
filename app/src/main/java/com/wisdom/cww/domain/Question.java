package com.wisdom.cww.domain;

/**
 * Created by Mr.Wang on 2017/12/3.
 */

public class Question {
    String questionId;
    String question;
    String date;
    String userId;
    String answerIds;

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
        return question +" " +questionId+" "  + userId+" "  + answerIds+" "  + date;
    }
}
