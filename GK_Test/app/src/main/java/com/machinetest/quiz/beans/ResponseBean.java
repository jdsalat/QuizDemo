package com.machinetest.quiz.beans;

import com.machinetest.quiz.utils.Constants;

/**
 * Created by Javed.Salat on 8/9/2017.
 */

public class ResponseBean {

    private String callFrom = Constants.CALL_UPLOAD;
    private boolean status = true;

    private String response;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCallFrom() {
        return callFrom;
    }

    public void setCallFrom(String callFrom) {
        this.callFrom = callFrom;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
