package com.autils.api.response.model;

import java.io.Serializable;

/**
 * Created by fengyulong on 2018/6/20.
 */
public class UserDetail implements Serializable {

    private String id;
    private String user_id;
    private String name;
    private String mobile;
    private String idcard;
    private String gender;
    private String age;
    private String home_province;
    private String home_city;
    private String home_district;
    private String home_address;
    private String work_province;
    private String work_city;
    private String work_district;
    private String work_address;
    private String idcard_front_image;
    private String idcard_back_image;
    private String idcard_headshot_image;
    private String contact;
    private String status;
    private String create_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHome_province() {
        return home_province;
    }

    public void setHome_province(String home_province) {
        this.home_province = home_province;
    }

    public String getHome_city() {
        return home_city;
    }

    public void setHome_city(String home_city) {
        this.home_city = home_city;
    }

    public String getHome_district() {
        return home_district;
    }

    public void setHome_district(String home_district) {
        this.home_district = home_district;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getWork_province() {
        return work_province;
    }

    public void setWork_province(String work_province) {
        this.work_province = work_province;
    }

    public String getWork_city() {
        return work_city;
    }

    public void setWork_city(String work_city) {
        this.work_city = work_city;
    }

    public String getWork_district() {
        return work_district;
    }

    public void setWork_district(String work_district) {
        this.work_district = work_district;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    public String getIdcard_front_image() {
        return idcard_front_image;
    }

    public void setIdcard_front_image(String idcard_front_image) {
        this.idcard_front_image = idcard_front_image;
    }

    public String getIdcard_back_image() {
        return idcard_back_image;
    }

    public void setIdcard_back_image(String idcard_back_image) {
        this.idcard_back_image = idcard_back_image;
    }

    public String getIdcard_headshot_image() {
        return idcard_headshot_image;
    }

    public void setIdcard_headshot_image(String idcard_headshot_image) {
        this.idcard_headshot_image = idcard_headshot_image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
