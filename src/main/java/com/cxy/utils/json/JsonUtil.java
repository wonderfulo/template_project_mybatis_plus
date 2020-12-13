//package com.cxy.utils.json;
//
//import com.google.common.collect.Lists;
//import com.vsvz.mapping.bbs.BbsQuestion;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONNull;
//import net.sf.json.JSONObject;
//import net.sf.json.JsonConfig;
//import org.apache.commons.beanutils.BeanUtils;
//
//import java.util.*;
//
//public class JsonUtil {
//
//    public static String getString(JSONObject jo, String key) {
//        if (jo.containsKey(key)) {
//            if (!JSONNull.getInstance().equals(jo.get(key))) {
//                return jo.getString(key);
//            }
//        }
//        return null;
//    }
//
//    public static Integer getInteger(JSONObject jo, String key) {
//        return jo.containsKey(key) ? jo.getInt(key) : null;
//    }
//
//    public static Long getLong(JSONObject jo, String key) {
//        return jo.containsKey(key) ? jo.getLong(key) : null;
//    }
//
//    /**
//     *
//     * @param <T>
//     *
//     */
//
//    /***
//     * 将List对象序列化为JSON文本
//     */
//    public static <T> String toJSONString(List<T> list) {
//        JSONArray jsonArray = JSONArray.fromObject(list);
//
//        return jsonArray.toString();
//    }
//
//    /***
//     * 将对象序列化为JSON文本
//     *
//     * @param object
//     * @return
//     */
//    public static String toJSONStringArray(Object object) {
//        JSONArray jsonArray = JSONArray.fromObject(object);
//
//        return jsonArray.toString();
//    }
//
//    /***
//     * 将对象序列化为JSON文本
//     *
//     * @param object
//     * @return
//     */
//    public static String toJSONStringObj(Object object) {
//        JSONObject jsonObj = JSONObject.fromObject(object);
//        return jsonObj.toString();
//    }
//
//    /***
//     * 将JSON对象数组序列化为JSON文本
//     *
//     * @param jsonArray
//     * @return
//     */
//    public static String toJSONString(JSONArray jsonArray) {
//        return jsonArray.toString();
//    }
//
//    /***
//     * 将JSON对象序列化为JSON文本
//     *
//     * @param jsonObject
//     * @return
//     */
//    public static String toJSONString(JSONObject jsonObject) {
//        return jsonObject.toString();
//    }
//
//    /***
//     * 将对象转换为List对象
//     *
//     * @param object
//     * @return
//     */
//    public static List toArrayList(Object object) {
//        if (object == null) {
//            return null;
//        }
//        List arrayList = new ArrayList();
//
//        JSONArray jsonArray = JSONArray.fromObject(object);
//
//        Iterator it = jsonArray.iterator();
//        while (it.hasNext()) {
//            JSONObject jsonObject = (JSONObject) it.next();
//
//            Iterator keys = jsonObject.keys();
//            while (keys.hasNext()) {
//                Object key = keys.next();
//                Object value = jsonObject.get(key);
//                arrayList.add(value);
//            }
//        }
//
//        return arrayList;
//    }
//
//    /***
//     * 将对象转换为Collection对象
//     *
//     * @param object
//     * @return
//     */
//    public static Collection toCollection(Object object) {
//        JSONArray jsonArray = JSONArray.fromObject(object);
//
//        return JSONArray.toCollection(jsonArray);
//    }
//
//    /***
//     * 将对象转换为JSON对象数组
//     *
//     * @param object
//     * @return
//     */
//    public static JSONArray toJSONArray(Object object) {
//        return JSONArray.fromObject(object);
//    }
//
//    /***
//     * 将对象转换为List
//     *
//     * @param object
//     * @return
//     */
//    public static List objToList(Object object) {
//        JSONArray jsonArray = JSONArray.fromObject(object);
//        return JSONArray.toList(jsonArray);
//    }
//
//    /***
//     * 将对象数组转换为传入类型的List
//     *
//     * @param <T>
//     * @param jsonArray
//     * @param objectClass
//     * @return
//     */
//    public static <T> List<T> objToList(Object object, Class<T> objClass) {
//        if (object == null) {
//            return null;
//        } else {
//            JSONArray jsonArray = JSONArray.fromObject(object);
//            // return JSONArray.toList(jsonArray, objectClass);
//            return JSONArray.toList(jsonArray, objClass, new JsonConfig());
//        }
//    }
//
//    /***
//     * 将对象数组转换为传入类型的List
//     *
//     * 该方法适用于List对象含有list对象时
//     *
//     * @param <T>
//     * @param jsonArray
//     * @param objectClass
//     * @return
//     */
//    public static <T> List<T> objToListObj(Object object, Class<T> clz, Map<String, Class> map) {
//        if (object == null) {
//            return null;
//        } else {
//            List<T> list = new ArrayList<>();
//            JSONArray jsonArray = JSONArray.fromObject(object);
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                T newClz = (T) jsonObject.toBean(jsonObject, clz, map);
//                list.add(newClz);
//            }
//            return list;
//        }
//    }
//
//    /***
//     * 将对象转换为JSON对象
//     *
//     * @param object
//     * @return
//     */
//    public static JSONObject toJSONObject(Object object) {
//        return JSONObject.fromObject(object);
//    }
//
//    /***
//     * 将对象转换为HashMap
//     *
//     * @param object
//     * @return
//     */
//    public static HashMap toHashMap(Object object) {
//        if (object == null) {
//            return null;
//        }
//        HashMap<String, Object> data = new HashMap<String, Object>();
//        JSONObject jsonObject = JsonUtil.toJSONObject(object);
//        Iterator it = jsonObject.keys();
//        while (it.hasNext()) {
//            String key = String.valueOf(it.next());
//            Object value = jsonObject.get(key);
//            data.put(key, value);
//        }
//        return data;
//    }
//
//    /***
//     * 将对象转换为List<Map<String,Object>>
//     *
//     * @param object
//     * @return
//     */
//    // 返回非实体类型(Map<String,Object>)的List
//    public static List<Map<String, Object>> toList(Object object) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        JSONArray jsonArray = JSONArray.fromObject(object);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//            Map<String, Object> map = new HashMap<String, Object>();
//            Iterator it = jsonObject.keys();
//            while (it.hasNext()) {
//                String key = (String) it.next();
//                Object value = jsonObject.get(key);
//                map.put((String) key, value);
//            }
//            list.add(map);
//        }
//        return list;
//    }
//
//    /***
//     * 将JSON对象数组转换为传入类型的List
//     *
//     * @param <T>
//     * @param jsonArray
//     * @param objectClass
//     * @return
//     */
//    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
//        return JSONArray.toList(jsonArray, objectClass);
//    }
//
//    /***
//     * 将对象转换为传入类型的List
//     *
//     * @param <T>
//     * @param jsonArray
//     * @param objectClass
//     * @return
//     */
//    public static <T> List<T> toList(Object object, Class<T> objectClass) {
//        JSONArray jsonArray = JSONArray.fromObject(object);
//
//        return JSONArray.toList(jsonArray, objectClass);
//    }
//
//    /***
//     * 将JSON对象转换为传入类型的对象
//     *
//     * @param <T>
//     * @param jsonObject
//     * @param beanClass
//     * @return
//     */
//    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
//        return (T) JSONObject.toBean(jsonObject, beanClass);
//    }
//
//    /***
//     * 将将对象转换为传入类型的对象
//     *
//     * @param <T>
//     * @param object
//     * @param beanClass
//     * @return
//     */
//    public static <T> T toBean(Object object, Class<T> beanClass) {
//        JSONObject jsonObject = JSONObject.fromObject(object);
//
//        return (T) JSONObject.toBean(jsonObject, beanClass);
//    }
//
//    /***
//     * 将JSON文本反序列化为主从关系的实体
//     *
//     * @param <T>
//     *            泛型T 代表主实体类型
//     * @param <D>
//     *            泛型D 代表从实体类型
//     * @param jsonString
//     *            JSON文本
//     * @param mainClass
//     *            主实体类型
//     * @param detailName
//     *            从实体类在主实体类中的属性名称
//     * @param detailClass
//     *            从实体类型
//     * @return
//     */
//    public static <T, D> T toBean(String jsonString, Class<T> mainClass, String detailName, Class<D> detailClass) {
//        JSONObject jsonObject = JSONObject.fromObject(jsonString);
//        JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);
//
//        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
//        List<D> detailList = JsonUtil.toList(jsonArray, detailClass);
//
//        try {
//            BeanUtils.setProperty(mainEntity, detailName, detailList);
//        } catch (Exception ex) {
//            throw new RuntimeException("主从关系JSON反序列化实体失败！");
//        }
//
//        return mainEntity;
//    }
//
//    /***
//     * 将JSON文本反序列化为主从关系的实体
//     *
//     * @param <T>泛型T
//     *            代表主实体类型
//     * @param <D1>泛型D1
//     *            代表从实体类型
//     * @param <D2>泛型D2
//     *            代表从实体类型
//     * @param jsonString
//     *            JSON文本
//     * @param mainClass
//     *            主实体类型
//     * @param detailName1
//     *            从实体类在主实体类中的属性
//     * @param detailClass1
//     *            从实体类型
//     * @param detailName2
//     *            从实体类在主实体类中的属性
//     * @param detailClass2
//     *            从实体类型
//     * @return
//     */
//    public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass, String detailName1,
//                                       Class<D1> detailClass1, String detailName2, Class<D2> detailClass2) {
//        JSONObject jsonObject = JSONObject.fromObject(jsonString);
//        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
//        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
//
//        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
//        List<D1> detailList1 = JsonUtil.toList(jsonArray1, detailClass1);
//        List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);
//
//        try {
//            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
//            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
//        } catch (Exception ex) {
//            throw new RuntimeException("主从关系JSON反序列化实体失败！");
//        }
//
//        return mainEntity;
//    }
//
//    /***
//     * 将JSON文本反序列化为主从关系的实体
//     *
//     * @param <T>泛型T
//     *            代表主实体类型
//     * @param <D1>泛型D1
//     *            代表从实体类型
//     * @param <D2>泛型D2
//     *            代表从实体类型
//     * @param jsonString
//     *            JSON文本
//     * @param mainClass
//     *            主实体类型
//     * @param detailName1
//     *            从实体类在主实体类中的属性
//     * @param detailClass1
//     *            从实体类型
//     * @param detailName2
//     *            从实体类在主实体类中的属性
//     * @param detailClass2
//     *            从实体类型
//     * @param detailName3
//     *            从实体类在主实体类中的属性
//     * @param detailClass3
//     *            从实体类型
//     * @return
//     */
//    public static <T, D1, D2, D3> T toBean(String jsonString, Class<T> mainClass, String detailName1,
//                                           Class<D1> detailClass1, String detailName2, Class<D2> detailClass2, String detailName3,
//                                           Class<D3> detailClass3) {
//        JSONObject jsonObject = JSONObject.fromObject(jsonString);
//        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
//        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
//        JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);
//
//        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
//        List<D1> detailList1 = JsonUtil.toList(jsonArray1, detailClass1);
//        List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);
//        List<D3> detailList3 = JsonUtil.toList(jsonArray3, detailClass3);
//
//        try {
//            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
//            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
//            BeanUtils.setProperty(mainEntity, detailName3, detailList3);
//        } catch (Exception ex) {
//            throw new RuntimeException("主从关系JSON反序列化实体失败！");
//        }
//
//        return mainEntity;
//    }
//
//    /***
//     * 将JSON文本反序列化为主从关系的实体
//     *
//     * @param <T>
//     *            主实体类型
//     * @param jsonString
//     *            JSON文本
//     * @param mainClass
//     *            主实体类型
//     * @param detailClass
//     *            存放了多个从实体在主实体中属性名称和类型
//     * @return
//     */
//    public static <T> T toBean(String jsonString, Class<T> mainClass, HashMap<String, Class> detailClass) {
//        JSONObject jsonObject = JSONObject.fromObject(jsonString);
//        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
//        for (Object key : detailClass.keySet()) {
//            try {
//                Class value = (Class) detailClass.get(key);
//                BeanUtils.setProperty(mainEntity, key.toString(), value);
//            } catch (Exception ex) {
//                throw new RuntimeException("主从关系JSON反序列化实体失败！");
//            }
//        }
//        return mainEntity;
//    }
//
//
//    /**
//     * @param jsonString
//     * @param clazz
//     * @return
//     */
//    public static <T> List<T> convertToList(String jsonString, Class<T> clazz) {
//        JSONArray array = JSONArray.fromObject(jsonString);
//        List<T> arrayList = Lists.newArrayList();
//        for (int i = 0; i < array.size(); i++) {
//            JSONObject jsonObject = JSONObject.fromObject(array.getString(i));
//            arrayList.add((T) JSONObject.toBean(jsonObject, clazz));
//        }
//        return arrayList;
//    }
//
//    public static void main(String[] args) {
//        List<BbsQuestion> questions = new ArrayList<>();
////        try{
//        questions = convertToList("[{\"QContext\":\"1234qwer[$]null[#]http://supershoper.xxynet.com/FniWhWWgXGgtNdcvtIzQjdqKo72V[#]\",\"QFrom\":\"\",\"QId\":8836,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":25,\"day\":4,\"hours\":10,\"minutes\":12,\"month\":9,\"seconds\":2,\"time\":1540433522000,\"timezoneOffset\":-480,\"year\":118},\"createUser\":325265129,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":0,\"goodsCode\":\"\",\"goodsId\":0,\"goodsImg\":\"\",\"goodsName\":\"\"},\"flag\":\"\",\"goldSet\":0,\"headPic\":\"http://supershoper.xxynet.com/cs201812131515207440wOrj5bBp\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"123ssp\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=8836\",\"showName\":\"123ssp\",\"startTime\":null,\"sysUserId\":325265129,\"typeName\":\"\",\"uname\":\"123ssp\"},{\"QContext\":\"Ms 你ni[$]null[#] [#] \",\"QFrom\":\"\",\"QId\":8990,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":19,\"day\":1,\"hours\":10,\"minutes\":49,\"month\":10,\"seconds\":27,\"time\":1542595767000,\"timezoneOffset\":-480,\"year\":118},\"createUser\":325265183,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":0,\"goodsCode\":\"\",\"goodsId\":0,\"goodsImg\":\"\",\"goodsName\":\"\"},\"flag\":\"\",\"goldSet\":0,\"headPic\":\"\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"好好的黑道\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=8990\",\"showName\":\"好好的黑道\",\"startTime\":null,\"sysUserId\":325265183,\"typeName\":\"\",\"uname\":\"好好的黑道\"},{\"QContext\":\"发图发个广告也一样一样一样[$]null[#]http://supershoper.xxynet.com/Fo7LzjgJ3O2YUQYdx7FiAPeJwSg7,http://supershoper.xxynet.com/FhfhoqEFPApJT7l22Gy_prqFMaKU,http://supershoper.xxynet.com/FoLabil3wzuHymj2jiAOvLjJX5oR,http://supershoper.xxynet.com/Fk2V9hcQPgpTx1oH4vKiU6leeuNS,http://supershoper.xxynet.com/FjtHTiC2_GTOMzLWlFPhDRfMhXWD,http://supershoper.xxynet.com/Fhh1W9o4kEtpMCd4nGzp-axrru7F,http://supershoper.xxynet.com/FgYGBmkwr9rrJKgKe5Z-PYgpRxlL,http://supershoper.xxynet.com/FkeFxlBRjoojx1_NrVSRFHRmu8Pg,http://supershoper.xxynet.com/FiXDanjM8HyoDiiBcN75nIAYw4ug[#]\",\"QFrom\":\"\",\"QId\":10260,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[{\"QContext\":\"\",\"QId\":10260,\"QStatus\":\"\",\"QTitle\":\"\",\"answerCont\":\"Hdhdjxbjsjigcudidhd \",\"answerId\":16286,\"answerPic\":\"\",\"answerText\":\"\",\"answers\":[],\"changeTime\":\"2019-06-05 04:36:07\",\"count\":5,\"createTime\":{\"date\":5,\"day\":3,\"hours\":16,\"minutes\":36,\"month\":5,\"seconds\":7,\"time\":1559723767000,\"timezoneOffset\":-480,\"year\":119},\"createUser\":325265147,\"deleteFlag\":0,\"deleteTime\":null,\"dutyUserFlag\":\"\",\"fileUrl\":\"http://supershoper.xxynet.com/FoIaA03PzM_4tCtBwHkO16uZ1E1c,http://supershoper.xxynet.com/FqkQtIEA8nuxFwGDFBJxJxV1BLNm,http://supershoper.xxynet.com/Fv4143B5Y7WryroPawBmDLk9pzFg,http://supershoper.xxynet.com/FnAYlexxh5xvr4wx6os9YaEnbrwu,http://supershoper.xxynet.com/FgSAL1dsvUhASDq61vIbZ5HShOs6,http://supershoper.xxynet.com/FupbWJA-G8dSkYBjVY1MHWw_fJ2a,http://supershoper.xxynet.com/FoR-GOVWc2ZpKiUZEQqm9_TTen_9,http://supershoper.xxynet.com/Fn4jO_Dd8sfUTaDcbvL7EIuXR5Nv,http://supershoper.xxynet.com/FhXPyx0yLF4hEvigR12iCxD3YUJj\",\"goldGet\":0,\"headPic\":\"http://supershoper.xxynet.com/FnXW2dU_48s5Qz9QgJgpmfrxX8AT\",\"isOrRead\":\"\",\"isTrue\":\"2\",\"name\":\"我就是投偷猫猫的鱼\",\"nickName\":\"蘑菇\",\"parentId\":0,\"praise\":0,\"praiseFlag\":\"\",\"qRelTag\":\"\",\"replyId\":0,\"showName\":\"蘑菇\",\"sysUserId\":0,\"topFlag\":0,\"type\":1,\"uname\":\"\"}],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":3,\"day\":1,\"hours\":10,\"minutes\":2,\"month\":5,\"seconds\":38,\"time\":1559527358000,\"timezoneOffset\":-480,\"year\":119},\"createUser\":325265144,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":0,\"goodsCode\":\"\",\"goodsId\":0,\"goodsImg\":\"\",\"goodsName\":\"\"},\"flag\":\"\",\"goldSet\":100,\"headPic\":\"http://supershoper.xxynet.com/FmAwVh8UeqAaOIba967eEYEbTehM\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"哦哦哦\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=10260\",\"showName\":\"哦哦哦\",\"startTime\":null,\"sysUserId\":325265144,\"typeName\":\"\",\"uname\":\"哦哦哦\"},{\"QContext\":\"聚聚股[$]null[#]http://supershoper.xxynet.com/cs_201910231505083390s5XghCWX0,http://supershoper.xxynet.com/cs_201910231505083430QgBWEvet1,http://supershoper.xxynet.com/cs_201910231505083460TcuGYYve2,http://supershoper.xxynet.com/cs_201910231505083480S66QNIXv3,http://supershoper.xxynet.com/cs_201910231505083510St9p04be4\",\"QFrom\":\"\",\"QId\":10596,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[{\"QContext\":\"\",\"QId\":10596,\"QStatus\":\"\",\"QTitle\":\"\",\"answerCont\":\"太空棉\",\"answerId\":17057,\"answerPic\":\"\",\"answerText\":\"\",\"answers\":[],\"changeTime\":\"2019-10-25 02:00:47\",\"count\":3,\"createTime\":{\"date\":25,\"day\":5,\"hours\":14,\"minutes\":0,\"month\":9,\"seconds\":47,\"time\":1571983247000,\"timezoneOffset\":-480,\"year\":119},\"createUser\":325265268,\"deleteFlag\":0,\"deleteTime\":null,\"dutyUserFlag\":\"\",\"fileUrl\":\"\",\"goldGet\":0,\"headPic\":\"http://supershoper.xxynet.com/cs202001072034177170i7pUz1nC\",\"isOrRead\":\"\",\"isTrue\":\"2\",\"name\":\"tjl005\",\"nickName\":\"\uD83C\uDE50️\",\"parentId\":0,\"praise\":0,\"praiseFlag\":\"\",\"qRelTag\":\"\",\"replyId\":0,\"showName\":\"\uD83C\uDE50️\",\"sysUserId\":0,\"topFlag\":0,\"type\":1,\"uname\":\"\"}],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":23,\"day\":3,\"hours\":14,\"minutes\":55,\"month\":9,\"seconds\":24,\"time\":1571813724000,\"timezoneOffset\":-480,\"year\":119},\"createUser\":325265189,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":0,\"goodsCode\":\"\",\"goodsId\":0,\"goodsImg\":\"\",\"goodsName\":\"\"},\"flag\":\"\",\"goldSet\":0,\"headPic\":\"http://supershoper.xxynet.com/325265189xxy1543452798406\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"恐暴龙怼巨鲨\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=10596\",\"showName\":\"恐暴龙怼巨鲨\",\"startTime\":null,\"sysUserId\":325265189,\"typeName\":\"\",\"uname\":\"恐暴龙怼巨鲨\"},{\"QContext\":\"超级超级丰富夫妇[$]null[#]{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262030495010zIMNciiM.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0324.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_202005262031135880CdTEdu4t0;\\n}\\n,{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262030496280mXkYHFrF.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0325.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_2020052620311359204euGnBPA1;\\n}\\n,{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262030497130IGVKndEW.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0326.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_202005262031135930piYxc9Af2;\\n}\\n,{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262030498000rdroNEIQ.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0330.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_2020052620311359504AHdSKW33;\\n}\\n\",\"QFrom\":\"\",\"QId\":13520,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":26,\"day\":2,\"hours\":20,\"minutes\":25,\"month\":4,\"seconds\":4,\"time\":1590495904000,\"timezoneOffset\":-480,\"year\":120},\"createUser\":325265190,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":389,\"goodsCode\":\"9787115492272\",\"goodsId\":1031151104,\"goodsImg\":\"http://supershoper.xxynet.com/vsvz1575023261188,http://supershoper.xxynet.com/vsvz1575023261315,http://supershoper.xxynet.com/vsvz1575023261413,http://supershoper.xxynet.com/vsvz1575023261778,http://supershoper.xxynet.com/vsvz1575023261851,http://supershoper.xxynet.com/vsvz1575023261887\",\"goodsName\":\"book1\"},\"flag\":\"\",\"goldSet\":0,\"headPic\":\"http://supershoper.xxynet.com/325265190xxy15892519262498698\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"陈家军开阵\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=13520\",\"showName\":\"陈家军开阵\",\"startTime\":null,\"sysUserId\":325265190,\"typeName\":\"\",\"uname\":\"陈家军开阵\"},{\"QContext\":\"局部和常常昏昏沉沉[$]null[#]{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262032001860pcUjx7Ag.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0328.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_2020052620320988706SIuE2bP0;\\n}\\n,{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_2020052620320031700GqC7Tr6.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0329.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_202005262032098890sKJVRIqM1;\\n}\\n,{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262032004030Y4n1pJsy.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0330.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_2020052620320989000szkJELW2;\\n}\\n,{\\n\\timagePath = /var/mobile/Containers/Data/Application/37CCF458-FFBF-4AF5-AAEB-02E7B482E350/Library/Caches/BBSImage_202005262032004910Whv3Acdm.png;\\n\\tlocalPath = file:///var/mobile/Media/DCIM/100APPLE/IMG_0337.PNG;\\n\\tQINIU_URL = http://supershoper.xxynet.com/cs_202005262032098910h146Tl773;\\n}\\n\",\"QFrom\":\"\",\"QId\":13521,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[{\"QContext\":\"\",\"QId\":13521,\"QStatus\":\"\",\"QTitle\":\"\",\"answerCont\":\"个小性感好吃好吃\",\"answerId\":17312,\"answerPic\":\"\",\"answerText\":\"\",\"answers\":[],\"changeTime\":\"2020-05-26 08:37:35\",\"count\":1,\"createTime\":{\"date\":26,\"day\":2,\"hours\":20,\"minutes\":37,\"month\":4,\"seconds\":35,\"time\":1590496655000,\"timezoneOffset\":-480,\"year\":120},\"createUser\":325265190,\"deleteFlag\":0,\"deleteTime\":null,\"dutyUserFlag\":\"\",\"fileUrl\":\"http://supershoper.xxynet.com/cs_202005262043435960Mw1AygFM0,http://supershoper.xxynet.com/cs_202005262043435990PEv7xInM1,http://supershoper.xxynet.com/cs_202005262043436010y5PyMeRf2,http://supershoper.xxynet.com/cs_2020052620434360309vHTscXn3,http://supershoper.xxynet.com/cs_202005262043436040v7UsFujC4,http://supershoper.xxynet.com/cs_202005262043436050GPe0SlRN5\",\"goldGet\":0,\"headPic\":\"http://supershoper.xxynet.com/325265190xxy15892519262498698\",\"isOrRead\":\"\",\"isTrue\":\"2\",\"name\":\"12345678\",\"nickName\":\"陈家军开阵\",\"parentId\":0,\"praise\":0,\"praiseFlag\":\"\",\"qRelTag\":\"\",\"replyId\":0,\"showName\":\"陈家军开阵\",\"sysUserId\":0,\"topFlag\":0,\"type\":0,\"uname\":\"\"}],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":26,\"day\":2,\"hours\":20,\"minutes\":26,\"month\":4,\"seconds\":1,\"time\":1590495961000,\"timezoneOffset\":-480,\"year\":120},\"createUser\":325265190,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":387,\"goodsCode\":\"9787115450630\",\"goodsId\":1031151103,\"goodsImg\":\"http://supershoper.xxynet.com/vsvz1575022676904,http://supershoper.xxynet.com/vsvz1575022677064,http://supershoper.xxynet.com/vsvz1575022677122,http://supershoper.xxynet.com/vsvz1575022677240,http://supershoper.xxynet.com/vsvz1575022677303,http://supershoper.xxynet.com/vsvz1575022677354,http://supershoper.xxynet.com/vsvz1575022677414,http://supershoper.xxynet.com/vsvz1575022677476\",\"goodsName\":\"book\"},\"flag\":\"\",\"goldSet\":0,\"headPic\":\"http://supershoper.xxynet.com/325265190xxy15892519262498698\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"陈家军开阵\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=13521\",\"showName\":\"陈家军开阵\",\"startTime\":null,\"sysUserId\":325265190,\"typeName\":\"\",\"uname\":\"陈家军开阵\"},{\"QContext\":\"123[$]null[#]http://supershoper.xxynet.com/cs_202006281650046420MVicnIzf0,http://supershoper.xxynet.com/cs_202006281650046450SyBuMFTi1,http://supershoper.xxynet.com/cs_202006281650046470lV4vttvQ2,http://supershoper.xxynet.com/cs_202006281650046480vYFkqTNi3,http://supershoper.xxynet.com/cs_202006281650046500pcPGPAbr4,http://supershoper.xxynet.com/cs_202006281650046510tZOeHz7L5,http://supershoper.xxynet.com/cs_202006281650046520ntjgMyiD6\",\"QFrom\":\"\",\"QId\":13593,\"QRelTag\":\"\",\"QStatus\":\"1\",\"QTitle\":\"\",\"answerList\":[],\"appId\":\"cs\",\"attachmentType\":0,\"banFlag\":0,\"bbsTagTypes\":[],\"changeTime\":\"\",\"count\":0,\"createFlag\":0,\"createTime\":{\"date\":28,\"day\":0,\"hours\":16,\"minutes\":48,\"month\":5,\"seconds\":38,\"time\":1593334118000,\"timezoneOffset\":-480,\"year\":120},\"createUser\":325265190,\"deleteFlag\":0,\"dutyReplyFlag\":0,\"dutyUserId\":0,\"endTime\":null,\"fabGoodsInfo\":{\"bbsQuestionId\":0,\"fabId\":0,\"goodsCode\":\"\",\"goodsId\":0,\"goodsImg\":\"\",\"goodsName\":\"\"},\"flag\":\"\",\"goldSet\":0,\"headPic\":\"http://supershoper.xxynet.com/325265190xxy15892519262498698\",\"isFlag\":0,\"isHot\":0,\"isOrRead\":\"\",\"isOrReadNum\":0,\"nickName\":\"陈家军开阵\",\"orgCode\":\"\",\"orgId\":0,\"orgIdParams\":\"\",\"orgName\":\"\",\"orgType\":0,\"qRelTag\":\"\",\"qaStatus\":\"\",\"quesAnswerUserId\":0,\"questionTypeId\":\"\",\"relTypeId\":\"\",\"replyUser\":0,\"searchCount\":0,\"shareUrl\":\"http://172.16.0.200:8080/shopguide/h5/share/questionAnswer_sharePage.html?Qid=13593\",\"showName\":\"陈家军开阵\",\"startTime\":null,\"sysUserId\":325265190,\"typeName\":\"\",\"uname\":\"陈家军开阵\"}]", BbsQuestion.class);
////        }catch (Exception e){
////
////        }
//        System.out.println(questions);
//
//    }
//
//    /**
//     * @param jsonString
//     * @param clazz
//     * @return
//     */
//    public static <T> T convertToObj(String jsonString, Class<T> clazz) {
//        JSONObject jsonObject = JSONObject.fromObject(jsonString);
//        return (T) JSONObject.toBean(jsonObject, clazz);
//    }
//}
