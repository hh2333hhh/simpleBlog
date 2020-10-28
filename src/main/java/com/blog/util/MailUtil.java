package com.blog.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailUtil {
    @Autowired
    public JavaMailSender mailSender;

    //生成并填充消息体
    @Test
    public void sentCode(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        mailMessage.setFrom("sambaaaaaa@163.com");//发送邮箱
        System.out.println(verifyCode);
        mailMessage.setTo("1920257494@qq.com");//接收邮箱
        mailMessage.setSubject("验证码"); //邮件主题
        mailMessage.setText("尊敬的用户，您本次的验证码是" + verifyCode + ",请在10分钟内输入。"); //邮件正文
        //发送邮件
        mailSender.send(mailMessage);

    }


}
