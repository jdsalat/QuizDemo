package com.machinetest.quiz.beans;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public class Questions {

    private int ID;
    private String question;
    private AnswerBean optionA;
    private AnswerBean optionB;
    private AnswerBean optionC;
    private AnswerBean optionD;

    private int answer;


    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerBean getOptionA() {
        return optionA;
    }

    public void setOptionA(AnswerBean optionA) {
        this.optionA = optionA;
    }

    public AnswerBean getOptionB() {
        return optionB;
    }

    public void setOptionB(AnswerBean optionB) {
        this.optionB = optionB;
    }

    public AnswerBean getOptionC() {
        return optionC;
    }

    public void setOptionC(AnswerBean optionC) {
        this.optionC = optionC;
    }

    public AnswerBean getOptionD() {
        return optionD;
    }

    public void setOptionD(AnswerBean optionD) {
        this.optionD = optionD;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "ID=" + ID +
                ", question='" + question + '\'' +
                ", optionA=" + optionA +
                ", optionB=" + optionB +
                ", optionC=" + optionC +
                ", optionD=" + optionD +
                ", answer=" + answer +
                '}';
    }
}
