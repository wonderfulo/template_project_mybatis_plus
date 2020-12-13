package com.cxy.utils.wx_pay;

import com.cxy.entity.user_info.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.cxy.utils.wx_pay.WXPayConstants.SignType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;


public class WXPayUtil {

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            WXPayUtil.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }


    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
        return generateSignedXml(data, key, SignType.MD5);
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, SignType signType) throws Exception {
        String sign = generateSignature(data, key, signType);
        data.put(WXPayConstants.FIELD_SIGN, sign);
        return mapToXml(data);
    }


    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, SignType.MD5);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, SignType.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }


    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 日志
     * @return
     */
    public static Logger getLogger() {
        Logger logger = LoggerFactory.getLogger("wxpay java sdk");
        return logger;
    }

    /**
     * 调用微信付款接口
     * @return 调用结果
     * @throws Exception
     */
    public static String wxPay(String xml) throws Exception {

        // 6.0获取需要发送的url地址 获取付款的api接口
        String wxUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

        System.out.println("发送前的xml为：" + xml);

        // 7,向微信发送请求转账请求
        String returnXml = CertHttpUtil.postData(wxUrl, xml, AuthUtil.MCHID, AuthUtil.CERTPATH);

        System.out.println("返回的returnXml为:" + returnXml);

        return returnXml;
    }

    /**
     * 调用订单查询接口
     * @return 调用结果
     * @throws Exception
     */
    public static String wxOrderQuery(String xml) throws Exception {

        // 6.0获取需要发送的url地址 获取付款的api接口
        String wxUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

        System.out.println("发送前的xml为：" + xml);

        // 7,向微信发送请求转账请求
        String returnXml = CertHttpUtil.postData(wxUrl, xml, AuthUtil.MCHID, AuthUtil.CERTPATH);

        System.out.println("返回的returnXml为:" + returnXml);

        return returnXml;
    }

    /**
     * 生成微信请求参数
     * @param request
     * @param userInfo
     * @return
     */
    public static SortedMap<String, String> genWxParam(HttpServletRequest request, UserInfo userInfo) {
        String openId = userInfo.getOpenId();

        // 1.0 拼凑企业支付需要的参数
        // 微信公众号的appid
        String appid = AuthUtil.APPID;
        // 商户号
        String mch_id = AuthUtil.MCHID;
        // 生成随机数
        String nonce_str = WXPayUtil.generateNonceStr();
        // 生成商户订单号
        String partner_trade_no = WXPayUtil.generateNonceStr();
        // 支付给用户openid
        String openid = openId;
        // 是否验证真实姓名呢
        String check_name = "NO_CHECK";
        // 收款用户姓名(非必须)
        String re_user_name = "KOLO";
        // 企业付款金额，最少为100，单位为分
        Random rand = new Random();
        int money = rand.nextInt(21)+ 30;
        String amount = String.valueOf(money);
        // 企业付款操作说明信息。必填。
        String desc = "鼓励金！";
        // 用户的ip地址
        String spbill_create_ip = AuthUtil.getRequestIp(request);

        // 2.0 生成map集合
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        // 微信公众号的appid
        packageParams.put("mch_appid", appid);
        // 商务号
        packageParams.put("mchid", mch_id);
        // 随机生成后数字，保证安全性
        packageParams.put("nonce_str", nonce_str);

        // 生成商户订单号
        packageParams.put("partner_trade_no", partner_trade_no);
        // 支付给用户openid
        packageParams.put("openid", openid);
        // 是否验证真实姓名呢
        packageParams.put("check_name", check_name);
        // 收款用户姓名
        packageParams.put("re_user_name", re_user_name);
        // 企业付款金额，单位为分
        packageParams.put("amount", amount);
        // 企业付款操作说明信息。必填。
        packageParams.put("desc", desc);
        // 调用接口的机器Ip地址
        packageParams.put("spbill_create_ip", spbill_create_ip);
        return packageParams;
    }

    /**
     * 生成微信订单查询参数
     * @return
     */
    public static SortedMap<String, String> getWxQueryParam(String partnerTradeNo) {

        // 1.0 拼凑企业订单查询需要的参数
        // 随机字符串
        String nonce_str = WXPayUtil.generateNonceStr();
        // 签名，在外部生成
        // 商户订单号
        String partner_trade_no = partnerTradeNo;
        // 商户号
        String mch_id = AuthUtil.MCHID;
        // 微信公众号的appid
        String appid = AuthUtil.APPID;

        // 2.0 生成map集合
        SortedMap<String, String> packageParams = new TreeMap<>();
        // 随机生成后数字，保证安全性
        packageParams.put("nonce_str", nonce_str);
        // 待查询商户订单号
        packageParams.put("partner_trade_no", partner_trade_no);
        // 商务号
        packageParams.put("mch_id", mch_id);
        // 微信公众号的appid
        packageParams.put("appid", appid);

        return packageParams;
    }



    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

}
