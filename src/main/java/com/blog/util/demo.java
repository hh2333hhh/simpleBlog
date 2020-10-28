package com.blog.util;

import com.blog.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class demo {

    @Autowired
    private MailService mailService;

    private static final String TO = "2829249088@qq.com";
    private static final String SUBJECT = "测试邮件";
    private static final String CONTENT = "请及时填写验证码！您本次的验证码是：";

    /**
     * 测试发送普通邮件
     */
    @Test
    public void sendSimpleMailMessage() {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

        mailService.sendMimeMessge(TO, SUBJECT, CONTENT+verifyCode);
    }
}