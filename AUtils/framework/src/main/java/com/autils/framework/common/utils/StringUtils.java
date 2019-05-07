package com.autils.framework.common.utils;

/**
 * Created by fengyulong on 2018/6/4.
 */
public class StringUtils extends com.autils.api.utils.StringUtils {
    public static String convertNumericPhone(String str) {
        StringBuffer numericPhone = new StringBuffer();
        if (str != null) {
            str = str.trim();
            int sz = str.length();
            for (int i = 0; i < sz; i++) {
                char curentChar = str.charAt(i);
                if (Character.isDigit(curentChar)) {
                    numericPhone.append(curentChar);
                }
            }
        }
        return numericPhone.toString();
    }

    public static String convertWanStringToFenString(String strWan) {
        String result = "";
        int unit = 10000 * 100;
        if (isNotNullOrEmpty(strWan)) {
            try {
                result = "" + Integer.valueOf(strWan) * unit;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String convertFenStringToWanString(String strFen) {
        String result = "";
        int unit = 10000 * 100;
        if (isNotNullOrEmpty(strFen)) {
            try {
                result = "" + (int) (Float.valueOf(strFen) / unit);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String justFiltTime(String str) {
        String result = "";
        if (isNotNullOrEmpty(str)) {
            int dividerIndex = str.indexOf(":") > -1 ? str.indexOf(":") : 0;
            try {
                result = str.substring(0, dividerIndex + 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @return -1:str1<str2 0:str1=str2 1:str1>str2
     */
    public static int compareTo4Number(String str1, String str2) {
        if (isNullOrEmpty(str1)) {
            if (isNullOrEmpty(str2)) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (isNullOrEmpty(str2)) {
                return 1;
            } else {
                if (str1.equals(str2)) {
                    return 0;
                }

                String[] array1 = str1.split("\\.");
                String[] array2 = str2.split("\\.");

                for (int i = 0; i < array1.length; i++) {
                    if (i >= array2.length) {
                        return 1;
                    }

                    if (Long.parseLong(array1[i]) > Long.parseLong(array2[i])) {
                        return 1;
                    } else if (Long.parseLong(array1[i]) < Long.parseLong(array2[i])) {
                        return -1;
                    }
                }

                return -1;
            }
        }
    }
}
