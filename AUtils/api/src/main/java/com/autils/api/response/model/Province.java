package com.autils.api.response.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public class Province extends Area implements Serializable {
    private String code;
    private String name;
    private List<Cities> cities;

    private String parentCode;

    @Override
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String getParentCode() {
        return "";
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
    public List<Cities> getAreas() {
        return cities;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }
}