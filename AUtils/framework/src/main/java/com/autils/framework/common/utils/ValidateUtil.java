package com.autils.framework.common.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by fengyulong on 2018/5/10.
 */
public class ValidateUtil {
    // 仅支持英文数字下划线中划线@英文句号
    private static final String RULE_USERNAME = "[0-9a-zA-Z\\._\\-@]*";
    private static final String RULE_POSTALCODE_SIMPLE = "^[0-9]{6}$";
    private static final String RULE_AUTHCODE_SIMPLE = "^[0-9]*$";
    private static final String RULE_AUTHCODE_SIMPLE_FOUR = "^[0-9]{4}$";
    private static final String RULE_MOBILE_NUMBER_SIMPLE = "^1[0-9]{10}$";

    /**
     * 判断8-15位字母或数据的合法密码
     */
    public static boolean checkPassword(String passWord) {
        if (TextUtils.isEmpty(passWord)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-z|A-Z|0-9]{8,15}$");

        return pattern.matcher(passWord).matches();
    }

    /**
     * 判断是否为移动手机号
     */
    public static boolean checkMobilePhone(String phone) {
        /*
         * 匹配移动手机号 "^1(3[4-9]|5[012789]|8[78])\d{8}$"以代码为准
         *
         * 匹配电信手机号 "^18[09]\d{8}$"
         *
         * 匹配联通手机号"^1(3[0-2]|5[56]|8[56])\d{8}$"
         *
         * 匹配CDMA手机号 "^1[35]3\d{8}$"
         */
        Pattern pattern = Pattern.compile("^1((3[5-9]|5[012789]|8[278])\\d{8})|(134[0-8]\\d{7})$");

        return pattern.matcher(phone).matches();
    }

    /**
     * 简单校验手机号（规则：长度为11，首位为1，仅数字）
     */
    public static boolean isValidMobileNumber(String mobile) {
        return isValidString(mobile, RULE_MOBILE_NUMBER_SIMPLE);
    }

    /**
     * 简单校验手机号（规则：长度<=15，仅数字）
     */
    public static boolean isValidMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        if (!mobile.matches(RULE_AUTHCODE_SIMPLE)) {
            return false;
        }

        if (mobile.length() < 11 || mobile.length() > 15) {
            return false;
        }
        return true;
    }

    /**
     * 简单校验邮政编码（规则：长度为6，仅数字）
     */
    public static boolean isValidPostalCode(String postalCode) {
        return isValidString(postalCode, RULE_POSTALCODE_SIMPLE);
    }

    public static boolean isValidString(String str, String rule) {
        if (str == null || "" == str) {
            return false;
        }
        if (!str.matches(rule)) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isPositiveNumber(String value) {
        if (!TextUtils.isEmpty(value)) {
            return false;
        }
        return isPositiveInteger(value) || isPositiveDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isPositiveInteger(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isPositiveDouble(String value) {
        try {
            double number = java.lang.Double.parseDouble(value);
            if (value.contains(".") && number > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 检查中文名输 入是否正确
     *
     * @return
     */
    public static boolean checkChineseName(String value) {
        return null != value && value.matches("^[\\u4e00-\\u9fa5]{0,}$") && value.length() >= 2;
    }

    /**
     * 验证固定电话号码
     * 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * *国家（地区） 代码 ：**标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     * 数字之后是空格分隔的国家（地区）代码。
     * *区号（城市代码）：**这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。
     * *电话号码：**这包含从 0 到 9 的一个或多个数字
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }
}
