package com.ywj.swiftbuy.admin;


public enum UserType {
    DEFAULT("default"), NORMAL_USER("normal_user"), VIP_USER("vip_user"),BUSINESS("business");
    private final String value;

    public String getValue() {
        return value;
    }

    UserType(String value) {
        this.value = value;
    }
}
