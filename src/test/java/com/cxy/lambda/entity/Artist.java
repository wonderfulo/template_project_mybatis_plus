package com.cxy.lambda.entity;

/**
 * @author ：陈翔宇
 * @date ：2020/11/30 18:13
 * @description：创作音乐的个人或团队
 * @modified By：
 * @version: $
 */
public class Artist {
    private String name;
    private String members;
    /**
     * 乐队来自哪里
     */
    private String nationality;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
