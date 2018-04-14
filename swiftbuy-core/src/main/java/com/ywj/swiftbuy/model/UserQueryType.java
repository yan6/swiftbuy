package com.ywj.swiftbuy.model;


public enum UserQueryType {
    DEFAULT(-1),
    UID(0),
    USER_NAME(1),
    REGISTER(2),
    ADDRESS(3),
    GOODS_NAME(4),
    GOODS_TYPE(5),
    BUSINESS_NAME(6);
    private final int value;

    public int getValue() {
        return value;
    }

    public static UserQueryType valueOf(int value) {
        for (UserQueryType type : UserQueryType.values()) {
            if (value == type.getValue()) {
                return type;
            }
        }
        return DEFAULT;
    }

    UserQueryType(int value) {
        this.value = value;
    }
}
