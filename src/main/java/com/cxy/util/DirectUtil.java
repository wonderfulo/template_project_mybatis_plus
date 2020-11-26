//package com.vsvz.utils.qiniu;
//
//import com.qiniu.pili.Client;
//import com.qiniu.pili.Hub;
//import com.qiniu.pili.Hub.ListRet;
//import com.qiniu.pili.PiliException;
//import com.qiniu.pili.Stream;
//import com.qiniu.pili.Stream.LiveStatus;
//import com.qiniu.pili.Stream.Record;
//import com.qiniu.pili.Stream.SaveOptions;
//
///**
// * 七牛云直播
// * API地址：https://developer.qiniu.com/pili/api/1219/the-server-api-reference
// * @author BZhou
// *
// */
//public class DirectUtil {
//
//	//七牛云密钥中的AK
//	public static final String ACCESS_KEY = "HZwZZ2-pm3JThoqcnHi9U7F_3L94DhLLQ7ovbKvc";
//
//	//七牛云密钥中的SK
//	public static final String SECRET_KEY = "QZ0h2U4CitYwO7J5B6SpiSwKu0AwU_4yvw0j0WN_";
//
//	// 直播空间名
//	public static final String defaultHubName = "cjdg";
//
//	// RTMP推流域名
//	public static final String publishDomain = "pili-publish.xxynet.com";
//
//	// RTMP播放域名
//	public static final String rtmpDomain = "pili-live-rtmp.xxynet.com";
//
//	// HLS播放域名
//	public static final String hlsDomain = "pili-live-hls.xxynet.com";
//
//	// TS切片播放域名
//	public static final String tsDomain = "pili-media.xxynet.com";
//
//	// HDL播放域名
//	public static final String hdlDomain = "pili-live-hdl.xxynet.com";
//
//	// 直播封面
//	public static final String coverDomain = "pili-live-snapshot.xxynet.com";
//
//	// 默认流名
//	private static final String defaultStreamKey = "cjdgStreamKey";
//
//	// 地址类型
//	public static enum URLTYPE {
//		RTMP, HLS, HDL, TS, Cover
//	};
//
//	private static final Client cli = new Client(ACCESS_KEY, SECRET_KEY);
//
//	/**
//	 * 根据直播空间名获取Hub
//	 * @param hubName
//	 * @return
//	 */
//	public static Hub getHub(String hubName) {
//		if(hubName == null || "".equals(hubName)) {
//			hubName = defaultHubName;
//		}
//		return cli.newHub(hubName);
//	}
//
//	/**
//	 * 生成 RTMP 推流地址
//	 * @return
//	 */
//	public static String getPublishUrl() {
//		return getPublishUrl(defaultHubName, defaultStreamKey, 60);
//	}
//
//	/**
//	 * 生成 RTMP 推流地址
//	 * @param streamKey 流名，流不需要事先存在，推流会自动创建流
//	 * @param publishExpireTime 生成的推流地址的有效时间(秒)
//	 * @return
//	 */
//	public static String getPublishUrl(String hubName, String streamKey, int publishExpireTime) {
//		/**
//		 * 生成 RTMP 推流地址
//		 * publishDomain: 与直播空间绑定的 RTMP 推流域名，可以在 portal.qiniu.com 上绑定
//		 * hub: 直播空间名
//		 * streamKey: 流名，流不需要事先存在，推流会自动创建流
//		 * expireAfterSeconds: 生成的推流地址的有效时间(秒)
//		 */
//		if(hubName == null || "".equals(hubName)) {
//			hubName = defaultHubName;
//		}
//		return cli.RTMPPublishURL(publishDomain, hubName, streamKey, publishExpireTime);
//	}
//
//	/**
//	 * 生成播放地址
//	 * @param urlType
//	 * 	URLTYPE.RTMP 生成 RTMP 播放地址
//	 *  URLTYPE.HLS 生成 HLS 播放地址
//	 *  URLTYPE.HDL 生成 HDL(HTTP-FLV) 播放地址
//	 *  URLTYPE.Cover 生成直播封面地址
//	 * @return
//	 */
//	public static String getPlayUrl(URLTYPE urlType) {
//		return getPlayUrl(defaultHubName, urlType, defaultStreamKey);
//	}
//
//	/**
//	 * 生成播放地址
//	 * @param urlType
//	 * 	URLTYPE.RTMP 生成 RTMP 播放地址
//	 *  URLTYPE.HLS 生成 HLS 播放地址
//	 *  URLTYPE.HDL 生成 HDL(HTTP-FLV) 播放地址
//	 *  URLTYPE.Cover 生成直播封面地址
//	 * @param streamKey 流名
//	 * @return
//	 */
//	public static String getPlayUrl(String hubName, URLTYPE urlType, String streamKey) {
//		if(hubName == null || "".equals(hubName)) {
//			hubName = defaultHubName;
//		}
//		String url = "";
//		if(urlType == URLTYPE.RTMP) {
//			url = cli.RTMPPlayURL(rtmpDomain, hubName, streamKey);
//		} else if(urlType == URLTYPE.HLS) {
//			url = cli.HLSPlayURL(hlsDomain, hubName, streamKey);
//		} else if(urlType == URLTYPE.HDL) {
//			url = cli.HDLPlayURL(hdlDomain, hubName, streamKey);
//		} else if(urlType == URLTYPE.Cover) {
//			url = cli.SnapshotPlayURL(coverDomain, hubName, streamKey);
//		}
//		return url;
//	}
//
//	/**
//	 * 创建默认流
//	 * @throws PiliException
//	 */
//	public static Stream createStream() throws PiliException {
//		return createStream(defaultHubName, defaultStreamKey);
//	}
//
//	/**
//	 * 根据流名称创建流
//	 * @param streamKey
//	 * @throws PiliException
//	 */
//	public static Stream createStream(String hubName, String streamKey) throws PiliException {
//		Stream stream = getHub(hubName).create(streamKey);
//		return stream;
//	}
//
//	/**
//	 * 获取默认流
//	 * @return
//	 * @throws PiliException
//	 */
//	public static Stream getStream() throws PiliException {
//		return getStream(defaultHubName, defaultStreamKey);
//	}
//
//	/**
//	 * 根据流名称获取流
//	 * @param streamKey
//	 * @return
//	 * @throws PiliException
//	 */
//	public static Stream getStream(String hubName, String streamKey) throws PiliException {
//		Stream stream = getHub(hubName).get(streamKey);
//		return stream;
//	}
//
//	/**
//	 * 根据条件查询流列表
//	 * @param hubName 直播空间名
//	 * @param liveonly true 表示查询的是正在直播的流，false表示返回所有的流
//	 * @param prefix 可选，限定只返回带以 prefix 为前缀的流名，不指定表示不限定前缀。
//	 * @param limit 可选，限定返回的流个数，不指定表示遵从系统限定的最大个数。
//	 * @param marker 可选，上一次查询返回的标记，用于提示服务端从上一次查到的位置继续查询，不指定表示从头查询。
//	 * @return
//	 * {
//	 * 		items : 数组，流列表，数组的元素是结构体，key 字段是流名。
//	 * 		marker : 字符串，此次遍历到的标记，用于下次遍历提示起始位置，"" 表示已经查询完所有
//	 * }
//	 * @throws PiliException
//	 */
//	public static ListRet getStreamList(String hubName, boolean liveonly, String prefix, int limit, String marker) throws PiliException {
//		ListRet list = null;
//		if(liveonly) {
//			list = getHub(hubName).listLive(prefix, limit, marker);
//		} else {
//			list = getHub(hubName).list(prefix, limit, marker);
//		}
//		return list;
//	}
//
//	/**
//	 * 查询流信息
//	 * @throws PiliException
//	 */
//	public static Stream getStreamInfo() throws PiliException {
//		return getStreamInfo(defaultHubName, defaultStreamKey);
//	}
//
//	/**
//	 * 查询流信息
//	 * @param streamKey
//	 * @throws PiliException
//	 */
//	public static Stream getStreamInfo(String hubName, String streamKey) throws PiliException {
//		return getStream(hubName, streamKey).info();
//	}
//
//	/**
//	 * 禁播默认流
//	 * @param streamKey
//	 * @throws PiliException
//	 */
//	public static void disableStream() throws PiliException {
//		disableStream(defaultHubName, defaultStreamKey);
//	}
//
//	/**
//	 * 根据流名称禁播流
//	 * @param streamKey
//	 * @throws PiliException
//	 */
//	public static void disableStream(String hubName, String streamKey) throws PiliException {
//		getStream(hubName, streamKey).disable();
//	}
//
//	/**
//	 * 恢复默认流
//	 * @param streamKey
//	 * @throws PiliException
//	 */
//	public static void enableStream() throws PiliException {
//		enableStream(defaultHubName, defaultStreamKey);
//	}
//
//	/**
//	 * 根据流名称恢复流
//	 * @param streamKey
//	 * @throws PiliException
//	 */
//	public static void enableStream(String hubName, String streamKey) throws PiliException {
//		getStream(hubName, streamKey).enable();
//	}
//
//	/**
//	 * 禁用该流一段时间
//	 * @param streamKey 流名称
//	 * @param disabledTill Unix 时间戳，表示流禁播的结束时间(分钟)，-1 表示永久禁播, 0表示恢复播放
//	 * @throws PiliException
//	 */
//	public static void disableStream(String hubName, String streamKey, long disabledTill) throws PiliException {
//		getStream(hubName, streamKey).disable(disabledTill);
//	}
//
//	/**
//	 * 获取流的实时信息
//	 * @param hubName 直播空间名
//	 * @param streamKey 流名称
//	 * @return
//	 * {
//	 * 	StartAt	整数，Unix 时间戳，表示推流开始的时间，0 表示不在推流。
//		ClientIP	字符串，表示主播的IP。
//		bps	整数，表示当前码率。
//		fps	结构体，表示当前帧率信息。
//		audio	整数，当前音频帧率。
//		video	整数，当前视频帧率。
//		data	整数，当前数据帧率。
//	 * }
//	 * @throws PiliException
//	 */
//	public static LiveStatus getLiveStatus(String hubName, String streamKey) throws PiliException {
//		return getStream(hubName, streamKey).liveStatus();
//	}
//
//	/**
//	 * 获取直播历史记录
//	 * @param hubName 直播空间名, 可选，默认默认空间名
//	 * @param streamKey 流名称
//	 * @param start 整数，可选，Unix 时间戳，起始时间，不指定或 0 值表示不限制起始时间。
//	 * @param end 整数，可选，Unix 时间戳，结束时间，不指定或 0 值表示当前时间。
//	 * @return
//	 * @throws PiliException
//	 */
//	public static Record[] getHistoryRecord(String hubName, String streamKey, int start, int end) throws PiliException {
//		return getStream(hubName, streamKey).historyRecord(start, end);
//	}
//
//	/**
//	 * 保存直播回放
//	 * @param hubName 直播空间名, 可选，默认默认空间名
//	 * @param streamKey 流名称
//	 * @param start 整数，可选，Unix 时间戳，要保存的直播的起始时间，不指定或 0 值表示从第一次直播开始。
//	 * @param end 整数，可选，Unix 时间戳，要保存的直播的结束时间，不指定或 0 值表示当前时间。
//	 * @return fname: 字符串，表示保存后在存储空间里的文件名。使用存储空间的下载域名可以访问这文件，形如: http://<DownloadDomainOfStorageBucket>/<Fname>
//	 * "http://"+tsDomain+"/"+fname
//	 * @throws PiliException
//	 */
//	public static String playBack(String hubName, String streamKey, int start, int end) throws PiliException {
//		return getStream(hubName, streamKey).save(start, end);
//	}
//
//	/**
//	 * 保存直播回放
//	 * @param hubName 直播空间名, 可选，默认默认空间名
//	 * @param streamKey 流名称
//	 * @param fname 保存的文件名，可选，不指定系统会随机生成。
//	 * @param start 整数，可选，Unix 时间戳，要保存的直播的起始时间，不指定或 0 值表示从第一次直播开始。
//	 * @param end 整数，可选，Unix 时间戳，要保存的直播的结束时间，不指定或 0 值表示当前时间。
//	 * @param format 保存的文件格式，可选，默认为m3u8，如果指定其他格式，则保存动作为异步模式。详细信息可以参考 转码 的api
//	 * @param pipeline 异步模式时，dora的私有队列，可选，不指定则使用公共队列
//	 * @param notify 异步模式时，保存成功回调通知地址，可选，不指定则不通知
//	 * @param expireDays 更改ts文件的过期时间，可选，默认为永久保存 -1表示不更改ts文件的生命周期，正值表示修改ts文件的生命周期为expireDays
//	 * @return fname: 字符串，表示保存后在存储空间里的文件名。使用存储空间的下载域名可以访问这文件，形如: http://<DownloadDomainOfStorageBucket>/<Fname>
//	 * "http://"+tsDomain+"/"+fname
//	 * @throws PiliException
//	 */
//	public static String playBack(String hubName, String streamKey, String fname, int start, int end,
//			String format, String pipeline, String notify, long expireDays) throws PiliException {
//		SaveOptions so = new SaveOptions();
//		so.fname = fname;
//		so.start = start;
//		so.end = end;
//		so.format = format;
//		so.pipeline = pipeline;
//		so.notify = notify;
//		so.expireDays = expireDays;
//		return getStream(hubName, streamKey).save(so);
//	}
//
//}
