package com.ywj.swiftbuy.web;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.model.JacksonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * @author ywj
 */

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialVersionUID = 3179258617487190616L;
    @JsonIgnore
    private int uid;
    @JsonView(JacksonViews.Admin.class)
    private String username;
    @JsonIgnore
    private String nickname;
    @JsonView(JacksonViews.Admin.class)
    private String password;
    @JsonView(JacksonViews.Admin.class)
    private String phone;
    @JsonView(JacksonViews.Admin.class)
    private String code;  //验证码
    private String userType;
    private String gender;
    private Date registDate;
    private String description;
    private String cover;
    private Date releaseDate;
    private Date updateDate;
    private int addressId;
    private String address;  //省-市-县


    public User(String username, String password, String phone) {
        this.username = username;
        this.nickname = username;
        this.password = password;//进行md5加密处理下
        this.phone = phone;
        this.registDate = new Date();
        return;
    }
}
