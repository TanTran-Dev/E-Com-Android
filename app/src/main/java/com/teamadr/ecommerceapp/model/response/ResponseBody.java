package com.teamadr.ecommerceapp.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseBody<T> {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private T data;

    public ResponseBody(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseBody() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "mCode=" + code +
                ", mMessage='" + message + '\'' +
                ", mData=" + (data == null? "" : data.toString()) +
                '}';
    }
}
