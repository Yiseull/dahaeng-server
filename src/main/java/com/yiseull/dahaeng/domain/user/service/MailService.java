package com.yiseull.dahaeng.domain.user.service;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") String fromEmail;
    private String authCode;

    private void createCode() throws Exception {

        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authCode = key.toString();
    }

    private MimeMessage createMessage(String toEmail) throws Exception {

        createCode();
        String subject = "Dahaeng 회원가입 인증 코드";
        String text = "";
        text += "<p>안녕하세요.</p>";
        text += "<p>Dahaneg 인증 코드는 다음과 같습니다.</p>";
        text += "<h3>" + authCode + "</h3>";
        text += "<p>감사합니다.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, toEmail);
        message.setSubject(subject);
        message.setFrom(fromEmail);
        message.setText(text, "utf-8", "html");

        return message;
    }

    public String sendMail(String toEmail) throws Exception {
        mailSender.send(createMessage(toEmail));
        return authCode;
    }

}
