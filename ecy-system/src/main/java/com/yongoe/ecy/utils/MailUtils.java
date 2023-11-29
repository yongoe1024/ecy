package com.yongoe.ecy.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 邮件
 *
 * @author yongoe
 * @since 2023/11/8
 */
@Lazy
@Component
public class MailUtils {
    @Resource
    private SysConfigUtils configUtils;
    private JavaMailSenderImpl mailSender;
    private Configuration configuration;

    /**
     * 初始化
     */
    @PostConstruct
    private void doConstruct() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setTemplateLoader(new SpringTemplateLoader(new DefaultResourceLoader(), "templates/"));
        init();
    }

    /**
     * 发送邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param isHtml  是否是html
     * @throws Exception 失败异常
     */
    public void sendMail(String to, String subject, String content, Boolean isHtml) throws Exception {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        //发件人
        helper.setFrom(Objects.requireNonNull(mailSender.getUsername()));
        //收件人
        helper.setTo(to);
        //主题
        helper.setSubject(subject);
        //发送日期
        helper.setSentDate(new Date());
        //邮件内容
        // 定义输出
        helper.setText(content, isHtml);
        //发送邮件
        mailSender.send(msg);
    }

    /**
     * 发送邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param template 模板
     * @param map      数据
     * @param isHtml   是否是html
     * @throws Exception 失败异常
     */
    public void sendMail(String to, String subject, String template, Map<Objects, Object> map, Boolean isHtml) throws Exception {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        //发件人
        helper.setFrom(Objects.requireNonNull(mailSender.getUsername()));
        //收件人
        helper.setTo(to);
        //主题
        helper.setSubject(subject);
        //发送日期
        helper.setSentDate(new Date());
        //邮件内容
        String str = createTemplate(template, map);
        // 定义输出
        helper.setText(str, isHtml);
        //发送邮件
        mailSender.send(msg);
    }

    private String createTemplate(String templateName, Map<Objects, Object> data) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
    }

    private void init() {
        String host = configUtils.get("mail-host");
        String port = configUtils.get("mail-port");
        String username = configUtils.get("mail-username");
        String password = configUtils.get("mail-password");
        String protocol = configUtils.get("mail-protocol");
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);
    }

}
