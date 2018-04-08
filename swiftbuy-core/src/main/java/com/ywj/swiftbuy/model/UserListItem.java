package com.ywj.swiftbuy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ywj.swiftbuy.web.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 用户列表，做分页使用
 */
@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListItem {
    private List<User> users;
    private String nextPageUrl;
}
