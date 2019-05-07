package com.autils.api.response.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/8/2.
 */
public class UserInfo {
    public interface STEP_KEY {
        String AIDE_PAGE = "AIDE_PAGE";
        String COMPANY_IMG_PAGE = "COMPANY_IMG_PAGE";

        String LICENSE_CERT_IMAGE = "LICENSE_CERT_IMAGE";//营业执照
        String INDUSTRY_CERT_IMAGE = "INDUSTRY_CERT_IMAGE";//行业许可证
        String SITE_CONTRACT_IMAGE = "SITE_CONTRACT_IMAGE";//场地租赁合同
        String COMPANY_IMAGE = "COMPANY_IMAGE";//公司照片
        String LEAGUE_IMAGE = "LEAGUE_IMAGE";//加盟合同
        String INDOOR_IMAGE = "INDOOR_IMAGE";//店内全角照片
        String OTHER_IMAGE = "OTHER_IMAGE";//其他证明
    }

    private String choose_channel;
    private String step;
    private List<Step> step_list;
    private String mobile;
    private String partner;
    private String channel;

    public String getChoose_channel() {
        return choose_channel;
    }

    public void setChoose_channel(String choose_channel) {
        this.choose_channel = choose_channel;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<Step> getStep_list() {
        return step_list != null ? step_list : new ArrayList<Step>();
    }

    public void setStep_list(List<Step> step_list) {
        this.step_list = step_list;
    }

    public static class Step {

        private String step;
        private String attr;
        private String type;
        private boolean finish;
        private List<StepList> list;

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public List<StepList> getList() {
            return list != null ? list : new ArrayList<StepList>();
        }

        public void setList(List<StepList> list) {
            this.list = list;
        }

        public static class StepList {
            private String step;
            private String attr;
            private String type;
            private boolean finish;
            private List<ListBean> list;

            public String getStep() {
                return step;
            }

            public void setStep(String step) {
                this.step = step;
            }

            public String getAttr() {
                return attr;
            }

            public void setAttr(String attr) {
                this.attr = attr;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isFinish() {
                return finish;
            }

            public void setFinish(boolean finish) {
                this.finish = finish;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private String step;
                private String attr;
                private String type;
                private boolean finish;

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }

                public String getAttr() {
                    return attr;
                }

                public void setAttr(String attr) {
                    this.attr = attr;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public boolean isFinish() {
                    return finish;
                }

                public void setFinish(boolean finish) {
                    this.finish = finish;
                }
            }
        }
    }
}
