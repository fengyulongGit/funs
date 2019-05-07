package com.autils.api.response.model;

import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public class Cities extends Area {
    private String code;
    private String name;
    private String parentCode;
    private List<Districts> districts;

    @Override
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String getParentCode() {
        return parentCode;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public List<Districts> getAreas() {
        return districts;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistricts(List<Districts> districts) {
        this.districts = districts;
    }
}
