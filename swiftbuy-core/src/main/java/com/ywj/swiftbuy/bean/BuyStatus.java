package com.ywj.swiftbuy.bean;

public enum BuyStatus {
    failure(0),
    success(1);

    private final int value;

    public int getValue() {
        return value;
    }

    public static BuyStatus valueOf(int value) {
        for (BuyStatus status : BuyStatus.values()) {
            if (value == status.getValue()) {
                return status;
            }
        }
        return failure;
    }

    BuyStatus(int value) {
        this.value = value;
    }
}
