package com.cxy.service.impl;

import com.alibaba.excel.util.CollectionUtils;
import com.cxy.utils.BaseUtil;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName BaseServiceImpl.java
 * @Description 基础服务实现
 * @createTime 2020年12月17日 17:24:00
 */
public class BaseServiceImpl {


//    @Override
//    public void batchSave(List<T> objectList) throws IllegalAccessException {
//        /**
//         * 比如：
//         * SQL 语句：insert into person(name, age, info) values ('...', '...', '...');
//         * 其中 values 改为占位符：
//         * SQL 语句：insert into person(name, age, info) values (?, ?, ?);
//         * 再获取所有的数据值 Object 数组
//         * [zhengbin, 21, 要毕业了]
//         */
//        // 确定占位符的个数（即对象中不为 null 的字段个数）
//        int columnNum = 0;
//        // 插入数据的列名
//        StringBuilder columns = new StringBuilder("(");
//        // 填充占位符的值（即对象中不为null的字段的值）
//        List<Object> valuesList = new ArrayList<Object>();
//        // 如果为空则不执行
//        if (CollectionUtils.isEmpty(objectList)) {
//            return;
//        }
//        // 通过 List 中的第一个 Object，确定插入对象的字段
//        Object object = objectList.get(0);
//        Class clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            if (field.get(object) != null) {
//                columnNum++;
//                String fieldName = field.getName();
//                String lineName = BaseUtil.humpToLine2(fieldName);
//                columns.append(lineName).append(", ");
//                valuesList.add(field.get(object));
//            }
//        }
//        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
//
//        // 获取所有的值
//        for (int i = 1; i < objectList.size(); i++) {
//            for (Field field : fields) {
//                field.setAccessible(true);
//                if (field.get(objectList.get(i)) != null) {
//                    valuesList.add(field.get(objectList.get(i)));
//                }
//            }
//        }
//        // 确定一个 Object 的占位符 '?'
//        StringBuilder zhanweifuColumn = new StringBuilder("(");
//        for (int i = 0; i < columnNum; i++) {
//            zhanweifuColumn.append("?, ");
//        }
//        zhanweifuColumn.replace(zhanweifuColumn.lastIndexOf(", "), zhanweifuColumn.length(), ")");
//
//        // 确定所有的占位符
//        int objectNum = objectList.size();
//        StringBuilder zhanweifu = new StringBuilder();
//        for (int j = 0; j < objectNum; j++) {
//            zhanweifu.append(zhanweifuColumn.toString()).append(", ");
//        }
//        zhanweifu.replace(zhanweifu.lastIndexOf(", "), zhanweifu.length(), "");
//
//        // 生成最终 SQL
//        String tableName = object.getClass().getAnnotation(Table.class).name();
//        String sql = "INSERT INTO " + tableName + " " + columns + " VALUES " + zhanweifu.toString();
//        System.out.println(sql);
//        System.out.println(valuesList);
//
//        Query query = getSession().createSQLQuery(sql);
//        if (valuesList != null && !valuesList.isEmpty()) {
//            for (int i = 0; i < valuesList.size(); i++) {
//                query.setParameter(i, valuesList.get(i));
//            }
//        }
//        query.executeUpdate();
//
//    }


//    public Session getSession(){
//        Session session;
//        try{
//            session = sessionFactory.getCurrentSession();
//        }catch (HibernateException e){
//            session = sessionFactory.openSession();
//            log.info("session leaking !!!!!!!!!!!!!!!!!!{}",session.hashCode());
//        }
//        return session;
//    }

}
