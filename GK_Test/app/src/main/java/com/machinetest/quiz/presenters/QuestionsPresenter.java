package com.machinetest.quiz.presenters;

import android.content.Context;

import com.machinetest.quiz.beans.Questions;
import com.machinetest.quiz.database.QuestionsDAO;
import com.machinetest.quiz.views.QuestionView;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public class QuestionsPresenter extends DefaultSubscriber<List<Questions>> {


    private Context mContext;

    private QuestionView view;
    List<Questions> questionsList;
    private Subscription subscription = Subscriptions.empty();

    public void setView(QuestionView view) {
        this.view = view;
    }

    public void fetchQuestions(Context context) {
        mContext = context;
        subscription = QuestionsDAO.getInstance(mContext)
                .getQuestionsList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        if (questionsList != null) {
            view.loadQuestions(questionsList);
        }
        subscription.unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }

    @Override
    public void onNext(List<Questions> questionses) {
        super.onNext(questionses);
        this.questionsList = questionses;
    }
}
