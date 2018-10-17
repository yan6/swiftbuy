package com.ywj.swiftbuy.testbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BriefCard {
    private long id;
    private String icon;
    private boolean ifPgc;//v3.10是否显示作者图标
}
