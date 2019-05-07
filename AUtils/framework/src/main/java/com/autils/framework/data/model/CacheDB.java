package com.autils.framework.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengyulong on 2018/7/13.
 */
@Entity
public class CacheDB {
    public interface TYPE {
        String TOKEN = "token";
        String USERINFO = "userinfo";
        String COMPANY_IMAGE = "company_image";
    }

    private String user_key = "";
    private String type = "";
    private String content = "";

    @Generated(hash = 1604917143)
    public CacheDB(String user_key, String type, String content) {
        this.user_key = user_key;
        this.type = type;
        this.content = content;
    }

    @Generated(hash = 773821969)
    public CacheDB() {
    }

    public String getUser_key() {
        return this.user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
