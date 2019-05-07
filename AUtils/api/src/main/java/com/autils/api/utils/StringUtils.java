package com.autils.api.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.hutool.core.codec.Base64;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by jisuyun on 2017/8/29.
 */

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return null == str || str.trim().isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    private static final String TRANSFORMATION = "AES/CBC/NOPadding";
    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_CHARSET = "utf-8";
    /**
     * 建议为16位或32位
     */
    private static final String KEY = "pigxpigxpigxpigx";
    /**
     * 必须16位
     */
    private static final String IV = "pigxpigxpigxpigx";

    /**
     * 加密
     *
     * @param data
     */
    public static String aesEncrypt(String data) {
        byte[] iv = IV.getBytes();
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int length = dataBytes.length;
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param data
     */
    public static String aesDecrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] result = cipher.doFinal(Base64.decode(data.getBytes(DEFAULT_CHARSET)));
            return new String(result, DEFAULT_CHARSET).replace("\0", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5(String str) {
        String resultString = null;
        try {
            resultString = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String byteToArrayString(byte bByte) {
        String[] strDigits = {"0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    public static TreeMap<String, String> splitUrlQuery(String originString) {
        TreeMap<String, String> query_pairs = new TreeMap<>();

        if (isNullOrEmpty(originString)) {
            return query_pairs;
        }

        int questIndex = originString.indexOf('?');
        if (questIndex == -1 || questIndex == originString.length() - 1) {
            return query_pairs;
        }

        return splitQuery(originString.substring(questIndex + 1, originString.length()));
    }

    public static TreeMap<String, String> splitQuery(String originString) {

        TreeMap<String, String> query_pairs = new TreeMap<>();

        if (isNullOrEmpty(originString)) {
            return query_pairs;
        }

        String[] pairs = originString.split("&");
        try {
            for (String pair : pairs) {
                int idx = pair.indexOf("=");

                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        return query_pairs;
    }

    public static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
