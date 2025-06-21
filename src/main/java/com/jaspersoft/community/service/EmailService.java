package com.jaspersoft.community.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.protocol}")
    private String mailProtocol;

    @Value("${app.reset.base-url}")
    private String resetPasswordUrl;

    @Value("${app.reset.username-url}")
    private String resetUsernameUrl;

    public void sendResetPasswordLink(String email, String token) {
        String subject = "Reset Your Password";
        String body = "Click the link below to reset your password:\n" + resetPasswordUrl + token;
        sendEmail(email, subject, body);
    }

    public void sendResetUsernameLink(String email, String token) {
        String subject = "Reset Your Username";
        String body = "Click the link below to reset your username:\n" + resetUsernameUrl + token;
        sendEmail(email, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);
        props.put("mail.smtp.auth", "true");

        if ("smtps".equalsIgnoreCase(mailProtocol)) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(fromEmail, emailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
