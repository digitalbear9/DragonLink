package com.dragonlink.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    // 邮件服务器配置
    private static final String SMTP_HOST = "smtp.example.com"; // SMTP服务器地址
    private static final String SMTP_PORT = "587";             // SMTP服务器端口
    private static final String EMAIL_USERNAME = "your-email@example.com"; // 邮件用户名
    private static final String EMAIL_PASSWORD = "your-password";          // 邮件密码

    /**
     * 发送简单文本邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return true: 发送成功, false: 发送失败
     */
    public boolean sendSimpleEmail(String to, String subject, String content) {
        try {
            Session session = createSession();
            Message message = createMessage(session, to, subject, content, false);
            Transport.send(message);
            System.out.println("简单邮件发送成功！");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("简单邮件发送失败！");
            return false;
        }
    }

    /**
     * 发送带HTML内容的邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content HTML格式的邮件内容
     * @return true: 发送成功, false: 发送失败
     */
    public boolean sendHtmlEmail(String to, String subject, String content) {
        try {
            Session session = createSession();
            Message message = createMessage(session, to, subject, content, true);
            Transport.send(message);
            System.out.println("HTML邮件发送成功！");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("HTML邮件发送失败！");
            return false;
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人邮箱
     * @param subject  邮件主题
     * @param content  邮件正文内容
     * @param filePath 附件文件路径
     * @return true: 发送成功, false: 发送失败
     */
    public boolean sendEmailWithAttachment(String to, String subject, String content, String filePath) {
        try {
            Session session = createSession();

            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // 创建邮件正文
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(content);

            // 创建附件部分
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(filePath);

            // 合并正文和附件
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // 发送邮件
            Transport.send(message);
            System.out.println("带附件的邮件发送成功！");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("带附件的邮件发送失败！");
            return false;
        }
    }

    /**
     * 创建邮件会话
     *
     * @return Session对象
     */
    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // 启用STARTTLS加密

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });
    }

    /**
     * 创建邮件消息
     *
     * @param session 会话
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param isHtml  是否为HTML格式
     * @return Message对象
     * @throws MessagingException 异常
     */
    private Message createMessage(Session session, String to, String subject, String content, boolean isHtml)
            throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        if (isHtml) {
            message.setContent(content, "text/html;charset=UTF-8");
        } else {
            message.setText(content);
        }

        return message;
    }
}
