package com.cww.smartanswer.model;

/**
 * Created by Mr.Wang on 2017/11/13.
 */

public class SendQuestion {

    String typeId="0";
    String question;

    public SendQuestion() {
        question = "who are you?";
        typeId = "0";
    }

    public SendQuestion(String qus, String tid) {
        question = qus;
        typeId = tid;
    }

    public void setTypeId(String typeId){this.typeId=typeId;}
    public void setQuestion(String question){this.question=question;}
    public String getTypeId(){return typeId;}
    public String getQuestion(){return question;}
}
