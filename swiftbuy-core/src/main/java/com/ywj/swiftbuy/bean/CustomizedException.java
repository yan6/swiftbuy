package com.ywj.swiftbuy.bean;

public abstract class CustomizedException extends RuntimeException {

    public abstract int getErrorCode();

    public abstract String getErrorMessage();
}
