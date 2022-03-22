package com.example.oj.service;

import com.example.oj.common.Constants;
import com.example.oj.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author sjy
 * @date 2022/2/15
 **/
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * 激活账号邮件发送
     * @param activationUrl 激活url连接
     * @param email 收件人邮箱
     * @param type 找回密码/注册
     * @return
     * @throws MessagingException
     */
    public void sendMailForActivationAccount(String activationUrl,String email,String type) {
        // 创建邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 获取信息编辑对象
        MimeMessageHelper  message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            // 邮件发送者
            message.setFrom(adminEmail);
            // 接收者
            message.setTo(email);
            //日期
            message.setSentDate(new Date());
            // 内容使用html
            Context context = new Context();
            String text;
            if("register".equals(type)){
                // 标题
                message.setSubject("欢迎来到【Online Judge】- 个人账号激活");
                context.setVariable("activationUrl",activationUrl);
                text = templateEngine.process("active-account.html",context);
            }else {
                message.setSubject("欢迎来到【Online Judge】- 个人密码找回");
                context.setVariable("activationUrl",activationUrl);
                text = templateEngine.process("find-account.html",context);
            }
            //设置正文
            message.setText(text,true);
        } catch (MessagingException e) {
            throw new ServiceException(Constants.CODE_510,"邮件服务器错误");
        }
        mailSender.send(mimeMessage);
    }
}
