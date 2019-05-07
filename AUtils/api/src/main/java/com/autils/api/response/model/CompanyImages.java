package com.autils.api.response.model;


import com.autils.api.utils.StringUtils;

import java.util.List;

/**
 * Created by fengyulong on 2018/7/31.
 */
public class CompanyImages {
    public enum Type {

        LICENSE_CERT_IMAGE("license_cert_image"), // 营业执照照片
        INDUSTRY_CERT_IMAGE("industry_cert_image"), // 行业许可证
        SITE_CONTRACT_IMAGE("site_contract_image"), // 最新场地租赁合同图片
        COMPANY_IMAGE("company_image"), // 公司照片 门头+厂房全景+经营场所内部照片
        LEAGUE_IMAGE("league_image"), // 加盟合同照片
        INDOOR_IMAGE("indoor_image"), // 店内全角照片
        OTHER_IMAGE("other_image"), // 其他证明
        ;

        Type(String type) {
            this.type = type;
        }

        private String type;

        public String getType() {
            return type;
        }
    }

    private List<String> license_cert_image; // 营业执照照片
    private List<String> industry_cert_image; // 行业许可证
    private List<String> site_contract_image; // 最新场地租赁合同图片
    private List<String> company_image; // 公司照片 门头+厂房全景+经营场所内部照片
    private List<String> league_image; // 加盟合同照片
    private List<String> indoor_image; // 店内全角照片
    private List<String> other_image; // 其他证明


    public String getLicense_cert_image() {
        String result = "";
        if (license_cert_image == null) {
            return result;
        }
        for (String str : license_cert_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setLicense_cert_image(List<String> license_cert_image) {
        this.license_cert_image = license_cert_image;
    }

    public String getIndustry_cert_image() {
        String result = "";
        if (industry_cert_image == null) {
            return result;
        }
        for (String str : industry_cert_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setIndustry_cert_image(List<String> industry_cert_image) {
        this.industry_cert_image = industry_cert_image;
    }

    public String getSite_contract_image() {
        String result = "";
        if (site_contract_image == null) {
            return result;
        }
        for (String str : site_contract_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setSite_contract_image(List<String> site_contract_image) {
        this.site_contract_image = site_contract_image;
    }

    public String getCompany_image() {
        String result = "";
        if (company_image == null) {
            return result;
        }
        for (String str : company_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setCompany_image(List<String> company_image) {
        this.company_image = company_image;
    }

    public String getLeague_image() {
        String result = "";
        if (league_image == null) {
            return result;
        }
        for (String str : league_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setLeague_image(List<String> league_image) {
        this.league_image = league_image;
    }

    public String getIndoor_image() {
        String result = "";
        if (indoor_image == null) {
            return result;
        }
        for (String str : indoor_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setIndoor_image(List<String> indoor_image) {
        this.indoor_image = indoor_image;
    }

    public String getOther_image() {
        String result = "";
        if (other_image == null) {
            return result;
        }
        for (String str : other_image) {
            if (StringUtils.isNotNullOrEmpty(str)) {
                if (StringUtils.isNotNullOrEmpty(result)) {
                    result += ",";
                }
                result += str;
            }
        }
        return result;
    }

    public void setOther_image(List<String> other_image) {
        this.other_image = other_image;
    }
}
