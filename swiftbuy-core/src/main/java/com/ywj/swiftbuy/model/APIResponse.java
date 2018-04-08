package com.ywj.swiftbuy.model;

import com.ywj.swiftbuy.bean.CustomizedException;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class APIResponse {

    private int resultCode;
    private String resultMessage;

    public APIResponse() {}

    public APIResponse(int errorCode, String errorMessage) {
        this.resultCode = errorCode;
        this.resultMessage = errorMessage;
    }

    public APIResponse(CustomizedException e) {
        this.resultCode = e.getErrorCode();
        this.resultMessage = e.getErrorMessage();
    }
}
