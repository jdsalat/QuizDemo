package com.machinetest.quiz.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.machinetest.quiz.beans.UserAnswerBean;
import com.machinetest.quiz.database.QuestionsDAO;

import java.util.List;

/**
 * Created by Javed.Salat on 8/9/2017.
 */

public class NetworkCheckReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int connectionStatus = getConnectivityStatusString(context);

        if (connectionStatus == TYPE_MOBILE || connectionStatus == TYPE_WIFI) {
            QuestionsDAO questionsDAO = QuestionsDAO.getInstance(context);
            List<UserAnswerBean> userAnswerBeanList = questionsDAO.getAllUserAnswerBean();

            if (!userAnswerBeanList.isEmpty()) {
                Log.e("", "Inside Receiver.");
                questionsDAO.uploadDataToServer(userAnswerBeanList);
                questionsDAO.deleteUploadedData();
            }
        }
    }


    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;
    public int TYPE_NOT_CONNECTED = 0;

    public int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public int getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI) {
            status = TYPE_WIFI;
        } else if (conn == TYPE_MOBILE) {
            status = TYPE_MOBILE;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = TYPE_NOT_CONNECTED;
        }
        return status;
    }

}
