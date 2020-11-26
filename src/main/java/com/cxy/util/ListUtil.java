//package com.cxy.util;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class ListUtil {
//
//    /**
//     * 第一种方法：
//     * 将list拆分成多给指定的大小的list
//     */
//    public static List<List<T>> subList(List<T> target, int size) {
//        List<List<T>> listArr = new ArrayList<List<T>>();
//        //获取被拆分的数组个数
//        int arrSize = target.size() % size == 0 ? target.size() / size : target.size() / size + 1;
//        for (int i = 0; i < arrSize; i++) {
//            List<T> sub = new ArrayList<T>();
//            //把指定索引数据放入到list中
//            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
//                if (j <= target.size() - 1) {
//                    sub.add(target.get(j));
//                }
//            }
//            listArr.add(sub);
//        }
//        return listArr;
//    }
//
//    /**
//     * 第一种方法：
//     * 将list拆分成多给指定的大小的list
//     */
//    public static List<List<T>> subSet(Set<T> target, int size) {
//        List<T> asList = new ArrayList<>(target);
//        return subList(asList,size);
//    }
//
//    public static String listToString(List list, char separator) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            sb.append(list.get(i)).append(separator);
//        }
//        return sb.toString().substring(0,sb.toString().length()-1);
//    }
//
//}
