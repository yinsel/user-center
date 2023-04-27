package com.yinsel.usercenter.common;

public class ResultUtlis {
    /**
     * 成功
     * @param result
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<T>(0,result,"ok");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     * @param
     */

    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode,String message,String description) {
        return new BaseResponse<>(errorCode.getCode(),errorCode.getMessage(),description);
    }

    public static BaseResponse error(ErrorCode errorCode,String description) {
        return new BaseResponse<>(errorCode.getCode(),errorCode.getMessage(),description);
    }

    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code,null,message,description);
    }
}
