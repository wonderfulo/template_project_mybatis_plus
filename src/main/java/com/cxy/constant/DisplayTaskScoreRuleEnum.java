package com.cxy.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @Description 陈列任务规则枚举
 * @createTime 2021年07月05日 11:39:00
 */
public enum DisplayTaskScoreRuleEnum {
    ADD("add"),
    MINUS("minus");

    /**
     * 得分规则
     */
    private String scoreRule;

    DisplayTaskScoreRuleEnum(String scoreRule){
        this.scoreRule = scoreRule;
    }

    public String getScoreRule() {
        return scoreRule;
    }

    public static boolean isExist(String scoreRule){
        if (StringUtils.isNotEmpty(scoreRule)){
            return false;
        }
        for (DisplayTaskScoreRuleEnum item: DisplayTaskScoreRuleEnum.values()) {
            if (item.get(scoreRule) != null){
                return true;
            }
        }
        return false;
    }

    public static DisplayTaskScoreRuleEnum get(String scoreRule){
        if (StringUtils.isNotEmpty(scoreRule)){
            return  null;
        }
        for (DisplayTaskScoreRuleEnum item: DisplayTaskScoreRuleEnum.values()) {
            if (item.get(scoreRule) != null){
                return item.get(scoreRule);
            }
        }
        return null;
    }

}
