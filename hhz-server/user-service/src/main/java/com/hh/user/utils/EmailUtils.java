package com.hh.user.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/***
* @Description: 发送邮件工具类
* @Param:
* @return:
* @Author: hh
* @Date: 2023/5/14
*/
@Component
public class EmailUtils {
    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    private static String sendMailer;
    private static String subject;
    private static String text;
    private static JavaMailSender MailSender;

    @PostConstruct
    public void setMailSender() {
        EmailUtils.MailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    public void setSendMailer(String sendMailer) {
        EmailUtils.sendMailer = sendMailer;
    }

    @Value("${mail.validateCode.subject}")
    public void setSubject(String subject) {
        EmailUtils.subject = subject;
    }

    @Value("${mail.validateCode.text}")
    public void setText(String text) {
        EmailUtils.text = text;
    }

    public static void senCodeToMail(String email, String code) {
        try {
            MimeMessage message = MailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(sendMailer); //邮件发件人
            helper.setTo(email); //邮件收件人
            helper.setSubject(subject); //邮件主题
            helper.setText(String.format(text, code)); // 邮件内容
            message.setSentDate(new Date()); // 邮件发送时间
            MailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
