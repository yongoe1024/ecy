package com.yongoe.ecy.system.controller.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * qq
 *
 * @author yongoe
 * @since 2023/3/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QQReq {

    private String uuid;
    private String username;
    private String nickname;
    private String avatar;
    private String blog;
    private String company;
    private String location;
    private String email;
    private String remark;
    private String gender;
    private String source;
    private Token token;
    private RawUserInfo rawUserInfo;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Token {
        private String accessToken;
        private Long expireIn;
        private String refreshToken;
        private Long refreshTokenExpireIn;
        private String uid;
        private String openId;
        private String accessCode;
        private String unionId;
        private String scope;
        private String tokenType;
        private String idToken;
        private String macAlgorithm;
        private String macKey;
        private String code;
        private String oauthToken;
        private String oauthTokenSecret;
        private String userId;
        private String screenName;
        private String oauthCallbackConfirmed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RawUserInfo {
        private Long ret;
        private String msg;
        private Long is_lost;
        private String gender;
        private String is_yellow_vip;
        private String city;
        private String year;
        private String level;
        private String figureurl_2;
        private String figureurl_1;
        private Long gender_type;
        private String is_yellow_year_vip;
        private String province;
        private String constellation;
        private String figureurl;
        private String figureurl_type;
        private String figureurl_qq;
        private String nickname;
        private String yellow_vip_level;
        private String figureurl_qq_1;
        private String vip;
        private String figureurl_qq_2;
    }


}

