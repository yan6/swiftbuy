package com.ywj.swiftbuy.model;

public enum CommonResponse {
    SUCCESS(200, "成功"),
    FAILURE(-1, "失败"),
    NOT_LOGGED_IN(-2, "请先登录账号");

    private int status;
    private String message;

    CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
