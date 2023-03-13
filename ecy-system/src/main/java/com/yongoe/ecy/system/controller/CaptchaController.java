package com.yongoe.ecy.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yongoe.ecy.utils.MailUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "验证码")
@ApiSupport(order = 1)
@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private MailUtils mailUtils;

    @Operation(summary = "邮件验证码")
    @GetMapping(value = "/captcha/email")
    public R email(String email) {
        if (StringUtils.isEmpty(email))
            return R.error("不能为空");
        try {
            Long expire = redisTemplate.getExpire(email, TimeUnit.MINUTES);
            if (expire == null || expire > 13)
                return R.error("请求过快，请60秒后重试");
            String code = mailUtils.sendMail(email);
            redisTemplate.opsForValue().set(email, code, 15, TimeUnit.MINUTES);
            return R.success("发送成功，请注意查收");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("邮件发送失败");
        }
    }

    @Operation(summary = "图片验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //定义response输出类型为image/jpeg
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //---------------------生成验证码----------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码:  " + text);
        //将验证码放到session中
        request.getSession().setAttribute("captcha", text);
        //根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片,格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //------------------------end------------------------
    }

}
