package com.cww.smartanswer.model;

/**
 * Created by Mr.Wang on 2017/11/13.
 */

public class Comment {
    String commentId;
    String comment;
    String date;
    String userId;

    public void setCommentId(String commitId)
    {
        this.commentId=commitId;
    }
    public void setCommit(String commit)
    {
        this.comment=commit;
    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public void setUserId(String userId)
    {
        this.userId=userId;
    }
    public String getCommitId(){return commentId;}
    public String getCommit(){return comment;}
    public String getDate(){return  date;}
    public String getUserId(){return userId;}
}
