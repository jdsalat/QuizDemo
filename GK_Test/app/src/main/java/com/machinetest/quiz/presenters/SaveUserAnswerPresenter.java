package com.machinetest.quiz.presenters;

import android.content.Context;

import com.machinetest.quiz.beans.ResponseBean;
import com.machinetest.quiz.beans.UserAnswerBean;
import com.machinetest.quiz.database.QuestionsDAO;
import com.machinetest.quiz.views.QuestionView;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Javed.Salat on 8/8/2017.
 */

public class SaveUserAnswerPresenter extends DefaultSubscriber<ResponseBean> {


    private Context mContext;

    private QuestionView view;
    ResponseBean responseBean;
    private Subscription subscription = Subscriptions.empty();

    public void setView(QuestionView view) {
        this.view = view;
    }

    public void saveAnswers(Context context, List<UserAnswerBean> userAnswerBeanList) {
        mContext = context;
        subscription = QuestionsDAO.getInstance(mContext)
                .saveDataToDatabase(userAnswerBeanList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

    }


    public void uploadDataToServer(Context context, List<UserAnswerBean> userAnswerBeanList) {
        mContext = context;
        subscription = QuestionsDAO.getInstance(mContext)
                .uploadDataToServer(userAnswerBeanList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        if (responseBean.isStatus() == true) {
            view.onSaveSuccess(responseBean);
        }
        subscription.unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }

    @Override
    public void onNext(ResponseBean responseBean) {
        super.onNext(responseBean);
        this.responseBean = responseBean;
    }
}
