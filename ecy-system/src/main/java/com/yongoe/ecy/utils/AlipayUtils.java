package com.yongoe.ecy.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付
 *
 * @author yongoe
 * @since 2023/12/9
 */
@Lazy
@Component
public class AlipayUtils {
    @Resource
    private SysConfigUtils configUtils;
    //获取配置文件中的配置信息
    //应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    private String appId;
    //商户私钥 您的PKCS8格式RSA2私钥
    private String privateKey;
    //支付宝公钥
    private String publicKey;
    //服务器异步通知页面路径
    private String notifyUrl;
    //页面跳转同步通知页面路径
    private String returnUrl;
    //签名方式
    private String signType;
    //字符编码格式
    private String charset;
    //支付宝网关
    private String gatewayUrl;
    private String format;

    /**
     * 初始化
     */
    @PostConstruct
    private void doConstruct() {
        appId = configUtils.get("alipay-appId");
        privateKey = configUtils.get("alipay-privateKey");
        publicKey = configUtils.get("alipay-publicKey");
        notifyUrl = configUtils.get("alipay-notifyUrl");
        returnUrl = configUtils.get("alipay-returnUrl");
        signType = configUtils.get("alipay-signType");
        charset = configUtils.get("alipay-charset");
        gatewayUrl = configUtils.get("alipay-gatewayUrl");
        format = configUtils.get("alipay-format");
    }

    /**
     * PC网页段支付，返回的是支付宝账号的登录页面
     */
    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
        //PC网页支付使用AlipayTradePagePayRequest传参，下面调用的是pageExecute方法
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 调用SDK生成html表单
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    /**
     * 手机扫码支付
     */
//    public R payQR(AlipayBean alipayBean) throws Exception {
//        //接口模拟数据
//        alipayBean.setOut_trade_no("20210817010101003");
//        alipayBean.setSubject("订单名称");
//        alipayBean.setTotal_amount(String.valueOf(new Random().nextInt(100)));
//        alipayBean.setBody("商品描述");
//        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
//        //扫码支付使用AlipayTradePrecreateRequest传参，下面调用的是execute方法
//        AlipayTradePrecreateRequest precreateRequest = new AlipayTradePrecreateRequest();
//        precreateRequest.setReturnUrl(returnUrl);
//        precreateRequest.setNotifyUrl(notifyUrl);
//        precreateRequest.setBizContent(JSON.toJSONString(alipayBean));
//        AlipayTradePrecreateResponse response = null;
//        try {
//            response = alipayClient.execute(precreateRequest);
//        } catch (AlipayApiException e) {
//            throw new Exception(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", e.getErrCode(), e.getErrMsg()));
//        }
//        /*  "code": "10000",
//            "msg": "Success",
//            "out_trade_no": "815259610498863104",
//            "qr_code": "https://qr.alipay.com/bax09455sq1umiufbxf4503e"   */
//        if (!response.isSuccess()) {
//            throw new Exception(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", response.getCode(), response.getMsg()));
//        }
//        // 返回结果，主要是返回 qr_code，前端根据 qr_code 进行重定向或者生成二维码引导用户支付
//        return R.success().put(response.getQrCode());
//    }

}
