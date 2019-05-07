package com.autils.api.response.model;

import java.util.List;

/**
 * Created by fengyulong on 2018/11/14.
 */
public class SellerInfo {

    private SysUserBean sysUser;
    private List<String> permissions;
    private List<String> roles;

    public SysUserBean getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserBean sysUser) {
        this.sysUser = sysUser;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static class SysUserBean {

        private String userId;
        private String username;
        private String realName;
        private String idcard;
        private String password;
        private String email;
        private String createTime;
        private String updateTime;
        private String delFlag;
        private String isOnDuty;
        private String phone;
        private String avatar;
        private String deptId;
        private String wxOpenid;
        private String qqOpenid;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getIsOnDuty() {
            return isOnDuty;
        }

        public void setIsOnDuty(String isOnDuty) {
            this.isOnDuty = isOnDuty;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getWxOpenid() {
            return wxOpenid;
        }

        public void setWxOpenid(String wxOpenid) {
            this.wxOpenid = wxOpenid;
        }

        public String getQqOpenid() {
            return qqOpenid;
        }

        public void setQqOpenid(String qqOpenid) {
            this.qqOpenid = qqOpenid;
        }
    }
}
