package com.ywj.swiftbuy.model;

public class SuccessAPIResponse extends APIResponse {

    public SuccessAPIResponse() {
        super(CommonResponse.SUCCESS.getStatus(), CommonResponse.SUCCESS.getMessage());
    }

    public SuccessAPIResponse(String message) {
        super(CommonResponse.SUCCESS.getStatus(), message);
    }
}
