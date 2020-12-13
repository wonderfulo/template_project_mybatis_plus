//package com.cxy.schedule;
//
//
//import org.apache.commons.lang3.math.NumberUtils;
//import org.redisson.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import xxy.nlp.entity.TmAppPaymentInfo;
//import xxy.nlp.entity.TmAppPaymentSettingInfo;
//import xxy.nlp.entity.TmPaymentOrder;
//import xxy.nlp.enums.MathematicsEnum;
//import xxy.nlp.enums.OrderStatusEnum;
//import xxy.nlp.enums.WxPayEnum;
//import xxy.nlp.service.crud.IAppPaymentInfoService;
//import xxy.nlp.service.crud.IAppPaymentSettingInfoService;
//import xxy.nlp.service.crud.IPaymentOrderService;
//import xxy.nlp.utils.sdk.AuthUtil;
//import xxy.nlp.utils.sdk.WXPayUtil;
//
//import java.util.List;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
///**
// * @Description 美鹿红包补偿操作
// * @Date 2020/9/21 17:04
// * @Author 陈翔宇
// * @Version 1.0
// **/
//@Component
//public class PayOrderScheduled {
//
//	@Autowired
//	private IPaymentOrderService paymentOrderService;
//
//	@Autowired
//	private RedissonClient client;
//
//	@Autowired
//	private IAppPaymentInfoService paymentInfoService;
//
//	@Autowired
//	private IAppPaymentSettingInfoService paymentSettingInfoService;
//
//	/**
//	 * 处理红包发放时，微信返回状态为处理中的订单
//	 * 目的: 检测红包是否发送成功
//	 * 结果: 发送成功==>>不做处理
//	 * 发送失败==>>给企业添加被扣除的余额
//	 */
//	@Scheduled(fixedRate = 5000 * 60 * 60) // 每5小时执行一次
//	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
//	public void scheduled() {
//		//1. 查询所有状态为处理中的订单
//		TmPaymentOrder tempOrder = new TmPaymentOrder();
//		tempOrder.setStatus(OrderStatusEnum.PROCESSING.getMessage());
//		List<TmPaymentOrder> orderList = paymentOrderService.getEntityListByAttr(tempOrder);
//		//2. 订单根据企业ID分组遍历
//		Map<String, List<TmPaymentOrder>> orderMap = orderList.stream().collect(Collectors.groupingBy(TmPaymentOrder::getAppId));
//		for (Map.Entry<String, List<TmPaymentOrder>> entry : orderMap.entrySet()) {
//			List<TmPaymentOrder> list = entry.getValue();
//			for (TmPaymentOrder order : list) {
//				try {
//					//3. 根据订单号查询当前订单的付款状态
//					String partnerTradeNo = order.getPartnerTradeNo();
//					Map<String, String> returnMap = getWxQueryResultByAttr(partnerTradeNo);
//
//					// 通信成功
//					if (WxPayEnum.SUCCESS.getMessage().equals(returnMap.get("return_code"))) {
//						//业务结果成功（非付款标识，付款是否成功需要查看status字段来判断）
//						//status字段在 return_code 和result_code都为SUCCESS的时候有返回
//						if (WxPayEnum.SUCCESS.getMessage().equals(returnMap.get("result_code"))) {
//
//							//4. 更新订单状态（成功/失败）
//							if (WxPayEnum.SUCCESS.getMessage().equals(returnMap.get("status"))) {
//								//转账成功，更新订单状态为成功
//								order.setStatus(OrderStatusEnum.SUCCESS.getMessage());
//								paymentOrderService.updateByPrimaryKeySelective(order);
//							} else if (WxPayEnum.FAIL.getMessage().equals(returnMap.get("status"))) {
//								//4.1 转账失败，申请当前企业redis锁
//								RLock lock = client.getLock(order.getAppId());
//
//								try {
//									//4.3 申请redis锁
//									boolean b = lock.tryLock(3, 10, TimeUnit.SECONDS);
//									if(b){
//										order.setStatus(OrderStatusEnum.FAIL.getMessage());
//										paymentOrderService.updateByPrimaryKeySelective(order);
//
//										//5. 给企业余额添加当前订单的金额
//										String wxOrderAmount = returnMap.get("payment_amount");
//										double amount = NumberUtils.toDouble(wxOrderAmount) / 100;
//										updateAppAmount(order.getAppId(), amount);
//									}
//								} catch (Exception e) {
//									e.printStackTrace();
//								} finally {
//									try {
//										//7. 释放redis锁
//										lock.unlock();
//									} catch (IllegalMonitorStateException e) {
//										System.out.println("锁超时释放异常，不用处理");
//									}
//								}
//							}
//						}
//					}
//				} catch (Exception e) {
//					//捕获WXPayUtil中异常
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 更新企业余额
//	 * @param appId 企业id
//	 * @param amount 金额
//	 */
//	private void updateAppAmount(String appId, double amount) {
//		//5.1 修改企业余额表，暂不修改（等红包功能修改企业余额时，在做处理）
////		TmAppPaymentInfo queryPaymentInfo = new TmAppPaymentInfo();
////		queryPaymentInfo.setAppId(appId);
////		TmAppPaymentInfo oldPaymentInfo = paymentInfoService.getEntityByAttr(queryPaymentInfo);
////		oldPaymentInfo.setAmount(oldPaymentInfo.getAmount() + amount);
////		paymentInfoService.updateByPrimaryKeySelective(oldPaymentInfo);
//
//		//5.2 修改企业设置信息(添加余额)
//		TmAppPaymentSettingInfo settingInfo = new TmAppPaymentSettingInfo();
//		settingInfo.setAppId(appId);
//		settingInfo.setAmount(amount);
//		paymentSettingInfoService.updateAmountByAppId(settingInfo, MathematicsEnum.ADD.getMessage());
//
//	}
//
//	/**
//	 * 封装参数，调用微信订单查询接口，返回响应结果
//	 * @param partnerTradeNo   商户订单号
//	 * @return
//	 * @throws Exception
//	 */
//	private Map<String, String> getWxQueryResultByAttr(String partnerTradeNo) throws Exception {
//		// 转换参数
//		SortedMap<String, String> wxQueryParamMap = WXPayUtil.getWxQueryParam(partnerTradeNo);
//		// 利用上面的参数，先去生成自己的签名
//		String sign = WXPayUtil.generateSignature(wxQueryParamMap, AuthUtil.PATERNERKEY);
//		// 将签名再放回map中，它也是一个参数
//		wxQueryParamMap.put("sign", sign);
//		// 将当前的map结合转化成xml格式
//		String xml = WXPayUtil.mapToXml(wxQueryParamMap);
//
//		//3.1 发送请求 获取响应结果
//		String returnXml;
//
//		try {
//			//3.2 调用微信订单查询接口
//			returnXml = WXPayUtil.wxOrderQuery(xml);
//		} catch (Exception ignored) {
//			returnXml = null;
//		}
//
//		// 将微信返回的xml结果转成map格式
//		return WXPayUtil.xmlToMap(returnXml);
//	}
//}
