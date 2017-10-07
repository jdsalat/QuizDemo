package com.machinetest.quiz.beans;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public class UserAnswerBean {

    private int questionId;
    private int answerId;


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Override
    public String toString() {
        return "UserAnswerBean{" +
                "questionId=" + questionId +
                ", answerId=" + answerId +
                '}';
    }
}
