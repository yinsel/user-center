package com.yinsel.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本响应对象
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 8189136286720021602L;
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 状态信息
     */
    private String message;
    /**
     * 状态描述（详情）
     */
    private String description;

    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code,data,message,"");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null, errorCode.getMessage(), errorCode.getDescription());
    }
}
