package com.machinetest.quiz.database;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.machinetest.quiz.beans.AnswerBean;
import com.machinetest.quiz.beans.Questions;
import com.machinetest.quiz.beans.ResponseBean;
import com.machinetest.quiz.beans.ServerRequestBean;
import com.machinetest.quiz.beans.UserAnswerBean;
import com.machinetest.quiz.network.RestAPI;
import com.machinetest.quiz.network.endpoints.UserAnswerEndPoints;
import com.machinetest.quiz.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public class QuestionsDAO {


    private static QuestionsDAO questionsDAO = null;
    private Context mContext;

    public static QuestionsDAO getInstance(Context mContext) {
        if (questionsDAO == null) {
            questionsDAO = new QuestionsDAO(mContext);
        }
        return questionsDAO;
    }

    public QuestionsDAO(Context mContext) {
        this.mContext = mContext;
    }


    public Observable getQuestionsList() {
        final List<Questions> questionsList = new ArrayList<>();
        Questions questions;

        AnswerBean answerBean;

        questions = new Questions();
        questions.setID(1);
        questions.setQuestion("Q1.:- Who developed Android ?");

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(1);
        answerBean.setAnswerText("James Ghosling");
        questions.setOptionA(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(2);
        answerBean.setAnswerText("Dennis Ritchie");
        questions.setOptionB(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(3);
        answerBean.setAnswerText("Andy Rubin");
        questions.setOptionC(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(4);
        answerBean.setAnswerText("Albert Einstein");
        questions.setOptionD(answerBean);

        questions.setAnswer(3);
        questionsList.add(questions);

        questions = new Questions();
        questions.setID(2);
        questions.setQuestion("Q2.:- Who invented Next Step?");
        answerBean = new AnswerBean();
        answerBean.setAnswerdId(1);
        answerBean.setAnswerText("Steve Jobs");
        questions.setOptionA(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(2);
        answerBean.setAnswerText("Dennis Ritchie");
        questions.setOptionB(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(3);
        answerBean.setAnswerText("Andy Rubin");
        questions.setOptionC(answerBean);


        answerBean = new AnswerBean();
        answerBean.setAnswerdId(4);
        answerBean.setAnswerText("James Ghosling");
        questions.setOptionD(answerBean);
        questions.setAnswer(1);
        questionsList.add(questions);


        questions = new Questions();
        questions.setID(3);
        questions.setQuestion("Q3.:- Who invented Facebook?");
        answerBean = new AnswerBean();
        answerBean.setAnswerdId(1);
        answerBean.setAnswerText("Mark Zukerbureg");
        questions.setOptionA(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(2);
        answerBean.setAnswerText("Dennis Ritchie");
        questions.setOptionB(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(3);
        answerBean.setAnswerText("Andy Rubin");
        questions.setOptionC(answerBean);

        answerBean = new AnswerBean();
        answerBean.setAnswerdId(4);
        answerBean.setAnswerText("James Ghosling");
        questions.setOptionD(answerBean);
        questions.setAnswer(1);
        questionsList.add(questions);


        return rx.Observable.create(new rx.Observable.OnSubscribe<List<Questions>>() {
            @Override
            public void call(Subscriber subscriber) {
                try {
                    subscriber.onNext(questionsList);    // Pass on the data to subscriber
                    subscriber.onCompleted();     // Signal about the completion subscriber
                } catch (Exception e) {
                    subscriber.onError(e);        // Signal about the error to subscriber
                }
            }

        });
    }


    public Observable saveDataToDatabase(List<UserAnswerBean> userAnswerBeanList) {

        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        for (UserAnswerBean userAnswerBean : userAnswerBeanList) {
            databaseHelper.saveUserAnswers(userAnswerBean);
        }

        return rx.Observable.create(new rx.Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber subscriber) {
                try {
                    final ResponseBean responseBean = new ResponseBean();
                    responseBean.setStatus(true);
                    responseBean.setCallFrom(Constants.CALL_DATABASE);
                    subscriber.onNext(responseBean);    // Pass on the data to subscriber
                    subscriber.onCompleted();     // Signal about the completion subscriber
                } catch (Exception e) {
                    subscriber.onError(e);        // Signal about the error to subscriber
                }
            }

        });

    }

    public List<UserAnswerBean> getAllUserAnswerBean() {
        List<UserAnswerBean> userAnswerBeanList = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        Cursor cursor = databaseHelper.getPendingUserAnswer();
        if (cursor.moveToFirst()) {
            do {
                UserAnswerBean userAnswerBean = new UserAnswerBean();
                userAnswerBean.setAnswerId(cursor.getInt(0));
                userAnswerBean.setQuestionId(cursor.getInt(1));
                userAnswerBeanList.add(userAnswerBean);
            } while (cursor.moveToNext());
        }

        return userAnswerBeanList;
    }


    public Observable uploadDataToServer(List<UserAnswerBean> userAnswerBeanList) {

        ServerRequestBean serverRequestBean = new ServerRequestBean();
        for (UserAnswerBean userAnswerBean : userAnswerBeanList) {
            if (userAnswerBean.getQuestionId() == 1) {
                serverRequestBean.setAnswer1(userAnswerBean.getAnswerId() + "");
            } else if (userAnswerBean.getQuestionId() == 2) {
                serverRequestBean.setAnswer2(userAnswerBean.getAnswerId() + "");
            } else if (userAnswerBean.getQuestionId() == 3) {
                serverRequestBean.setAnswer3(userAnswerBean.getAnswerId() + "");
            }
        }
        String jsonObj = new Gson().toJson(serverRequestBean);
        UserAnswerEndPoints userAnswerEndPoints = RestAPI.initializeRetrofit(mContext).create(UserAnswerEndPoints.class);

        return userAnswerEndPoints.uploadData(jsonObj);

    }

    public void deleteUploadedData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        databaseHelper.deleteUploadedData();
    }
}
