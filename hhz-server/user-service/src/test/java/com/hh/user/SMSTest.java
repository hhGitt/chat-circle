package com.hh.user;

import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class SMSTest {
    private static final String EMAIL_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


    /**
     * 判断是否为有效邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkMail(String email) {
        if (StringUtils.isNotBlank(email)) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            return pattern.matcher(email).matches();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkMail("2421159018@qq.com"));
    }


}
