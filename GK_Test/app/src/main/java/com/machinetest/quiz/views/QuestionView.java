package com.machinetest.quiz.views;

import com.machinetest.quiz.beans.Questions;
import com.machinetest.quiz.beans.ResponseBean;

import java.util.List;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public interface QuestionView {
    public void loadQuestions(List<Questions> questionsList);

    public void onSaveSuccess(ResponseBean responseBean);

    public void onSaveFailure();


}
