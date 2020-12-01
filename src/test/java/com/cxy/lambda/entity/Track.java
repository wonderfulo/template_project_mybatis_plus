package com.cxy.lambda.entity;

/**
 * @author ：陈翔宇
 * @date ：2020/11/30 17:40
 * @description：专辑中的一支曲目
 * @modified By：
 * @version: $
 */
public class Track {

    /**
     * 曲目名称
     */
    private String name;
    /**
     * 歌曲长度
     */
    private int length;


    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


}
