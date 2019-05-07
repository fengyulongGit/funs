package com.autils.api.utils;

import java.io.File;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jisuyun on 2017/8/29.
 */

public class FileUtils {

    public static Headers fileToHeader(File file, String key) {
        String fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
        return new Headers.Builder()
                .add("Content-Transfer-Encoding", "binary")
                .add("Content-Disposition", "form-data; name=\"" + key + "\"; filename=\"" + key + fileSuffix + "\"")
                .build();
    }

    public static RequestBody fileToRequestBody(File file) {
        return RequestBody.create(MediaType.parse("application/octet-stream"), file);
    }
}
