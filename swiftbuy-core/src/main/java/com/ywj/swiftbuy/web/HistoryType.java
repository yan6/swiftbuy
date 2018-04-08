package com.ywj.swiftbuy.web;

public enum HistoryType {

    buy(0),
    view(1);

    private final int value;

    public int getValue() {
        return value;
    }

    public static HistoryType valueOf(int value) {
        for (HistoryType type : HistoryType.values()) {
            if (value == type.getValue()) {
                return type;
            }
        }
        return view;
    }

    HistoryType(int value) {
        this.value = value;
    }
}
