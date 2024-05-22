package com.techsoft.springsecurity.emailService;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;
    @Async
    public void sendEmail(
            String userName,
            String to,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        MimeMessage mimeMessage=emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,
                MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name()
        );


        Map<String,Object> properties=new HashMap<>();
        properties.put("username",userName);
        properties.put("confirmationUrl",confirmationUrl);
        Context context=new Context();
        context.setVariables(properties );

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        String template=templateEngine.process("Activation",context);
        mimeMessageHelper.setText(template,true);
        emailSender.send(mimeMessage);
    }

}
