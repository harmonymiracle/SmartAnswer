package com.cww.smartanswer.model;

/**
 * Created by Mr.Wang on 2017/11/13.
 */

public class SendQuestionId {

    String typeId="1";
    String questionId;
    public void setQuestionId(String questionId){this.questionId=questionId;}
    public void setTypeId(String typeId){this.typeId=typeId;}
    public String getTypeId(){return typeId;}
    public String getQuestionId(){ return questionId;}
}
