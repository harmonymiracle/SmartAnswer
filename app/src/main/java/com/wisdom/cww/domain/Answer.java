package com.wisdom.cww.domain;

import java.io.Serializable;

/**
 * Created by Mr.Wang on 2017/12/3.
 */

public class Answer implements Serializable {
    String answerId;
    String answer;
    String date;
    String userId;
    String commentId;

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setAnswer(String answer)
    {
        this.answer=answer;
    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public void setUserId(String userId)
    {
        this.userId=userId;
    }
    public void setCommentId(String commentId)
    {
        this.commentId=commentId;
    }
    public String getAnswerId(){return  answerId;}
    public String getAnswer(){return answer;}
    public String getDate(){return date;}
    public String getUserId(){return userId;}
    public String getCommentId(){return commentId;}
    public String toString()
    {
        return answerId+"   "+answer+"  "+date+  "   "+userId+"   "+commentId;
    }
}
