package com.autils.api.response.model;

import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public class Districts extends Area {
    private String code;
    private String name;
    private String parentCode;

    @Override
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String getParentCode() {
        return parentCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public List<Area> getAreas() {
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
