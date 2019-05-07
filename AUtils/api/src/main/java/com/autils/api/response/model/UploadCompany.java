package com.autils.api.response.model;

/**
 * Created by fengyulong on 2018/7/31.
 */
public class UploadCompany {
    private String license_cert_image; // 营业执照照片
    private String industry_cert_image; // 行业许可证
    private String site_contract_image; // 最新场地租赁合同图片
    private String company_image; // 公司照片 门头+厂房全景+经营场所内部照片
    private String league_image; // 加盟合同照片
    private String indoor_image; // 店内全角照片
    private String other_image; // 其他证明

    public String getLicense_cert_image() {
        return license_cert_image;
    }

    public String getIndustry_cert_image() {
        return industry_cert_image;
    }

    public String getSite_contract_image() {
        return site_contract_image;
    }

    public String getCompany_image() {
        return company_image;
    }

    public String getLeague_image() {
        return league_image;
    }

    public String getIndoor_image() {
        return indoor_image;
    }

    public String getOther_image() {
        return other_image;
    }
}
