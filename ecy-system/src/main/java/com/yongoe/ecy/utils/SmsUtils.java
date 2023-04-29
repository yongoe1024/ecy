package com.yongoe.ecy.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 腾讯云短信 工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class SmsUtils {
    @Resource
    private SysConfigUtils configUtils;

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return 六位数字
     */
    public String sendVerificationCode(String phone) throws TencentCloudSDKException {
        String code = random6();
        sendSms(phone, new String[]{code});
        return code;
    }

    /**
     * 随机6位数
     */
    public String random6() {
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(random);
    }

    /**
     * 发送短信
     *
     * @param phone   手机号
     * @param content 内容
     */
    public void sendSms(String phone, String[] content) throws TencentCloudSDKException {
        // 地区，随意选
        String region = "ap-nanjing";
        String secretId = configUtils.get("sms-secretId");
        String secretKey = configUtils.get("sms-secretKey");
        String sdkAppId = configUtils.get("sms-sdkAppId");
        String signName = configUtils.get("sms-signName");
        String templateId = configUtils.get("sms-templateId");
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(secretId, secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, region, clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = {phone};
        req.setPhoneNumberSet(phoneNumberSet1);
        req.setSmsSdkAppId(sdkAppId);
        req.setSignName(signName);
        req.setTemplateId(templateId);
        req.setTemplateParamSet(content);
        // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
        SendSmsResponse resp = client.SendSms(req);
        for (SendStatus sendStatus : resp.getSendStatusSet()) {
            if (!"Ok".equals(sendStatus.getCode())) {
                throw new RuntimeException(sendStatus.getPhoneNumber() + "发送失败--> " + sendStatus.getMessage());
            }
        }
        // 输出json格式的字符串回包
        System.out.println(SendSmsResponse.toJsonString(resp));
    }


}