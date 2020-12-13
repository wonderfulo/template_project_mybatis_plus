//package com.cxy.controller.wx_pay;
//
//import cn.hutool.core.codec.Caesar;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.redisson.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import xxy.nlp.domain.ResponseInfo;
//import xxy.nlp.domain.redis.RedisBaseService;
//import xxy.nlp.entity.*;
//import xxy.nlp.enums.*;
//import xxy.nlp.model.InsightUserInfo;
//import xxy.nlp.service.IInsightTokenService;
//import xxy.nlp.service.crud.*;
//import xxy.nlp.utils.RedPacketUtil;
//import xxy.nlp.utils.UUIDUtil;
//import xxy.nlp.utils.sdk.AuthUtil;
//import xxy.nlp.utils.sdk.WXPayUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * 企业转账到零钱
// * @author: lichaofeng
// */
//
//@CrossOrigin
//@RestController
//@RequestMapping("/transfer")
//@Api(tags = {"企业转账操作处理"})
//public class TransferController {
//
//
//    @Autowired
//    private RedissonClient client;
//
//    @Autowired
//    private IInsightTokenService insightTokenService;
//
//    @Autowired
//    private IAppPaymentInfoService appPaymentInfoService;
//
//    @Autowired
//    private IPaymentOrderService paymentOrderService;
//
//    @Autowired
//    private IPaymentOrderLogService paymentOrderLogService;
//
//    @Autowired
//    private IPaymentLogService paymentLogService;
//
//    @Autowired
//    private IAppPaymentSettingInfoService paymentSettingInfoService;
//
//    @Autowired
//    private IAppPaymentTurnoverService paymentTurnoverService;
//
//    @Autowired
//    private RedisBaseService redisBaseService;
//
//
//    /**
//     * @Title: transfer
//     * @Description: 企业转账到零钱
//     * @param:
//     * @return:
//     */
//    @RequestMapping(value = "/pay",method = RequestMethod.POST)
//    @ApiOperation(
//            value = "企业转账到零钱",
//            notes = "红包功能",
//            response = String.class,
//            responseContainer = "Object")
//    public @ResponseBody
//    ResponseInfo<Map<String, String>> transfer(HttpServletRequest request,
//                                         @ApiParam(value = "登录令牌", required = true)
//                                  @RequestParam(value = "accessToken", required = true)
//                                          String accessToken) {
//        String returnAmount = "";
//        //1:处理中,2:成功,3:失败
//        Integer status = -1;
//        InsightUserInfo userInfo = insightTokenService.getUserInfo(accessToken);
//        if (userInfo == null) {
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.INVALID_TOEKN.toMap());
//        }
//
//        // 限流
//        RRateLimiter rateLimiter = client.getRateLimiter("wxpay_meilu");
//        //每1秒产生30个令牌
//        rateLimiter.trySetRate(RateType.OVERALL, 30, 1,
//                RateIntervalUnit.SECONDS);
//
//        //cc:尝试获取一个令牌
//        if (rateLimiter.tryAcquire(1)) {
//            RLock lock = client.getLock(userInfo.getAppId());
//            try {
//                //1. 申请redis锁
//                lock.tryLock(3, 10, TimeUnit.SECONDS);
//
//                //2. 判断用户是否超过领取金额/次数限制
//                ExceedCeilingEnum exceedCeiling = paymentSettingInfoService.isExceedCeilingEnum(userInfo);
//
//                // 转换参数
//                SortedMap<String, String> packageParams = WXPayUtil.genWxParam(request, userInfo);
//                // 利用上面的参数，先去生成自己的签名
//                String sign = WXPayUtil.generateSignature(packageParams, AuthUtil.PATERNERKEY);
//                // 将签名再放回map中，它也是一个参数
//                packageParams.put("sign", sign);
//                // 将当前的map结合转化成xml格式
//                String xml = WXPayUtil.mapToXml(packageParams);
//
//                //3.验证企业是否开启红包功能
//                if(Objects.equals(exceedCeiling.getCode(), ExceedCeilingEnum.NOT_OPEN.getCode())){
//                    return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.NOT_OPEN.toMap());
//                }
//
//                //次数上限
//                if(Objects.equals(exceedCeiling.getCode(), ExceedCeilingEnum.USER_DAY_NUM.getCode())){
//                    redPacketNum(userInfo);
//                    return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.EXCEED_CEILING.toMap());
//                }
//                //企业余额上限
//                if(Objects.equals(exceedCeiling.getCode(), ExceedCeilingEnum.APP_TOTAL_MONEY.getCode())){
//                    return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.NOT_SUFFICIENT_FUNDS.toMap());
//                }
//
//                //4 生成订单，记录微信订单ID,用户ID，金额
//                TmPaymentOrder order = new TmPaymentOrder();
//                order.buildOrder(userInfo, packageParams, xml, null, null);
//                order.setOrderId(UUIDUtil.genId());
//                order.setCreateUser(userInfo.getUserId());
//                order.setCreateTime(new Date());
//                order.setStatus(OrderStatusEnum.NO_DISPOSE.getMessage());
//                paymentOrderService.insertSelective(order);
//
//                //5. 发送请求 获取响应结果
//                String returnXml;
//
//                try {
//                    //5.1 调用微信支付
//                    returnXml = WXPayUtil.wxPay(xml);
//                }catch (Exception ignored){
//                    returnXml = null;
//                }
//
//                // 将微信返回的xml结果转成map格式
//                Map<String, String> returnMap = WXPayUtil.xmlToMap(returnXml);
//
//                //6 更新订单状态
//                TmPaymentOrder oldOrder = paymentOrderService.getEntityByPartnerTradeNo(order.getPartnerTradeNo());
//                oldOrder.buildOrder(null, null, null, returnMap, returnXml);
//                //此时数据库中还没有插入定单，获取的old订单没有主键,使用上方的订单id
//                oldOrder.setOrderId(order.getOrderId());
//
//                if(WxPayEnum.FAIL.getMessage().equals(returnMap.get("result_code"))){
//                    if(WxPayEnum.PROCESSING.getMessage().equals(returnMap.get("err_code"))){
//                        //付款处理中
//                        oldOrder.setStatus(OrderStatusEnum.PROCESSING.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        returnAmount = paySuccess(userInfo, packageParams, xml, returnXml, returnMap, oldOrder);
//                        status = 1;
//                    }else if(WxPayEnum.SENDNUM_LIMIT.getMessage().equals(returnMap.get("err_code"))){
//                        //该用户今日付款次数超过限
//                        oldOrder.setStatus(OrderStatusEnum.FAIL.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        status = 4;
//                    }else if(WxPayEnum.NOTENOUGH.getMessage().equals(returnMap.get("err_code"))){
//                        //平台余额不足
//                        oldOrder.setStatus(OrderStatusEnum.FAIL.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        status = 5;
//                    }else if(WxPayEnum.FREQ_LIMIT.getMessage().equals(returnMap.get("err_code"))){
//                        //调用接口过于频繁
//                        oldOrder.setStatus(OrderStatusEnum.FAIL.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        status = 6;
//                    }else if(WxPayEnum.MONEY_LIMIT.getMessage().equals(returnMap.get("err_code"))){
//                        //付款额度已经超限
//                        oldOrder.setStatus(OrderStatusEnum.FAIL.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        status = 7;
//                    }else if(WxPayEnum.V2_ACCOUNT_SIMPLE_BAN.getMessage().equals(returnMap.get("err_code"))){
//                        //无法给未实名用户付款
//                        oldOrder.setStatus(OrderStatusEnum.FAIL.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        status = 8;
//                    }else{
//                        //付款失败
//                        oldOrder.setStatus(OrderStatusEnum.FAIL.getMessage());
//                        paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                        status = 3;
//                    }
//                }else{
//                    oldOrder.setStatus(OrderStatusEnum.SUCCESS.getMessage());
//                    paymentOrderService.updateByPrimaryKeySelective(oldOrder);
//                    List<TmPaymentOrder> entityListByAttr = paymentOrderService.getEntityListByAttr(oldOrder);
//                    returnAmount = paySuccess(userInfo, packageParams, xml, returnXml, returnMap, entityListByAttr.get(0));
//                    status = 2;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.GET_FAILURE.toMap());
//            }finally {
//                try {
//                    //11 释放redis锁
//                    lock.unlock();
//                }catch (IllegalMonitorStateException e){
//                    System.out.println("锁超时释放异常，不用处理");
//                }
//            }
//        }else{
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.CURRENT_LIMITING.toMap());
//        }
//
//        if(status == 1 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.WAIT_A_MOMENT.toMap());
//        }else if(status == 2 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.OK,returnAmount,RedPacketEnum.SUCCESS.toMap());
//        }else if(status == 3 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.GET_FAILURE.toMap());
//        }else if(status == 4 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.SENDNUM_LIMIT.toMap());
//        }else if(status == 5 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.NOTENOUGH.toMap());
//        }else if(status == 6 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.CURRENT_LIMITING.toMap());
//        }else if(status == 7 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.MONEY_LIMIT.toMap());
//        }else if(status == 8 ){
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.ERROR,null,RedPacketEnum.V2_ACCOUNT_SIMPLE_BAN.toMap());
//        }else {
//            return ResponseInfo.setCodeAndMessageAndData(ResponseInfo.OK,returnAmount,RedPacketEnum.GET_FAILURE.toMap());
//        }
//    }
//
//    /**
//     * 业务追加新功能，统计转账成功/处理中、超过上限的次数
//     * @param userInfo
//     */
//    private void redPacketNum(InsightUserInfo userInfo) {
//        String key = RedPacketUtil.getRedPacketNumKey(userInfo);
//        int value = 1;
//
//        //先查看redis中是否已经存在
//        Object obj = redisBaseService.get(key);
//        if(obj != null){
//            value = Integer.parseInt(obj.toString()) + 1;
//        }
//
//        Calendar ca = Calendar.getInstance();
//        //失效的时间: 当日23:59:59
//        ca.set(Calendar.HOUR_OF_DAY, 23);
//        ca.set(Calendar.MINUTE, 59);
//        ca.set(Calendar.SECOND, 59);
//        long endTime = ca.getTimeInMillis();
//
//        long startTime = System.currentTimeMillis();
//        long surplusTime = (endTime - startTime) / 1000;
//
//        redisBaseService.set(key,String.valueOf(value),surplusTime, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 付款成功后，流水日志记录与处理
//     * @param userInfo
//     * @param packageParams
//     * @param xml
//     * @param returnXml
//     * @param returnMap
//     * @param order
//     * @return
//     */
//    private String paySuccess(InsightUserInfo userInfo, SortedMap<String, String> packageParams, String xml, String returnXml, Map<String, String> returnMap, TmPaymentOrder order) {
//        String returnAmount;//付款成功
//
//        //红包金额赋值
//        returnAmount = (order.getAmount() / 100) + "";
//
//        //7 记录订单日志
//        TmPaymentOrderLog orderLog = new TmPaymentOrderLog();
//        orderLog.buildOrderLog(userInfo, packageParams, xml, returnMap, returnXml);
//        orderLog.setOrderId(order.getOrderId());
//        orderLog.setCreateTime(new Date());
//        orderLog.setCreateUser(userInfo.getUserId());
//        paymentOrderLogService.insertSelective(orderLog);
//
//        //8 扣除企业余额（订单状态为成功/处理中）
//        //8.1 操作企业余额表, 此处也可以 update x set y = y - money where XXX
//        // 所有限制从企业设置表去查，企业余额表暂不操作
////        TmAppPaymentInfo tempTmApmInfo = new TmAppPaymentInfo();
////        tempTmApmInfo.setAppId(userInfo.getAppId());
////        TmAppPaymentInfo oldTmApmInfo = appPaymentInfoService.getEntityByAttr(tempTmApmInfo);
////        oldTmApmInfo.setAmount(oldTmApmInfo.getAmount() - NumberUtils.toDouble(packageParams.get("amount")) / 100);
////        appPaymentInfoService.updateByPrimaryKeySelective(oldTmApmInfo);
//
//        //8.2 操作企业设置信息表
//        TmAppPaymentSettingInfo settingInfo = new TmAppPaymentSettingInfo();
//        settingInfo.setAppId(userInfo.getAppId());
//        settingInfo.setAmount(NumberUtils.toDouble(packageParams.get("amount")) / 100);
//        paymentSettingInfoService.updateAmountByAppId(settingInfo, MathematicsEnum.REDUCE.getMessage());
//
//        //9 记录企业流水日志
//        TmAppPaymentTurnover tmAppPaymentTurnover = new TmAppPaymentTurnover();
//        tmAppPaymentTurnover.buildPaymentTurnover(order);
//        paymentTurnoverService.insertSelective(tmAppPaymentTurnover);
//        //10 记录交易流水日志
//        TmPaymentLog tmPaymentLog = new TmPaymentLog();
//        tmPaymentLog.buildPaymentLog(order);
//        paymentLogService.insertSelective(tmPaymentLog);
//        return returnAmount;
//    }
//
//}