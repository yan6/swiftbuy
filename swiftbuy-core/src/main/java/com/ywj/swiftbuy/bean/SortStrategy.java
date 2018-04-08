package com.ywj.swiftbuy.bean;


/**
 * @author ywj
 * 排序策略
 */
public enum SortStrategy {
    date(0),            /* 时间*/
    price(1),           /* 价格 */
    soldCount(2),       /* 已售出数量   */
    range(3);           /* 距离商家范围远近    */

    private final int value;

    public int getValue() {
        return value;
    }

    public static SortStrategy valueOf(int value) {
        for (SortStrategy sortStrategy : SortStrategy.values()) {
            if (value == sortStrategy.getValue()) {
                return sortStrategy;
            }
        }
        return date;
    }

    SortStrategy(int value) {
        this.value = value;
    }
}
