package com.cxy.lambda.entity;

import java.util.List;

/**
 * @author ：陈翔宇
 * @date ：2020/11/30 18:14
 * @description：专辑，由若干曲目组成
 * @modified By：
 * @version: $
 */
public class Album {

    /**
     * 专辑名
     */
    private String name;
    /**
     * 专辑上所有曲目列表
     */
    private List<Track> trackList;

    /**
     * 参与创作本专辑的艺术家列表
     */
    private List<Artist> musicianList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public List<Artist> getMusicianList() {
        return musicianList;
    }

    public void setMusicianList(List<Artist> musicianList) {
        this.musicianList = musicianList;
    }
}
