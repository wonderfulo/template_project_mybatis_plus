package com.cxy.assert_package;


import com.cxy.constant.exception.DisplayTaskEnumException;
import com.cxy.constant.exception.IEnumException;
import com.cxy.exception.ServiceException;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName AssertEx.java
 * @Description 断言类
 * @createTime 2021年05月11日 10:55:00
 */
public class AssertEx {

    public static <Ex extends IEnumException> void isNotEmpty(String param, Ex ex) {
        Optional.ofNullable(param)
                .filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new ServiceException(buildExCode(ex),buildExMessage(ex)));
    }

    public static <Ex extends IEnumException> void isNotEmpty(Long param, Ex ex) {
        Optional.ofNullable(param)
                .filter(x -> x != 0L)
                .orElseThrow(() -> new ServiceException(buildExCode(ex),buildExMessage(ex)));
    }


    public static <T, Ex extends IEnumException> void isNotEmpty(List<T> paramList, Ex ex) {
        Optional.ofNullable(paramList)
                .filter(CollectionUtils::isNotEmpty)
                .orElseThrow(() -> new ServiceException(buildExCode(ex),buildExMessage(ex)));
    }

    public static <Ex extends IEnumException> void isNotNull(Object param, Ex ex) {
        Optional.ofNullable(param)
                .orElseThrow(() -> new ServiceException(buildExCode(ex),buildExMessage(ex)));
    }

    public static <Ex extends IEnumException> void isTrue(Boolean param, Ex ex) {
        Optional.ofNullable(param)
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> new ServiceException(buildExCode(ex),buildExMessage(ex)));
    }

    public static <Ex extends IEnumException> String buildExMessage(Ex ex) {
        return Optional.ofNullable(ex)
                .map(e -> ex.getMessage())
                .orElse("");
    }

    public static <Ex extends IEnumException> int buildExCode(Ex ex) {
        return Optional.ofNullable(ex)
                .map(e -> ex.getCode())
                .orElse(0);
    }

    public static void main(String[] args) {
        List<String> arrayList = Lists.newArrayList();
        AssertEx.isNotEmpty(arrayList, DisplayTaskEnumException.ID_NOT_EXIST);
    }

}
