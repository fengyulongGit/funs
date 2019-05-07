package com.autils.common.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class AsyncNetUtils {
    private static String METHOD_POST = "POST";
    private static String METHOD_GET = "GET";

    public static void get(final String url, final Map<String, String> params, final CallBack callBack) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {
                return openUrl(url, METHOD_GET, params);
            }

            @Override
            protected void onPostExecute(String s) {
                callBack.callback(s);
            }
        }.execute("");
    }

    public static void post(final String url, final Map<String, String> params, final CallBack callBack) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {
                return openUrl(url, METHOD_POST, params);
            }

            @Override
            protected void onPostExecute(String s) {
                callBack.callback(s);
            }
        }.execute("");
    }

    private static String openUrl(String url, String method, Map<String, String> params) {

        String response = "";
        try {
            String paramsString = params2String(params);
            if (method.equalsIgnoreCase(METHOD_GET)) {
                if (!url.endsWith("?")) {
                    url += "?";
                }
                url += paramsString;
            }
            System.out.println(method + " URL: " + url);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(method);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            if (!method.equals(METHOD_GET)) {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                System.out.println(method + " PARAMS: " + paramsString);
                conn.getOutputStream().write(paramsString.getBytes("UTF-8"));
            }

            InputStream is = null;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }
            response = read(is);

            System.out.println("result is " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static String params2String(Map<String, String> parameters) {
        if (parameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String value = parameters.get(key == null ? "" : key);
            // sb.append(key + "=" + URLEncoder.encode(value == null ? "" :
            // value));
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

    private static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (null != in) {
            BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
        }
        return sb.toString();
    }

    public interface CallBack {
        void callback(String response);
    }

    public static long getRemoteFileSzie(String url) {
        long size = 0;
        try {
            HttpURLConnection httpUrl = (HttpURLConnection) (new URL(url)).openConnection();
            size = httpUrl.getContentLength();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
