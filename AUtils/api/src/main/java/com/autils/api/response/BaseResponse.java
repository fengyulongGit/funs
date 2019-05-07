package com.autils.api.response;

/**
 * Created by jisuyun on 2017/4/18.
 */

public class BaseResponse<T> {
    public static boolean isSuccess(int code) {
        return 0 == code;
    }

    public static boolean isRefreshToken(int code) {
        return CODE_TOKEN == code;
    }

    public static boolean isLogin(int code) {
        return CODE_TOKEN == code;
    }

    public static final int CODE_TOKEN = -1040;

    public int code; //返回状态码
    public String desc; //返回说明
    public T data; //返回数据 接口没有给示例的一般为空对象
}
