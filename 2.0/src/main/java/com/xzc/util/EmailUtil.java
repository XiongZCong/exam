package com.xzc.util;

import com.xzc.model.Email;
import com.xzc.result.CodeMsg;
import com.xzc.result.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendSimpleMail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());
        mailSender.send(message);
    }

    public void sendHtmlMail(Email email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText(), true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }

    public void sendAttachmentsMail(Email email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText());
            FileSystemResource file = new FileSystemResource(new File("spring.log"));
            helper.addAttachment("附件-1.jpg", file);
            helper.addAttachment("附件-2.jpg", file);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }

    public void sendInlineMail(Email email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);
            FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
            helper.addInline("weixin", file);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }
}
