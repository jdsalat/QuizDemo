package com.machinetest.quiz.network.endpoints;

import com.machinetest.quiz.beans.ResponseBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Javed.Salat on 8/9/2017.
 */

public interface
UserAnswerEndPoints {

    @POST("question_data")
    Observable<ResponseBean> uploadData(@Query("data") String data);

}
