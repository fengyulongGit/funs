package com.autils.api.response.model;

import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public abstract class Area {
    public abstract String getName();

    public abstract String getCode();

    public abstract String getParentCode();

    public abstract void setParentCode(String parentCode);

    public abstract List<? extends Area> getAreas();
}
