package com.util;

public class ResponseWrapper<T>{
    private String message;
    private T data;

    public ResponseWrapper(String message, T data) {
        this.setMessage(message);
        this.setData(data);
    }
    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "message='" + getMessage() + '\'' +
                ", data=" + getData() +
                '}';
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

}
