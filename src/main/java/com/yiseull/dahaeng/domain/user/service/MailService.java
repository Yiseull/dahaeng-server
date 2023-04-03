package com.yiseull.dahaeng.domain.user.service;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") String fromEmail;
    private String authCode;

    private void createCode() {

        String key = UUID.randomUUID().toString().replace("-", "");
        authCode = key.substring(0, 10);
    }

    private MimeMessage createMessage(String toEmail, int option) throws Exception {

        createCode();

        String subject, text;
        String[] result = new String[0];
        if (option == 0) {  // option 0이면 회원가입, 1이면 비밀번호 찾기
            result = signUp();
        }
        subject = result[0];
        text = result[1];

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, toEmail);
        message.setSubject(subject);
        message.setFrom(fromEmail);
        message.setText(text, "utf-8", "html");

        return message;
    }

    private String[] signUp() {
        String subject = "Dahaeng 회원가입 인증 코드";
        String text = "";
        text += "<p>안녕하세요.</p>";
        text += "<p>Dahaneg 인증 코드는 다음과 같습니다.</p>";
        text += "<h3>" + authCode + "</h3>";
        text += "<p>감사합니다.</p>";
        return new String[] {subject, text};
    }

    public String sendMail(String toEmail, int option) throws Exception {
        mailSender.send(createMessage(toEmail, option));
        return authCode;
    }

}
