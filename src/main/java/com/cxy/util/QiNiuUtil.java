//package com.cxy.util;
//
//import java.io.InputStream;
//
//import com.qiniu.common.QiniuException;
//import com.qiniu.common.Zone;
//import com.qiniu.http.Response;
//import com.qiniu.storage.Configuration;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.util.Auth;
//
//public class QiNiuUtil {
//
//	/**
//	 * You can get your accesskey from <a href="https://dev.qiniutek.com"
//	 * target="blank"> https://dev.qiniutek.com </a>
//	 */
//	//七牛云密钥中的AK
//	public static String ACCESS_KEY = "HZwZZ2-pm3JThoqcnHi9U7F_3L94DhLLQ7ovbKvc";
//
//	/**
//	 * You can get your accesskey from <a href="https://dev.qiniutek.com"
//	 * target="blank"> https://dev.qiniutek.com </a>
//	 */
//	//七牛云密钥中的SK
//	public static String SECRET_KEY = "QZ0h2U4CitYwO7J5B6SpiSwKu0AwU_4yvw0j0WN_";
//	/**
//	 * 云存储空间名
//	 */
//	private static String bucketName = "supershoper";
//	private static String bucketName_video ="insight-video";
//	/**
//	 * 云存储域名
//	 */
////	public static String DOMAIN = /*"supershoper.qiniudn.com"*/"7fvhtu.com1.z0.glb.clouddn.com";
////	public static String DOMAIN_VIDEO = "insight-video.qiniudn.com";
//
//	public static String DOMAIN = "supershoper.xxynet.com";
//	public static String DOMAIN_VIDEO = "video.xxynet.com";
//
//	//简单上传，使用默认策略，只需要设置上传的空间名就可以了
//    public static String getUpToken(){
//    	//密钥配置
//        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//        return auth.uploadToken(bucketName);
//    }
//
//	//普通上传
//    public static void upload(String filePath, String fileName) throws Exception{
//      try {
//    	//创建上传对象
//    	UploadManager uploadManager = new UploadManager(null);
//        //调用put方法上传
//        Response res = uploadManager.put(filePath, fileName, getUpToken());
//        //打印返回的信息
//        System.out.println(res.bodyString());
//      } catch (QiniuException e) {
//           throw new Exception("qiniu upload error",e);
//      }
//    }
//
//    //普通上传
//    public static void upload(InputStream stream, String fileName) throws Exception{
//      try {
//    	//创建上传对象
//    	UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
//        //调用put方法上传
//        Response res = uploadManager.put(stream, fileName, getUpToken(), null, null);
//        //打印返回的信息
////        System.out.println(res.bodyString());
//      } catch (QiniuException e) {
//           throw new Exception("qiniu upload error",e);
//      }
//    }
//}
