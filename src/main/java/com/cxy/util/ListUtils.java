package com.cxy.util;

import com.google.common.base.Function;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * list集合工具类
 *
 * @author lichaofeng
 * @date 2020/5/23
 */
public class ListUtils {

    /**
     * List转Map
     *
     * @param tList
     * @param getKeyFunction
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getMapFromList(List<T> tList
            , Function<T, String> getKeyFunction) {
        if (CollectionUtils.isEmpty(tList)) {
            return null;
        }
        Map<String, T> tMap = new HashMap<>();
        for (T t : tList) {
            if (t == null) {
                continue;
            }
            String key = getKeyFunction.apply(t);
            if (key == null) {
                continue;
            }
            tMap.put(key, t);
        }
        return tMap;
    }

    /**
     * List转Map
     *
     * @param tList
     * @param getKeyFunction
     * @param <T>
     * @return
     */
    public static <T, S> Map<String, S> getMapFromList(List<T> tList
            , Function<T, String> getKeyFunction, Function<T, S> getValueFunction) {
        if (CollectionUtils.isEmpty(tList) || getKeyFunction == null || getValueFunction == null) {
            return null;
        }
        Map<String, S> tMap = new HashMap<>();
        for (T t : tList) {
            if (t == null) {
                continue;
            }
            S s = getValueFunction.apply(t);
            String key = getKeyFunction.apply(t);
            if (s != null && key != null) {
                tMap.put(key, s);
            }
        }
        return tMap;
    }

    /**
     * List 分组成Map
     *
     * @param tList
     * @param getKeyFunction
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<T>> getMapListFromList(List<T> tList, Function<T, String> getKeyFunction) {
        if (CollectionUtils.isEmpty(tList)) {
            return null;
        }
        Map<String, List<T>> tMap = new HashMap<>();
        for (T t : tList) {
            if (t == null) {
                continue;
            }
            String key = getKeyFunction.apply(t);
            if (key == null) {
                continue;
            }
            if (tMap.get(key) == null) {
                tMap.put(key, new ArrayList<T>());
            }
            tMap.get(key).add(t);
        }
        return tMap;
    }

    /**
     * List 分组成Map
     *
     * @param tList
     * @param getKeyFunction
     * @param <T>
     * @return
     */
    public static <T, S> Map<String, List<S>> getMapListFromList(List<T> tList, Function<T, String> getKeyFunction
            , Function<T, S> getValueFunction) {
        if (CollectionUtils.isEmpty(tList)) {
            return null;
        }
        Map<String, List<S>> tMap = new HashMap<>();
        for (T t : tList) {
            if (t == null) {
                continue;
            }
            String key = getKeyFunction.apply(t);
            S s = getValueFunction.apply(t);
            if (key == null || s == null) {
                continue;
            }
            if (tMap.get(key) == null) {
                tMap.put(key, new ArrayList<S>());
            }
            tMap.get(key).add(s);
        }
        return tMap;
    }

    /**
     * List 转换成新类型的List
     *
     * @param tList
     * @param transFunction
     * @param <>
     * @return
     */
    public static <T, S> List<S> transToTypeList(List<T> tList, Function<T, S> transFunction) {
        if (CollectionUtils.isEmpty(tList)) {
            return null;
        }
        List<S> sList = new ArrayList<>();
        for (T t : tList) {
            if (t == null) {
                continue;
            }
            S s = transFunction.apply(t);
            if (s == null) {
                continue;
            }
            sList.add(s);
        }
        return sList;
    }
    /**
     * List 转换成新类型的List
     *
     * @param tList
     * @param transFunction
     * @param <>
     * @return
     */
    public static <T, S> Set<S> transToTypeSet(List<T> tList, Function<T, S> transFunction) {
        if (CollectionUtils.isEmpty(tList)) {
            return null;
        }
        Set<S> sSet = new HashSet<>();
        for (T t : tList) {
            if (t == null) {
                continue;
            }
            S s = transFunction.apply(t);
            if (s == null) {
                continue;
            }
            sSet.add(s);
        }
        return sSet;
    }
}
