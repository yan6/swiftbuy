package com.ywj.swiftbuy.model;


public class FailureAPIResponse extends APIResponse {

    public FailureAPIResponse() {
        super(CommonResponse.FAILURE.getStatus(), CommonResponse.FAILURE.getMessage());
    }

    public FailureAPIResponse(String errorMessage) {
        super(CommonResponse.FAILURE.getStatus(), errorMessage);
    }
}
