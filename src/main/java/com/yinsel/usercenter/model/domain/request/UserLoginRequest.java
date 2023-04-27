package com.yinsel.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author yinsel
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5679854316998510038L;
    /**
     * 用户账户
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;
}
