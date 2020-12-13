package com.cxy.entity.user_info;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName InsightUserInfo.java
 * @Description 用户信息vo
 * @createTime 2020年12月13日 12:33:00
 */
public class UserInfo {

    private String userId;

    private String appId;

    private String openId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
