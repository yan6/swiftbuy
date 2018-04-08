package com.ywj.swiftbuy.common.model;

/**
 * @author ywj
 */

public class APIResponse {
    private int errorCode;
    private String errorMessage;

    public APIResponse() {
    }

    public APIResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
