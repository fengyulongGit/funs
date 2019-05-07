package com.autils.api.response.model;

import java.io.Serializable;

/**
 * Created by fengyulong on 2018/5/15.
 */
public class AppVersion implements Serializable {

    private String name;
    private String os;
    private String version;
    private String last_version;
    private String force_update;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLast_version() {
        return last_version;
    }

    public void setLast_version(String last_version) {
        this.last_version = last_version;
    }

    public String getForce_update() {
        return force_update;
    }

    public void setForce_update(String force_update) {
        this.force_update = force_update;
    }
}
