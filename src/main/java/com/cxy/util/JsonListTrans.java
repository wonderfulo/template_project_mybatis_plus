//package com.cxy.util;
//import java.util.ArrayList;
///*
// * @author JasonLi*/
//import java.util.List;
//
//import net.sf.json.JSONArray;
//
//
//public class JsonListTrans{
//	/**
//     * 将一个json字串转为list
//     * @param props
//     * @return
//     */
//    public static List converAnswerFormString(String props){
//        if (props == null || props.equals(""))
//            return new ArrayList();
//
//        JSONArray jsonArray = JSONArray.fromObject(props);
//        List list = (List) JSONArray.toCollection(jsonArray);
//
//        return list;
//    }
//}
//