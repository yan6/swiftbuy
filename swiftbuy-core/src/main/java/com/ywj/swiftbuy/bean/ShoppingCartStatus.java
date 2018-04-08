package com.ywj.swiftbuy.bean;

public enum ShoppingCartStatus {

    ONLINE(0),
    DELETE(1),
    FINISH(3);//完成交易
    private final int value;

    public int getValue() {
        return value;
    }

    public static ShoppingCartStatus valueOf(int value) {
        for (ShoppingCartStatus status : ShoppingCartStatus.values()) {
            if (value == status.getValue()) {
                return status;
            }
        }
        return ONLINE;
    }

    ShoppingCartStatus(int value) {
        this.value = value;
    }
}
