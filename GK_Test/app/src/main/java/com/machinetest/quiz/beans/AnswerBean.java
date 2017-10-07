package com.machinetest.quiz.beans;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public class AnswerBean {

    private int answerdId;
    private String answerText;

    public int getAnswerdId() {
        return answerdId;
    }

    public void setAnswerdId(int answerdId) {
        this.answerdId = answerdId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }


    @Override
    public String toString() {
        return "AnswerBean{" +
                "answerdId=" + answerdId +
                ", answerText='" + answerText + '\'' +
                '}';
    }
}
