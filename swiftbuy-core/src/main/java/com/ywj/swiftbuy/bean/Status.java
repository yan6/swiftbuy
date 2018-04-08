package com.ywj.swiftbuy.bean;

public enum Status {
    deleted(-1),
    ready(0),
    online(1),
    finished(2);

    private final int value;

    public int getValue() {
        return value;
    }

    public static Status valueOf(int value) {
        for (Status status : Status.values()) {
            if (value == status.getValue()) {
                return status;
            }
        }
        return ready;
    }

    Status(int value) {
        this.value = value;
    }
}
