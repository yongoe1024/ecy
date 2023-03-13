package com.yongoe.ecy.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

/**
 * 邮件发送
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class MailUtils {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;

    public String sendMail(String email) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        //发件人
        helper.setFrom(mailProperties.getUsername());
        //收件人
        helper.setTo(email);
        //主题
        helper.setSubject("员工管理系统");
        //发送日期
        helper.setSentDate(new Date());
        //邮件内容
        Context context = new Context();
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        context.setVariable("code", String.valueOf(random));
        // 模板生成
        String s = templateEngine.process("mail", context);
        helper.setText(s, true);
        //发送邮件
        javaMailSender.send(msg);
        return String.valueOf(random);
    }


}
