package com.yongoe.ecy.basic.controller.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 信件
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "信件管理ReqVo")
public class LetterReqVo {

    private Long id;
    /**
     * 发件人id
     */
    private Long addresserId;
    /**
     * 发件人
     */
    private String addresser;
    /**
     * 收件人id
     */
    private Long addresseeId;
    /**
     * 收件人
     */
    private String addressee;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态
     */
    private Boolean state;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;
}